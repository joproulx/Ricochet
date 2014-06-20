package com.absolom.ricochet.model;

import com.absolom.ricochet.model.common.GameColor;
import com.absolom.utility.common.Direction;

public class RobotPathsBuilder {
	
	public static RobotAvailableMoves buildRobotPaths(RobotId robotId,
											   TileCoordinates startTile, 
			                                   EntityManager entityManager) {
		
		Tile currentTile = entityManager.getBoard().getTile(startTile);
		RobotAvailableMoves paths = new RobotAvailableMoves();
		Robot robot = entityManager.getEntity(robotId);
		buildRobotPaths(paths, entityManager, startTile, robot, currentTile, Direction.Up);
		buildRobotPaths(paths, entityManager, startTile, robot, currentTile, Direction.Down);
		buildRobotPaths(paths, entityManager, startTile, robot, currentTile, Direction.Right);
		buildRobotPaths(paths, entityManager, startTile, robot, currentTile, Direction.Left);
		return paths;
	}

	private static void buildRobotPaths(RobotAvailableMoves robotPaths, 
			                     EntityManager entityManager,
								 TileCoordinates coord, 
			                     Robot robot, 
			                     Tile currentTile, 
			                     Direction direction) {
		TilePath path = new TilePath(coord);
		buildPath(path, entityManager, robot.getColor(), currentTile, direction);

		robotPaths.setAvailableMove(direction, new RobotMove(robot.getEntityId(), path));
	}

	private static void buildPath(TilePath path, EntityManager entityManager, GameColor robotColor, Tile currentTile, Direction direction) {
		Tile nextTile = entityManager.getBoard().getNextTile(currentTile, direction);

		if (currentTile.isWalled(direction) || nextTile == null || nextTile.hasRobot()) {
			path.addWayPoint(currentTile.getCoordinates());
			return;
		}

		if (nextTile.hasRicochet()) {
			Ricochet ricochet = entityManager.getEntity(nextTile.getRicochetId());
			if (ricochet != null && !ricochet.getColor().equals(robotColor)) {
				path.addWayPoint(nextTile.getCoordinates());
				boolean isBack = ricochet.getType() == RicochetType.Backslash;
				switch(direction){
					case Down : direction = isBack ? Direction.Right : Direction.Left; break;
					case Left : direction = isBack ? Direction.Up : Direction.Down; break;
					case None : break;
					case Right : direction = isBack ? Direction.Down : Direction.Up; break;
					case Up : direction = isBack ? Direction.Left : Direction.Right; break;
					default : break;
					
				}
			}
		}

		buildPath(path, entityManager, robotColor, nextTile, direction);
	}
}
