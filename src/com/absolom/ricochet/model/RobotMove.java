package com.absolom.ricochet.model;

import java.io.Serializable;

public final class RobotMove implements Serializable {

	private static final long serialVersionUID = 1L;
	private final RobotId m_robotId;
	private final TilePath m_path;

	public RobotMove(RobotId robotId, TilePath path) {
		m_robotId = robotId;
		m_path = path;
	}

	public RobotMove(RobotMove copy) {
		m_robotId = copy.m_robotId;
		m_path = new TilePath(copy.m_path);
	}

	public static RobotMove createEmpty(RobotId robotId) {
		return new RobotMove(robotId, new TilePath());
	}

	public boolean isRevert(RobotMove robotMove) {
		return getPath().isRevert(robotMove.getPath());
	}

	public TilePath getPath() {
		return m_path;
	}

	public RobotId getRobotId() {
		return m_robotId;
	}

	public boolean isComplete() {
		return m_path.isComplete();
	}
}
