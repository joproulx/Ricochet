package com.absolom.ricochet.message.command;

import com.absolom.utility.messaging.MessageResult;

public class BasicCommandResult<T> extends MessageResult {
	private T m_value;

	public BasicCommandResult(T value) {
		m_value = value;
	}

	public T getValue() {
		return m_value;
	}
}
