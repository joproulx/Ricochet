package com.absolom.ricochet.model;

import com.absolom.utility.data.IDocumentEntry;

public abstract class GameBoardEntity<T extends EntityId> extends Entity<T> {

	private static final long serialVersionUID = 1L;

	public GameBoardEntity(IDocumentEntry data) {
		super(data);
	}

	public void setCoordinates(TileCoordinates coordinates) {
		getData().setValue("coordinates", coordinates);
	}

	public TileCoordinates getCoordinates() {
		return getData().getValue("coordinates");
	}

}
