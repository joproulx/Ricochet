package com.absolom.ricochet.model;

import com.absolom.utility.data.IDocumentEntry;

public abstract class GameBoardItem extends Entity {

	private static final long serialVersionUID = 1L;

	public GameBoardItem(IDocumentEntry data) {
		super(data);
	}

	public void setCoordinates(TileCoordinates coordinates) {
		getData().setValue("coordinates", coordinates);
	}

	public TileCoordinates getCoordinates() {
		return getData().getValue("coordinates");
	}

}
