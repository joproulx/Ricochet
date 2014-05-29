package com.absolom.ricochet.model;

import java.util.UUID;

public class RicochetId extends EntityId {

	private static final long serialVersionUID = 1L;

	public RicochetId(UUID id) {
		super(id);
	}

	public static RicochetId generate() {
		return new RicochetId(UUID.randomUUID());
	}
}
