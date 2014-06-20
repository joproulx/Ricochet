package com.absolom.ricochet.model.state;

import com.absolom.ricochet.message.command.ClaimSolutionCommand;
import com.absolom.ricochet.model.common.StateType;
import com.absolom.utility.messaging.IMessageHandler;
import com.absolom.utility.messaging.IMessageResult;
import com.absolom.utility.messaging.MessageHandler;
import com.absolom.utility.messaging.MessageResult;

public class FindingSolutionState extends PausableState {
	public FindingSolutionState() {
		super(StateType.ShowingNextTarget);
	}

	public @Override
	void onEntered() {
		super.onEntered();
		registerMessageReceiver(ClaimSolutionCommand.class, onClaimSolutionCommand);
	}

	public @Override
	void onExited() {
		super.onExited();
		unregisterMessageReceiver(onClaimSolutionCommand);
	}

	private IMessageHandler onClaimSolutionCommand = new MessageHandler<ClaimSolutionCommand>(this) {
		public @Override
		IMessageResult onMessage(ClaimSolutionCommand command) {
			getSolutionManager().addClaimedSolution(command.getPlayerId(), command.getMoveCount());
			if (selectPlayerWithBestSolution()){
				gotoState(new ShowingSolutionState());
				return MessageResult.success();
			}
			// TODO: Return error
			return MessageResult.success();
		}
	};
}
