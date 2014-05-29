package com.absolom.ricochet.message.event;

import com.absolom.ricochet.model.PlayerId;

public class PlayerRemovedEvent extends PlayerEvent {

	private static final long serialVersionUID = 1L;

	public PlayerRemovedEvent(PlayerId playerId) {
		super(playerId);
	}

}
