package com.absolom.ricochet.model.state;

import java.util.List;

import com.absolom.ricochet.config.IConfiguration;
import com.absolom.ricochet.model.EntityManager;
import com.absolom.ricochet.model.GameBoard;
import com.absolom.ricochet.model.GameStateContext;
import com.absolom.ricochet.model.Player;
import com.absolom.ricochet.model.PlayerClaimedSolution;
import com.absolom.ricochet.model.PlayerSolutionManager;
import com.absolom.ricochet.model.Robot;
import com.absolom.ricochet.model.TargetId;
import com.absolom.ricochet.model.common.StateType;
import com.absolom.utility.messaging.IMessage;
import com.absolom.utility.messaging.IMessageHandler;
import com.absolom.utility.messaging.MessageEndPoint;
import com.absolom.utility.messaging.MessageTransceiver;

public class State extends MessageEndPoint {
	protected GameStateContext m_context;
	protected StateType m_type;

	public State(StateType type) {
		m_type = type;
	}

	public boolean isCurrentState() {
		return m_context.getCurrentState() == this;
	}

	public boolean addPlayer(Player player) {
		// TODO
		return false;
	}

	public boolean commitUpdates() {
		return getEntityManager().applyChanges();
	}

	public EntityManager getEntityManager() {
		return m_context.getEntityManager();
	}

	public IConfiguration getConfig() {
		return m_context.getConfig();
	}

	public void registerMessageReceiver(Class<?> commandType, IMessageHandler messageReceiver) {
		getMessageTransceiver().registerMessageReceiver(commandType, messageReceiver);
	}

	public void unregisterMessageReceiver(IMessageHandler messageReceiver) {
		getMessageTransceiver().unregisterMessageReceiver(messageReceiver);
	}

	public void postMessage(IMessage message) {
		getMessageTransceiver().postAndWait(this, message);
	}

	public MessageTransceiver getMessageTransceiver() {
		return m_context.getMessageTransceiver();
	}

	public void gotoState(State nextState) {
		m_context.gotoState(nextState);
	}

	public StateType getType() {
		return m_type;
	}

	public GameBoard getBoard() {
		return m_context.getGameBoard();
	}

	public long getShowSolutionRemainingTime() {
		return 0;
	}

	public void setContext(GameStateContext context) {
		m_context = context;
	}
	
	public void onEntered() {
	}

	public void onExited() {
	}

	protected List<Robot> getAllRobots() {
		return getEntityManager().getAllEntitiesByType(Robot.class);
	}

	protected TargetId getCurrentTargetId() {
		return m_context.getCurrentTargetId();
	}

	protected PlayerSolutionManager getSolutionManager() {
		return m_context.getSolutionManager();
	}

	protected void onTargetReached() {
		if (m_context.anyTargetLeft()) {
			m_context.selectNextTarget();
			gotoState(new WaitingAllPlayersReadyState());
			return;
		}

		gotoState(new GameCompletedState());
	}

	protected boolean selectPlayerWithBestSolution() {
		PlayerClaimedSolution solution = getSolutionManager().getBestClaimedUnvalidatedSolution();

		if (solution == null || solution.getPlayerId() == m_context.getCurrentPlayerId()) {
			m_context.setCurrentPlayerId(null);
			return false;
		}

		m_context.setCurrentPlayerId(solution.getPlayerId());
		return true;
	}

}
