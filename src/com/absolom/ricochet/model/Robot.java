package com.absolom.ricochet.model;

import java.util.UUID;

import com.absolom.ricochet.model.common.GameColor;
import com.absolom.utility.data.DocumentEntry;
import com.absolom.utility.data.IDocumentEntry;

public class Robot extends GameBoardEntity<RobotId> {

	private static final long serialVersionUID = 1L;

	public Robot(RobotId id) {
		this(new DocumentEntry(id.toString()));
	}

	public Robot(IDocumentEntry data) {
		super(data);
	}

	@Override
	public RobotId toEntityId(UUID id) {
		return new RobotId(id);
	}

	public GameColor getColor() {
		return getData().getValue("color");
	}

	public void setColor(GameColor color) {
		getData().setValue("color", color);
	}

}
