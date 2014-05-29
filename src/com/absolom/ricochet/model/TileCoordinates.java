package com.absolom.ricochet.model;

import com.absolom.utility.common.Coordinates;

public class TileCoordinates extends Coordinates<Integer> {
	
	private static final long serialVersionUID = 1L;

	public TileCoordinates(int x, int y) {
		super(x, y);
	}
	
	public TileCoordinates(Coordinates<Integer> copy) {
		super(copy);
	}

}
