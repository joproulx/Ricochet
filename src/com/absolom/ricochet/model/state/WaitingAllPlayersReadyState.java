package com.absolom.ricochet.model.state;

import java.util.HashSet;

import com.absolom.ricochet.message.command.IPlayerMessage;
import com.absolom.ricochet.message.command.SetPlayerReadyCommand;
import com.absolom.ricochet.message.event.PlayerRemovedEvent;
import com.absolom.ricochet.model.EntityId;
import com.absolom.ricochet.model.Player;
import com.absolom.ricochet.model.PlayerId;
import com.absolom.ricochet.model.common.StateType;
import com.absolom.utility.messaging.IMessageHandler;
import com.absolom.utility.messaging.IMessageResult;
import com.absolom.utility.messaging.MessageHandler;
import com.absolom.utility.messaging.MessageResult;

public class WaitingAllPlayersReadyState extends State {
	private HashSet<EntityId> m_standbyPlayers;

	public WaitingAllPlayersReadyState() {
		super(StateType.WaitingAllPlayersReady);
	}

	public @Override
	void onEntered() {
		super.onEntered();
		registerMessageReceiver(SetPlayerReadyCommand.class, onPlayerReady);
		registerMessageReceiver(PlayerRemovedEvent.class, onPlayerReady);

		m_standbyPlayers = new HashSet<EntityId>(getEntityManager().getAllEntityIdByType(Player.class));
	}

	public @Override
	void onExited() {
		super.onExited();
		unregisterMessageReceiver(onPlayerReady);
	}

	private IMessageHandler onPlayerReady = new MessageHandler<IPlayerMessage>(this) {
		public @Override
		IMessageResult onMessage(IPlayerMessage message) {
			removeStandbyPlayer(message.getPlayerId());
			return MessageResult.success();
		}
	};

	private void removeStandbyPlayer(PlayerId playerId) {
		m_standbyPlayers.remove(playerId);

		// All players ready?
		if (m_standbyPlayers.isEmpty()) {
			gotoState(new FindingSolutionState());
		}
	}

}
