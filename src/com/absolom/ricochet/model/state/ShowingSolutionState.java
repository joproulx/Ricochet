package com.absolom.ricochet.model.state;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.absolom.ricochet.message.command.ClaimSolutionCommand;
import com.absolom.ricochet.message.command.MoveRobotCommand;
import com.absolom.ricochet.message.event.PlayerShowSolutionCompletedEvent;
import com.absolom.ricochet.message.event.RobotMovedEvent;
import com.absolom.ricochet.model.PlayerId;
import com.absolom.ricochet.model.PlayerSolution;
import com.absolom.ricochet.model.PlayerSolutionManager;
import com.absolom.ricochet.model.Robot;
import com.absolom.ricochet.model.RobotAvailableMoves;
import com.absolom.ricochet.model.RobotId;
import com.absolom.ricochet.model.RobotMove;
import com.absolom.ricochet.model.RobotPathsBuilder;
import com.absolom.ricochet.model.common.MoveErrorType;
import com.absolom.ricochet.model.common.MoveResult;
import com.absolom.ricochet.model.common.StateType;
import com.absolom.utility.common.Direction;
import com.absolom.utility.messaging.IMessageHandler;
import com.absolom.utility.messaging.IMessageResult;
import com.absolom.utility.messaging.MessageHandler;
import com.absolom.utility.messaging.MessageResult;

public class ShowingSolutionState extends PausableState {
	ScheduledFuture<?> m_moveRobotTimer;
	ScheduledFuture<?> m_showSolutionTimer;

	public ShowingSolutionState() {
		super(StateType.ShowSolution);

	}

	public @Override
	void onEntered() {
		super.onEntered();
		registerMessageReceiver(MoveRobotCommand.class, onMoveRobotCommand);
		registerMessageReceiver(ClaimSolutionCommand.class, onClaimSolutionCommand);

		startShowSolutionTimer();
	}

	public @Override
	void onExited() {
		super.onExited();
		unregisterMessageReceiver(onMoveRobotCommand);
		unregisterMessageReceiver(onClaimSolutionCommand);
		cancelMoveRobotTimer();
		cancelShowSolutionTimer();
	}

	private IMessageHandler onClaimSolutionCommand = new MessageHandler<ClaimSolutionCommand>(this) {
		public @Override
		IMessageResult onMessage(ClaimSolutionCommand command) {
			onPlayerClaimSolution(command.getPlayerId(), command.getMoveCount());

			return MessageResult.success();
		}
	};

	private void startShowSolutionTimer() {
		cancelShowSolutionTimer();
		m_showSolutionTimer = m_context.startTimer(getConfig().getShowSolutionTimeout(), onShowSolutionTimeout, this);
	}

	public long getShowSolutionRemainingTime() {
		if (m_showSolutionTimer != null) {
			return m_showSolutionTimer.getDelay(TimeUnit.MILLISECONDS);
		}

		return -1;
	}

	private void cancelShowSolutionTimer() {
		if (m_showSolutionTimer == null) {
			return;
		}

		m_showSolutionTimer.cancel(true);
		m_showSolutionTimer = null;
	}

	private IMessageHandler onMoveRobotCommand = new MessageHandler<MoveRobotCommand>(this) {
		public @Override
		IMessageResult onMessage(MoveRobotCommand command) {
			MoveResult moveResult = moveRobot(command.getPlayerId(), command.getRobotId(), command.getDirection());

			if (moveResult.isSucceeded()) {
				postMessage(new RobotMovedEvent(moveResult.getPlayerId(), moveResult.getMove()));
			}

			if (moveResult.isCompleted()) {
				postMessage(new PlayerShowSolutionCompletedEvent(moveResult.isSucceeded()));
			}

			return new MoveRobotCommand.Result(moveResult);
		}
	};

	private MoveResult moveRobot(PlayerId playerId, RobotId robotId, Direction direction) {
		Robot robot = getEntityManager().getEntity(robotId);
		RobotAvailableMoves moves = RobotPathsBuilder.buildRobotPaths(robotId, robot.getCoordinates(), getEntityManager());
		RobotMove move = moves.getMove(direction);		
		
		MoveResult result = validate(playerId, move);
		if (result != null) {
			return result;
		}

		cancelMoveRobotTimer();

		result = moveRobotInternal(playerId, robot, move);

		commitUpdates();

		if (!result.isCompleted()) {
			startMoveRobotTimer();
			return result;
		}

		if (!result.isSucceeded()) {
			handleInvalidSolution();
			return result;
		}

		onTargetReached();

		return result;
	}

	private void handleInvalidSolution() {
		boolean betterClaimedSolutionExist = selectPlayerWithBestSolution();

		if (betterClaimedSolutionExist) {
			gotoState(new ShowingSolutionState());
			return;
		}

		gotoState(new FindingSolutionState());
	}

	private void startMoveRobotTimer() {
		cancelMoveRobotTimer();
		m_moveRobotTimer = m_context.startTimer(getConfig().getMoveRobotTimeout(), onMoveRobotTimeout, this);
	}

	private Runnable onMoveRobotTimeout = new Runnable() {
		public void run() {
			if (!isCurrentState()) {
				return;
			}

			getSolutionManager().setFailedSolution(m_context.getCurrentPlayerId(), MoveErrorType.MoveTimeout);
			handleInvalidSolution();
		}
	};

	private Runnable onShowSolutionTimeout = new Runnable() {
		public void run() {
			if (!isCurrentState()) {
				return;
			}

			getSolutionManager().setFailedSolution(m_context.getCurrentPlayerId(), MoveErrorType.SolutionTimeout);

			handleInvalidSolution();
		}
	};

	private void cancelMoveRobotTimer() {
		if (m_moveRobotTimer == null) {
			return;
		}

		m_moveRobotTimer.cancel(true);
		m_moveRobotTimer = null;
	}

	private MoveResult validate(PlayerId playerId, RobotMove move) {
		// ---------------------
		// Validation
		// ---------------------
		if (!m_context.getCurrentPlayerId().equals(playerId)) {
			return new MoveResult(playerId, MoveErrorType.InvalidPlayer, move);
		}

		if (!isValidMove(move)) {
			return new MoveResult(playerId, MoveErrorType.InvalidDirection, move);
		}

		return null;
	}

	private void onPlayerClaimSolution(PlayerId playerId, int claimedMoveCount) {
		PlayerSolutionManager solutionManager = getSolutionManager();

		if (!solutionManager.addClaimedSolution(playerId, claimedMoveCount)) {
			return;
		}

		if (isBetterSolutionThanCurrent(claimedMoveCount) && getConfig().overrideTurnWithBetterSolution()) {
			if (selectPlayerWithBestSolution()) {
				gotoState(new ShowingSolutionState());
			} else {
				gotoState(new FindingSolutionState());
			}
		}
	}
	
	private boolean isBetterSolutionThanCurrent(int claimedMoveCount) {
		return claimedMoveCount < getCurrentClaimedMoveCount();
	}

	private int getCurrentClaimedMoveCount() {
		return getSolutionManager().getPlayerSolution(m_context.getCurrentPlayerId()).getClaimedMoveCount();
	}

	private MoveResult moveRobotInternal(PlayerId playerId, Robot robot, RobotMove robotMove) {
		// ---------------------
		// Actual robot move
		// ---------------------
		moveRobotOnBoard(robot, robotMove);

		// ---------------------
		// Update
		// ---------------------
		PlayerSolution solution = addPlayerMove(playerId, robotMove);

		if (solution == null){
			new MoveResult(playerId, MoveErrorType.NoSolutionClaimed, true, null);
		}
		
		return new MoveResult(solution.getPlayerId(), solution.getFailReason(), solution.isValidated(), solution.getLastMove());
	}

	private PlayerSolution addPlayerMove(PlayerId playerId, RobotMove move) {
		return getSolutionManager().addMove(playerId, move);
	}

	private void moveRobotOnBoard(Robot robot, RobotMove move) {
		getBoard().moveItem(robot, move.getPath().getLastWayPoint());
	}

	private boolean isValidMove(RobotMove move) {
		return !move.isComplete();
	}

	

}
