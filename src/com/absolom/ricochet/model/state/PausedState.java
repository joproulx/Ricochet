package com.absolom.ricochet.model.state;

import com.absolom.ricochet.message.command.IPlayerMessage;
import com.absolom.ricochet.message.command.ResumeGameCommand;
import com.absolom.ricochet.message.event.PlayerRemovedEvent;
import com.absolom.ricochet.model.PlayerId;
import com.absolom.ricochet.model.common.StateType;
import com.absolom.utility.messaging.IMessageHandler;
import com.absolom.utility.messaging.IMessageResult;
import com.absolom.utility.messaging.MessageHandler;
import com.absolom.utility.messaging.MessageResult;

public class PausedState extends State {
	private final PlayerId m_pauseOrigin;
	private final State m_previousState;

	public PausedState(PlayerId pauseOrigin, State previousState) {
		super(StateType.Paused);
		m_pauseOrigin = pauseOrigin;
		m_previousState = previousState;
	}

	public @Override
	void onEntered() {
		super.onEntered();
		registerMessageReceiver(ResumeGameCommand.class, onPlayerMessage);
		registerMessageReceiver(PlayerRemovedEvent.class, onPlayerMessage);
	}

	public @Override
	void onExited() {
		super.onExited();
		unregisterMessageReceiver(onPlayerMessage);
	}

	private IMessageHandler onPlayerMessage = new MessageHandler<IPlayerMessage>(this) {
		public @Override
		IMessageResult onMessage(IPlayerMessage message) {
			if (m_pauseOrigin.equals(message.getPlayerId())) {
				resume();
			}
			return MessageResult.success();
		}
	};

	private void resume() {
		gotoState(m_previousState);
	}
}
