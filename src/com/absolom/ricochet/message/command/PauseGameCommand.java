package com.absolom.ricochet.message.command;

import com.absolom.ricochet.model.PlayerId;

public class PauseGameCommand extends PlayerCommand {
	private static final long serialVersionUID = 1L;

	public PauseGameCommand(PlayerId playerId) {
		super(playerId);
	}

}
