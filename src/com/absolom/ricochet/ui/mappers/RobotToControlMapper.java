package com.absolom.ricochet.ui.mappers;

import com.absolom.ricochet.model.EntityManager;
import com.absolom.ricochet.model.Robot;
import com.absolom.ricochet.model.RobotId;
import com.absolom.ricochet.model.RobotPathsBuilder;
import com.absolom.ricochet.ui.controls.RobotControl;

public class RobotToControlMapper implements IEntityToControlMapper<Robot, RobotId, RobotControl> {
	EntityManager m_entityManager;
	
	public RobotToControlMapper(EntityManager entityManager)
	{
		m_entityManager = entityManager;
	}
	
	@Override
	public RobotControl mapEntityToControl(Robot robot, RobotControl control) {
		if (control == null) {
			control = new RobotControl(robot.getEntityId(), robot.getColor());
		}

		control.setAvailableMoves(RobotPathsBuilder.buildRobotPaths(robot.getEntityId(), robot.getCoordinates(), m_entityManager));
		
		return control;
	}
}
