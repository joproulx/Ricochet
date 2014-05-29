package com.absolom.ricochet.message.command;

import com.absolom.ricochet.model.common.StateType;
import com.absolom.utility.messaging.Message;

public class GetCurrentStateCommand extends Message {
	private static final long serialVersionUID = 1L;

	public static class Result extends BasicCommandResult<StateType> {
		public Result(StateType stateType) {
			super(stateType);
		}
	}

}
