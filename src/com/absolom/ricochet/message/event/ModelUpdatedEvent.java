package com.absolom.ricochet.message.event;

import com.absolom.ricochet.model.Game;
import com.absolom.utility.messaging.Message;

public class ModelUpdatedEvent extends Message {

	private static final long serialVersionUID = 1L;
	private Game m_game;

	public ModelUpdatedEvent(Game game) {
		m_game = game;
	}

	public Game getGame() {
		return m_game;
	}
}
