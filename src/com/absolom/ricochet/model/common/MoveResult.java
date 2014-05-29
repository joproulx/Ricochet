package com.absolom.ricochet.model.common;

import java.io.Serializable;

import com.absolom.ricochet.model.PlayerId;
import com.absolom.ricochet.model.RobotMove;

// Immutable class
public final class MoveResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private final boolean m_isCompleted;
	private final MoveErrorType m_error;
	private final RobotMove m_move;
	private final PlayerId m_playerId;

	public RobotMove getMove() {
		return m_move;
	}

	public PlayerId getPlayerId() {
		return m_playerId;
	}

	public boolean isSucceeded() {
		return m_error == MoveErrorType.None;
	}

	public MoveErrorType getError() {
		return m_error;
	}

	public boolean isCompleted() {
		return m_isCompleted;
	}

	public MoveResult(PlayerId playerId, MoveErrorType error, RobotMove move) {
		this(playerId, error, false, move);
	}

	public MoveResult(PlayerId playerId, boolean isCompleted, RobotMove move) {
		this(playerId, MoveErrorType.None, isCompleted, move);
	}

	public MoveResult(PlayerId playerId, MoveErrorType error, boolean isCompleted, RobotMove move) {
		m_isCompleted = isCompleted;
		m_error = error;
		m_move = new RobotMove(move);
		m_playerId = playerId;
	}

}
