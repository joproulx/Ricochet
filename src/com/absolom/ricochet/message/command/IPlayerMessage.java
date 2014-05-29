package com.absolom.ricochet.message.command;

import com.absolom.ricochet.model.PlayerId;
import com.absolom.utility.messaging.IMessage;

public interface IPlayerMessage extends IMessage {
	public PlayerId getPlayerId();
}
