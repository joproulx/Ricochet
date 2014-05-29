package com.absolom.ricochet.message.event;

import com.absolom.ricochet.model.common.StateType;
import com.absolom.utility.messaging.Message;

public class StateChangedEvent extends Message {

	private static final long serialVersionUID = 1L;
	private final StateType m_newState;
	private final StateType m_previousState;

	public StateChangedEvent(StateType previousState, StateType newState) {
		m_newState = newState;
		m_previousState = previousState;
	}

	public StateType getNewStateType() {
		return m_newState;
	}

	public StateType getPreviousStateType() {
		return m_previousState;
	}
}
