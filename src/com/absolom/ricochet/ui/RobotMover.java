package com.absolom.ricochet.ui;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import android.graphics.PointF;

import com.absolom.ricochet.model.RobotAvailableMoves;
import com.absolom.ricochet.model.RobotMove;
import com.absolom.ricochet.model.TileCoordinates;
import com.absolom.ricochet.model.TilePath;
import com.absolom.ricochet.ui.controls.GameBoardControl;
import com.absolom.ricochet.ui.controls.RobotControl;
import com.absolom.utility.common.Direction;
import com.absolom.utility.common.PointCoordinates;

public class RobotMover {
	private GameBoardControl m_gameBoardControl;
	private RobotControl m_robotMoving;
	private PositionPath m_path;
	private float m_currentPathRatio;
	//private float m_speed;
	private long m_lastElapsed;
	private final float m_constantSpeed = 2000F;
	ReadWriteLock m_readWriteLock = new ReentrantReadWriteLock();

	public RobotMover(GameBoardControl gameBoardControl) {
		m_gameBoardControl = gameBoardControl;
	}

	private void startMoveRobot(RobotControl robotControl) {
		stopMoveRobot();
		m_robotMoving = robotControl;
		m_robotMoving.setMoving(true);
	}

	private void stopMoveRobot() {
		if (m_robotMoving != null) {
			m_robotMoving.setMoving(false);
			m_robotMoving = null;
		}

		m_path = null;
		m_currentPathRatio = 0F;
		//m_speed = 0F;
		m_lastElapsed = 0;
	}

	private boolean isRobotMoving() {
		return m_robotMoving != null;
	}

	public Direction fling(RobotControl robotControl, float velocityX, float velocityY) {
		m_readWriteLock.writeLock().lock();

		try {
			startMoveRobot(robotControl);

			RobotAvailableMoves moves = m_robotMoving.getAvailableMoves();

			Direction direction = getDirection(velocityX, velocityY);

//			if (direction == Direction.Right || direction == Direction.Left) {
//				m_speed = Math.abs(velocityX);
//			} else if (direction == Direction.Up || direction == Direction.Down) {
//				m_speed = Math.abs(velocityY);
//			}

			//m_speed = m_constantSpeed;
					
			if (!moves.isDirectionAvailable(direction)) {
				return Direction.None;
			}

			RobotMove move = moves.getMove(direction);
			m_path = convert(move.getPath());
			return direction;
			
		} 
		finally {
			m_readWriteLock.writeLock().unlock();
		}
	}

	private PositionPath convert(TilePath path) {
		List<TileCoordinates> wayPoints = path.getAllWayPoints();

		PositionPath positionPath = new PositionPath();

		for (TileCoordinates wayPoint : wayPoints) {
			positionPath.addWayPoint(convert(wayPoint));
		}

		return positionPath;
	}

	private PointCoordinates convert(TileCoordinates coordinates) {
		PointF point = m_gameBoardControl.getTilePosition(coordinates);
		return new PointCoordinates(point);
	}

	private Direction getDirection(float velocityX, float velocityY) {
		Direction directionX = velocityX > 0 ? Direction.Right : Direction.Left;
		Direction directionY = velocityY > 0 ? Direction.Down : Direction.Up;

		return Math.abs(velocityX) > Math.abs(velocityY) ? directionX : directionY;
	}

	public void update(long elapsedMilliseconds) {
		m_readWriteLock.readLock().lock();

		try {

			if (!isRobotMoving()) {
				return;
			}

			if (m_lastElapsed == 0) {
				m_lastElapsed = elapsedMilliseconds;
				return;
			}
			
			

			float length = m_path.getLength();

			float diffRatio = (m_constantSpeed * elapsedMilliseconds) / (length * 1000F);

			m_currentPathRatio += diffRatio;

			
			if (m_currentPathRatio >= 1F) {
				m_currentPathRatio = 1F;
			}

			PointF position = m_path.getPositionFromRatio(m_currentPathRatio);

			m_robotMoving.setMovingPosition(position);

			if (m_currentPathRatio > 1F ||  Math.abs(m_currentPathRatio - 1F) < 0.00001F) {
				stopMoveRobot();
			}
			
			m_lastElapsed = elapsedMilliseconds;
		} finally {
			m_readWriteLock.readLock().unlock();

		}
	}
	// public void moveRobot(float fromX, float fromY, float toX, float toY) {
	// if (m_robotMoving == null)
	// {
	// return;
	// }
	//
	// Direction direction = getDirection(fromX, fromY, toX, toY);
	//
	// if (direction == Direction.None)
	// {
	// return;
	// }
	//
	// RobotMove move = m_robotMoving.getAvailableMoves().getMove(direction);
	//
	// if (move == null)
	// {
	// return;
	// }
	//
	// Path path = move.getPath();
	//
	// path.
	//
	// }
	//
	// private Direction getDirection(float fromX, float fromY, float toX, float
	// toY)
	// {
	// if (m_direction == Direction.None)
	// {
	// m_direction = findDirection(fromX, fromY, toX, toY);
	// }
	//
	// return m_direction;
	// }
	//
	// private Direction findDirection(float fromX, float fromY, float toX,
	// float toY) {
	// RobotAvailableMoves moves = m_robotMoving.getAvailableMoves();
	//
	// float diffX = toX - fromX;
	// float diffY = toY - fromY;
	//
	// if (diffX > 0 && diffY > 0 )
	// {
	// return diffX >= diffY ? chooseDirection(moves, Direction.Right,
	// Direction.Down) :
	// chooseDirection(moves, Direction.Down, Direction.Right);
	// }
	//
	// if (diffX > 0 && diffY < 0 )
	// {
	// return diffX >= -diffY ? chooseDirection(moves, Direction.Right,
	// Direction.Up) :
	// chooseDirection(moves, Direction.Up, Direction.Right);
	// }
	//
	// if (diffX < 0 && diffY > 0 )
	// {
	// return -diffX >= diffY ? chooseDirection(moves, Direction.Left,
	// Direction.Down) :
	// chooseDirection(moves, Direction.Down, Direction.Left);
	// }
	//
	// if (diffX < 0 && diffY < 0 )
	// {
	// return -diffX >= -diffY ? chooseDirection(moves, Direction.Left,
	// Direction.Up) :
	// chooseDirection(moves, Direction.Up, Direction.Left);
	// }
	//
	// return Direction.None;
	// }
	//
	// private Direction chooseDirection(RobotAvailableMoves moves, Direction
	// firstDirection, Direction secondDirection)
	// {
	// if (moves.isDirectionAvailable(firstDirection))
	// {
	// return firstDirection;
	// }
	//
	// if (moves.isDirectionAvailable(secondDirection))
	// {
	// return secondDirection;
	// }
	//
	// return Direction.None;
	// }

}
