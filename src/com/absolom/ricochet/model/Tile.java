package com.absolom.ricochet.model;

import java.util.EnumSet;

import com.absolom.utility.common.Direction;

public class Tile {
	private int m_x;
	private int m_y;

	private EnumSet<Direction> m_walls;
	private RobotId m_robotId;
	private RicochetId m_ricochetId;
	private TargetId m_targetId;

	public Tile(int x, int y) {
		m_x = x;
		m_y = y;
		m_walls = EnumSet.noneOf(Direction.class);
	}

	public TileCoordinates getCoordinates() {
		return new TileCoordinates(m_x, m_y);
	}

	public boolean isWalled(Direction direction) {
		return m_walls.contains(direction);
	}
	
	public EnumSet<Direction> getWalls() {
		return m_walls;
	}
	
	public void setWalled(Direction direction) {
		setWalled(EnumSet.of(direction));
	}
	
	public void setWalled(EnumSet<Direction> directions) {
		m_walls = directions;
	}

	public int getX() {
		return m_x;
	}

	public int getY() {
		return m_y;
	}

	public void addEntityId(EntityId entityId) {
		if (entityId instanceof RobotId) {
			m_robotId = (RobotId) entityId;
		}
		if (entityId instanceof RicochetId) {
			m_ricochetId = (RicochetId) entityId;
		}
		if (entityId instanceof TargetId) {
			m_targetId = (TargetId) entityId;
		}
	}

	public void removeItem(EntityId itemId) {
		if (itemId instanceof RobotId) {
			m_robotId = null;
		}
		if (itemId instanceof RicochetId) {
			m_ricochetId = null;
		}
		if (itemId instanceof TargetId) {
			m_targetId = null;
		}
	}

	public boolean hasRobot() {
		return m_robotId != null;
	}

	public RobotId getRobotId() {
		return m_robotId;
	}

	public void setRobotId(RobotId robotId) {
		m_robotId = robotId;
	}

	public boolean hasRicochet() {
		return m_ricochetId != null;
	}

	public RicochetId getRicochetId() {
		return m_ricochetId;
	}

	public void setRicochetId(RicochetId ricochetId) {
		m_ricochetId = ricochetId;
	}

	public boolean hasTargetId() {
		return m_targetId != null;
	}

	public TargetId getTargetId() {
		return m_targetId;
	}

	public void setTargetId(TargetId targetId) {
		m_targetId = targetId;
	}
}
