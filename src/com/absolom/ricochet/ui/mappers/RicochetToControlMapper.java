package com.absolom.ricochet.ui.mappers;

import com.absolom.ricochet.model.Ricochet;
import com.absolom.ricochet.ui.RicochetControl;

public class RicochetToControlMapper implements IEntityToControlMapper<Ricochet, RicochetControl>{

	public RicochetControl mapEntityToControl(Ricochet ricochet, RicochetControl control) {
		if (control == null) {
			control = new RicochetControl(ricochet.getRicochetId(), ricochet.getColor(), ricochet.getType());
		}

		return control;
	}
}
