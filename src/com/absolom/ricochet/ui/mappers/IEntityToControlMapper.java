package com.absolom.ricochet.ui.mappers;

import com.absolom.ricochet.model.Entity;
import com.absolom.ricochet.model.EntityId;
import com.absolom.ricochet.ui.controls.EntityUserControl;

public interface IEntityToControlMapper<T extends Entity<U>, U extends EntityId, V extends EntityUserControl> {
	public V mapEntityToControl(T entity, V control);
}
