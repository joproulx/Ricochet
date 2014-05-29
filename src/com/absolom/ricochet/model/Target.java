package com.absolom.ricochet.model;

import java.util.UUID;

import com.absolom.ricochet.model.common.GameColor;
import com.absolom.utility.data.IDocumentEntry;

public class Target extends GameBoardItem {

	private static final long serialVersionUID = 1L;

	public Target(IDocumentEntry data) {
		super(data);
	}

	@Override
	protected EntityId toEntityId(UUID id) {
		return new TargetId(id);
	}

	public GameColor getColor() {
		return getData().getValue("color");
	}

	public boolean isShown() {
		return getData().getValue("isShown", false);
	}

	public TargetId getTargetId() {
		return (TargetId) getEntityId();
	}

	public void setColor(GameColor color) {
		getData().setValue("color", color);
	}
}
