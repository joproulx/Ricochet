package com.absolom.ricochet.model;

import java.util.UUID;

import com.absolom.utility.data.IDocumentEntry;

public class GameStateEntity extends Entity<GameStateEntityId> {

	private static final long serialVersionUID = 1L;

	public PlayerClaimedSolution getCurrentSolution() {
		return getData().getValue("currentSolution");
	}

	public void setCurrentSolution(PlayerClaimedSolution currentSolution) {
		getData().setValue("currentSolution", currentSolution);
	}

	public GameStateEntity(IDocumentEntry data) {
		super(data);
	}

	@Override
	protected GameStateEntityId toEntityId(UUID id) {
		return GameStateEntityId.getInstance();
	}

	
	
}
