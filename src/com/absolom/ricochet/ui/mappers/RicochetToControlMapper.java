package com.absolom.ricochet.ui.mappers;

import com.absolom.ricochet.model.Ricochet;
import com.absolom.ricochet.model.RicochetId;
import com.absolom.ricochet.ui.controls.RicochetControl;

public class RicochetToControlMapper implements IEntityToControlMapper<Ricochet, RicochetId, RicochetControl>{

	@Override
	public RicochetControl mapEntityToControl(Ricochet ricochet, RicochetControl control) {
		if (control == null) {
			control = new RicochetControl(ricochet.getEntityId(), ricochet.getColor(), ricochet.getType());
		}

		return control;
	}
}
