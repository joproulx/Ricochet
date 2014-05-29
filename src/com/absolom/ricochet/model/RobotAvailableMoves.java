package com.absolom.ricochet.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.absolom.utility.common.Direction;

public class RobotAvailableMoves {

	private final HashMap<Direction, RobotMove> m_availableMoves;

	public RobotAvailableMoves() {
		m_availableMoves = new HashMap<Direction, RobotMove>();
	}

	public RobotAvailableMoves(RobotAvailableMoves copy) {
		m_availableMoves = new HashMap<Direction, RobotMove>(copy.m_availableMoves);
	}

	public RobotMove getMove(Direction direction) {
		return m_availableMoves.get(direction);
	}
	
	public List<RobotMove> getAllMoves() {
		return new ArrayList<RobotMove>(m_availableMoves.values());
	}

	public boolean isDirectionAvailable(Direction direction) {
		return getMove(direction) != null;
	}

	public void setAvailableMove(Direction direction, RobotMove move) {
		m_availableMoves.put(direction, move);
	}

}
