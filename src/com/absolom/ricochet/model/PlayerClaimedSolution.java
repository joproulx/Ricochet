package com.absolom.ricochet.model;

import java.io.Serializable;

public class PlayerClaimedSolution implements Serializable {
	private static final long serialVersionUID = 1L;
	private final PlayerId m_playerId;
	private final int m_claimedMoveCount;

	public PlayerClaimedSolution(PlayerId playerId, int claimedMoveCount) {
		m_claimedMoveCount = claimedMoveCount;
		m_playerId = playerId;
	}

	public PlayerId getPlayerId() {
		return m_playerId;
	}

	public int getClaimedMoveCount() {
		return m_claimedMoveCount;
	}

	public boolean isBetterThan(PlayerSolution other) {
		return m_claimedMoveCount < other.getClaimedMoveCount();
	}

}
