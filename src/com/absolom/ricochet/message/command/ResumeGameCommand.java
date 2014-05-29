package com.absolom.ricochet.message.command;

import com.absolom.ricochet.model.PlayerId;

public class ResumeGameCommand extends PlayerCommand {
	private static final long serialVersionUID = 1L;

	public ResumeGameCommand(PlayerId playerId) {
		super(playerId);
	}

}
