package com.absolom.ricochet.model;

import java.util.UUID;

public class GameStateEntityId extends EntityId {

	private static final long serialVersionUID = 1L;

	private static final UUID m_id = UUID.fromString("4effa552-02ff-455b-8b0b-9ce278b2182d");
	private static GameStateEntityId m_instance;
	private GameStateEntityId() {
		super(m_id);
	}

	public static GameStateEntityId getInstance(){
		if (m_instance == null){
			m_instance = new GameStateEntityId();
		}
		
		return m_instance;
	}
}
