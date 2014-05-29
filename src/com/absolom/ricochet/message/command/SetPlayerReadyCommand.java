package com.absolom.ricochet.message.command;

import com.absolom.ricochet.model.PlayerId;

public class SetPlayerReadyCommand extends PlayerCommand {
	private static final long serialVersionUID = 1L;

	public SetPlayerReadyCommand(PlayerId playerId) {
		super(playerId);
	}
}
