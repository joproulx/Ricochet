package com.absolom.ricochet.model;

import java.util.UUID;

public class RobotId extends EntityId {

	private static final long serialVersionUID = 1L;

	public RobotId(UUID id) {
		super(id);
	}

	public static RobotId generate() {
		return new RobotId(UUID.randomUUID());
	}
}
