package com.absolom.ricochet.ui.mappers;

import com.absolom.ricochet.model.Target;
import com.absolom.ricochet.model.TargetId;
import com.absolom.ricochet.ui.controls.TargetControl;

public class TargetToControlMapper implements IEntityToControlMapper<Target, TargetId, TargetControl> {

	@Override
	public TargetControl mapEntityToControl(Target target, TargetControl control) {
		if (control == null) {
			control = new TargetControl(target.getColor(), target.getEntityId());
		}

		return control;
	}
	
}
