package com.absolom.ricochet.ui.mappers;

import com.absolom.ricochet.model.Target;
import com.absolom.ricochet.ui.TargetControl;

public class TargetToControlMapper implements IEntityToControlMapper<Target, TargetControl> {

	public TargetControl mapEntityToControl(Target target, TargetControl control) {
		if (control == null) {
			control = new TargetControl(target.getColor(), target.getTargetId());
		}

		return control;
	}
	
}
