package com.absolom.ricochet.model;

import java.util.UUID;

import com.absolom.ricochet.model.common.GameColor;
import com.absolom.utility.data.IDocumentEntry;

public class Player extends Entity {

	private static final long serialVersionUID = 1L;

	public Player(IDocumentEntry data) {
		super(data);
	}

	@Override
	protected EntityId toEntityId(UUID id) {
		return new PlayerId(id);
	}

	public GameColor getColor() {
		return getData().getValue("color");
	}

	public void setIsLocal(boolean isLocal) {
		getData().setValue("isLocal", isLocal);
	}

	public void setPlayerName(String playerName) {
		getData().setValue("name", playerName);
	}

}
