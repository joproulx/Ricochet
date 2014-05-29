package com.absolom.ricochet.model;

import java.util.ArrayList;
import java.util.List;

import com.absolom.ricochet.model.common.MoveErrorType;

public class PlayerSolution {

	public void setFailed(MoveErrorType failReason) {
		m_state = State.Failed;
		m_failReason = failReason;
	}

	public MoveErrorType getFailReason() {
		return m_failReason;
	}

	public boolean isRevertMove(RobotMove robotMove) {
		RobotMove lastMove = getLastMove();

		if (lastMove == null) {
			return false;
		}

		return lastMove.isRevert(robotMove);
	}

	public RobotMove getLastMove() {
		if (m_robotMoves.size() == 0) {
			return null;
		}

		return m_robotMoves.get(m_robotMoves.size() - 1);
	}

	public void removeLastMove() {
		if (m_robotMoves.size() == 0) {
			return;
		}

		m_robotMoves.remove(m_robotMoves.size() - 1);
	}

	public int getClaimedMoveCount() {
		return m_claimedSolution.getClaimedMoveCount();
	}

	public enum State {
		NotShown, Failed, Succeeded
	}

	public List<RobotMove> getAllMoves() {
		return new ArrayList<RobotMove>(m_robotMoves);
	}

	public void addMove(RobotMove move) {
		m_robotMoves.add(new RobotMove(move));
	}

	private ArrayList<RobotMove> m_robotMoves;
	private MoveErrorType m_failReason;

	private State m_state;
	private final PlayerClaimedSolution m_claimedSolution;

	public PlayerSolution(PlayerId playerId, int claimedMoveCount) {
		m_failReason = MoveErrorType.None;
		m_state = State.NotShown;
		m_claimedSolution = new PlayerClaimedSolution(playerId, claimedMoveCount);
		m_robotMoves = new ArrayList<RobotMove>();

	}

	public PlayerId getPlayerId() {
		return m_claimedSolution.getPlayerId();
	}

	public PlayerClaimedSolution getClaimedSolution() {
		return m_claimedSolution;
	}

	public int getMoveCount() {
		return m_robotMoves.size();
	}

	public boolean hasBustedMoveCount() {
		return getMoveCount() > m_claimedSolution.getClaimedMoveCount();
	}

	public boolean hasReachedMoveCount() {
		return getMoveCount() >= m_claimedSolution.getClaimedMoveCount();
	}

	public void setSucceeded() {
		m_state = State.Succeeded;
		m_failReason = MoveErrorType.None;
	}

	public boolean isValidated() {
		return m_state == State.Succeeded || m_state == State.Failed;
	}

}
