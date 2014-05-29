package com.absolom.ricochet.message.command;

import com.absolom.ricochet.model.PlayerId;
import com.absolom.ricochet.model.RobotId;
import com.absolom.ricochet.model.common.MoveResult;
import com.absolom.utility.common.Direction;
import com.absolom.utility.messaging.IMessageResult;

public class MoveRobotCommand extends PlayerCommand {
	private static final long serialVersionUID = 1L;
	private final RobotId m_robotId;
	private final Direction m_direction;

	public static class Result implements IMessageResult {
		private final MoveResult m_moveResult;

		public Result(MoveResult moveResult) {
			m_moveResult = moveResult;
		}

		public @Override
		boolean IsSuccess() {
			return m_moveResult.isSucceeded();
		}
	}

	public MoveRobotCommand(PlayerId playerId, RobotId robotId, Direction direction) {
		super(playerId);
		m_robotId = robotId;
		m_direction = direction;
	}

	public RobotId getRobotId() {
		return m_robotId;
	}

	public Direction getDirection() {
		return m_direction;
	}
}
