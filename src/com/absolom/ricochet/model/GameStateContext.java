package com.absolom.ricochet.model;

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import com.absolom.ricochet.config.Configuration;
import com.absolom.ricochet.config.IConfiguration;
import com.absolom.ricochet.message.event.StateChangedEvent;
import com.absolom.ricochet.model.common.StateType;
import com.absolom.ricochet.model.state.State;
import com.absolom.utility.common.*;
import com.absolom.utility.messaging.MessageTransceiver;

public class GameStateContext {
	protected State m_currentState;

	protected PlayerId m_currentPlayerId;

	protected ArrayList<TargetId> m_remainingTargets;

	protected TargetId m_currentTargetId;

	protected PlayerSolutionManager m_playerSolutionManager;

	protected EntityManager m_entityManager;

	protected WorkerThread m_workerThread;
	
	protected WorkScheduler m_workScheduler;

	protected MessageTransceiver m_messageTransceiver;
	
	private IConfiguration m_config;
	
	public GameStateContext(WorkerThread workerThread, WorkScheduler workScheduler, EntityManager entityManager, MessageTransceiver messageTransceiver) {
		m_workerThread = workerThread;
		m_workScheduler = workScheduler;
		m_entityManager = entityManager;
		m_messageTransceiver = messageTransceiver;
		m_remainingTargets = new ArrayList<TargetId>();
		m_config = new Configuration();
		m_playerSolutionManager = new PlayerSolutionManager(entityManager, m_config);
		
	}

	public EntityManager getEntityManager() {
		return m_entityManager;
	}

	public MessageTransceiver getMessageTransceiver() {
		return m_messageTransceiver;
	}

	public IConfiguration getConfig() {
		return m_config;
	}

	public void setCurrentPlayerId(PlayerId playerId) {
		m_currentPlayerId = playerId;
	}

	public PlayerSolutionManager getSolutionManager() {
		return m_playerSolutionManager;
	}

	public TargetId getCurrentTargetId() {
		return m_currentTargetId;
	}
	
	public GameBoard getGameBoard() {
		return m_entityManager.getBoard();
	}

	public void setCurrentTarget(TargetId targetId) {
		m_currentTargetId = targetId;
	}

	public void selectNextTarget() {
		m_playerSolutionManager.clear();

		ArrayList<TargetId> remainingTargets = new ArrayList<TargetId>(m_remainingTargets);
		TargetId nextTarget = remainingTargets.get(remainingTargets.size() - 1);
		remainingTargets.remove(remainingTargets.size() - 1);
		m_remainingTargets = remainingTargets;
		setCurrentTarget(nextTarget);
	}

	public boolean anyTargetLeft() {
		return m_remainingTargets.size() > 0;
	}

	public State getCurrentState() {
		return m_currentState;
	}

	public PlayerId getCurrentPlayerId() {
		return m_currentPlayerId;
	}

	public ScheduledFuture<?> startTimer(long milliseconds, final Runnable callback, final State stateFrom) {
		return m_workScheduler.schedule(new LogRunnable(new Runnable() {
			public void run() {
				if (stateFrom != m_currentState) {
					return;
				}

				callback.run();
			}
		}), milliseconds);
	}

	public void gotoState(final State nextState) {
		final GameStateContext thisObj = this;

		m_workerThread.execute(new LogRunnable(new Runnable() {
			public void run() {
				State previousState = null;
				if (m_currentState != null) {
					previousState = m_currentState;
					previousState.onExited();
				}

				m_currentState = nextState;
				m_currentState.setContext(thisObj);
				m_currentState.onEntered();

				StateType previousStateType = previousState != null ? previousState.getType() : StateType.None;

				getMessageTransceiver().postAndWait(this, new StateChangedEvent(previousStateType, m_currentState.getType()));
			}
		}));
	}

	

}
