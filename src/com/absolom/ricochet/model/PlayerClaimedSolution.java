package com.absolom.ricochet.model;

public class PlayerClaimedSolution {
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
