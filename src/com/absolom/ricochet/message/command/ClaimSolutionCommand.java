package com.absolom.ricochet.message.command;

import com.absolom.ricochet.model.PlayerId;

public class ClaimSolutionCommand extends PlayerCommand {
	private static final long serialVersionUID = 1L;

	private final int m_moveCount;

	public ClaimSolutionCommand(PlayerId playerId, int moveCount) {
		super(playerId);
		m_moveCount = moveCount;
	}

	public int getMoveCount() {
		return m_moveCount;
	}

}
