package com.absolom.ricochet.model;

import java.util.UUID;

public class PlayerId extends EntityId {

	private static final long serialVersionUID = 1L;

	public PlayerId(UUID id) {
		super(id);
	}

	public static PlayerId generate() {
		return new PlayerId(UUID.randomUUID());
	}
}
