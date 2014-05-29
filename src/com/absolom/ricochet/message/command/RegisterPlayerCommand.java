package com.absolom.ricochet.message.command;

import com.absolom.ricochet.model.PlayerId;
import com.absolom.ricochet.model.common.AddPlayerResult;
import com.absolom.utility.messaging.IMessageResult;
import com.absolom.utility.messaging.Message;

public class RegisterPlayerCommand extends Message {
	private static final long serialVersionUID = 1L;

	public static class Result implements IMessageResult {
		private final AddPlayerResult m_result;

		public Result(AddPlayerResult result) {
			m_result = result;
		}

		public @Override
		boolean IsSuccess() {
			return m_result == AddPlayerResult.Succeeded;
		}
	}

	private final PlayerId m_playerId;
	private final String m_playerName;
	private final boolean m_isLocal;

	public RegisterPlayerCommand(PlayerId playerId, String playerName, boolean isLocal) {
		m_playerId = playerId;
		m_playerName = playerName;
		m_isLocal = isLocal;
	}

	public boolean isLocal() {
		return m_isLocal;
	}

	public String getPlayerName() {
		return m_playerName;
	}

	public PlayerId getPlayerId() {
		return m_playerId;
	}
}
