package com.absolom.ricochet.message.command;

import com.absolom.ricochet.model.GameBoard;
import com.absolom.utility.messaging.Message;

public class GetGameBoardCommand extends Message {
	private static final long serialVersionUID = 1L;

	public static class Result extends BasicCommandResult<GameBoard> {
		public Result(GameBoard gameBoard) {
			super(gameBoard);
		}
	}

}
