package com.absolom.ricochet.message.event;

import com.absolom.ricochet.model.PlayerId;

public class PlayerAddedEvent extends PlayerEvent {

	private static final long serialVersionUID = 1L;

	public PlayerAddedEvent(PlayerId playerId) {
		super(playerId);
	}
}
