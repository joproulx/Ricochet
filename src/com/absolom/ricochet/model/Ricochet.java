package com.absolom.ricochet.model;

import java.util.UUID;

import com.absolom.ricochet.model.common.GameColor;
import com.absolom.utility.data.IDocumentEntry;

public class Ricochet extends GameBoardEntity<RicochetId> {

	private static final long serialVersionUID = 1L;

	public Ricochet(IDocumentEntry data) {
		super(data);
	}

	@Override
	protected RicochetId toEntityId(UUID id) {
		return new RicochetId(id);
	}

	public GameColor getColor() {
		return getData().getValue("color");
	}

	public RicochetType getType() {
		return getData().getValue("ricochetType");
	}

	public void setColor(GameColor color) {
		getData().setValue("color", color);
	}

}
