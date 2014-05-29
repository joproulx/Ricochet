package com.absolom.ricochet.ui.mappers;

import com.absolom.ricochet.model.EntityManager;
import com.absolom.ricochet.model.Robot;
import com.absolom.ricochet.model.RobotPathsBuilder;
import com.absolom.ricochet.ui.RobotControl;

public class RobotToControlMapper implements IEntityToControlMapper<Robot, RobotControl> {
	EntityManager m_entityManager;
	
	public RobotToControlMapper(EntityManager entityManager)
	{
		m_entityManager = entityManager;
	}
	
	public RobotControl mapEntityToControl(Robot robot, RobotControl control) {
		if (control == null) {
			control = new RobotControl(robot.getRobotId(), robot.getColor());
		}

		control.setAvailableMoves(RobotPathsBuilder.buildRobotPaths(robot.getRobotId(), robot.getCoordinates(), m_entityManager));
		
		return control;
	}
}
