package com.absolom.ricochet.ui.mappers;

import com.absolom.ricochet.model.Entity;
import com.absolom.ricochet.ui.EntityUserControl;

public interface IEntityToControlMapper<T extends Entity, U extends EntityUserControl> {
	public U mapEntityToControl(T entity, U control);
}
