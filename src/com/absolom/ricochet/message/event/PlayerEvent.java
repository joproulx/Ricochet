package com.absolom.ricochet.message.event;

import com.absolom.ricochet.message.command.IPlayerMessage;
import com.absolom.ricochet.model.PlayerId;
import com.absolom.utility.messaging.Message;

public class PlayerEvent extends Message implements IPlayerMessage {

	private static final long serialVersionUID = 1L;
	private final PlayerId m_playerId;

	public PlayerEvent(PlayerId playerId) {
		m_playerId = playerId;
	}

	public @Override
	PlayerId getPlayerId() {
		return m_playerId;
	}

}
