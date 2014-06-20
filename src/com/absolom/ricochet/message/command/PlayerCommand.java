package com.absolom.ricochet.message.command;

import com.absolom.ricochet.model.PlayerId;
import com.absolom.utility.messaging.Message;

public abstract class PlayerCommand extends Message implements IPlayerMessage {
	private static final long serialVersionUID = 1L;
	private final PlayerId m_playerId;

	public PlayerCommand(PlayerId playerId) {
		m_playerId = playerId;
	}

	@Override
	public PlayerId getPlayerId() {
		return m_playerId;
	}
}
