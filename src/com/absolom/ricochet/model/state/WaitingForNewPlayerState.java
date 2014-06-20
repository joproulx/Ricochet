package com.absolom.ricochet.model.state;

import com.absolom.ricochet.message.command.RegisterPlayerCommand;
import com.absolom.ricochet.message.command.StartNewGameCommand;
import com.absolom.ricochet.model.Player;
import com.absolom.ricochet.model.common.AddPlayerResult;
import com.absolom.ricochet.model.common.StateType;
import com.absolom.utility.messaging.IMessageHandler;
import com.absolom.utility.messaging.IMessageResult;
import com.absolom.utility.messaging.MessageHandler;
import com.absolom.utility.messaging.MessageResult;

public class WaitingForNewPlayerState extends State {
	public WaitingForNewPlayerState() {
		super(StateType.WaitingForNewPlayer);
	}

	public @Override
	void onEntered() {
		super.onEntered();
		registerMessageReceiver(RegisterPlayerCommand.class, onRegisterPlayerCommand);
		registerMessageReceiver(StartNewGameCommand.class, onStartGameCommand);
	}

	public @Override
	void onExited() {
		super.onExited();
		unregisterMessageReceiver(onRegisterPlayerCommand);
		unregisterMessageReceiver(onStartGameCommand);
	}

	private IMessageHandler onRegisterPlayerCommand = new MessageHandler<RegisterPlayerCommand>(this) {
		public @Override
		IMessageResult onMessage(RegisterPlayerCommand command) {
			Player player = getEntityManager().createEntity(command.getPlayerId());
			player.setPlayerName(command.getPlayerName());
			player.setIsLocal(command.isLocal());
			
			getEntityManager().applyChanges();
			
			//AddPlayerResult result = addPlayer(player) ? AddPlayerResult.Succeeded : AddPlayerResult.AlreadyExist;

			return new RegisterPlayerCommand.Result(AddPlayerResult.Succeeded);
		}
	};

	private IMessageHandler onStartGameCommand = new MessageHandler<StartNewGameCommand>(this) {
		public @Override
		IMessageResult onMessage(StartNewGameCommand command) {
			gotoState(new WaitingAllPlayersReadyState());
			return MessageResult.success();
		}
	};

}
