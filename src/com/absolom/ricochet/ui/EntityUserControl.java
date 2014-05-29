package com.absolom.ricochet.ui;

import com.absolom.ricochet.model.EntityId;

public abstract class EntityUserControl extends UserControl {

	private final EntityId m_entityId;
	
	public EntityUserControl(EntityId entityId)
	{
		m_entityId = entityId;
	}

	public EntityId getEntityId() {
		return m_entityId;
	}

	
}
