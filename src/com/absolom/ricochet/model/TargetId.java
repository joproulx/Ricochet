package com.absolom.ricochet.model;

import java.util.UUID;

public class TargetId extends EntityId {

	private static final long serialVersionUID = 1L;

	public TargetId(UUID id) {
		super(id);
	}

	public static TargetId generate() {
		return new TargetId(UUID.randomUUID());
	}

}
