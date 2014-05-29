package com.absolom.ricochet.message.event;

import com.absolom.ricochet.model.PlayerId;
import com.absolom.ricochet.model.RobotMove;
import com.absolom.utility.messaging.Message;

public class RobotMovedEvent extends Message {

	private static final long serialVersionUID = 1L;
	private final PlayerId m_playerId;
	private final RobotMove m_move;

	public RobotMovedEvent(PlayerId playerId, RobotMove move) {
		m_playerId = playerId;
		m_move = move;
	}

	public RobotMove getMove() {
		return m_move;
	}

	public PlayerId getPlayerId() {
		return m_playerId;
	}
}
