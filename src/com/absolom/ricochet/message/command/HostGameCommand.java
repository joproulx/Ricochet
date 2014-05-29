package com.absolom.ricochet.message.command;

import java.util.EnumSet;

import com.absolom.utility.messaging.Message;

public class HostGameCommand extends Message {
	private static final long serialVersionUID = 1L;

	private EnumSet<HostOptions> m_hostOptions;

	public HostGameCommand(EnumSet<HostOptions> hostOptions) {
		m_hostOptions = hostOptions;
	}

	public HostGameCommand(HostOptions hostOptions) {
		this(EnumSet.of(hostOptions));
	}

	public boolean isRemoteJoinAccepted() {
		return isBluetoothAccepted() || isTcpAccepted();
	}

	public boolean isBluetoothAccepted() {
		return m_hostOptions.contains(HostOptions.AcceptBluetooth);
	}

	public boolean isTcpAccepted() {
		return m_hostOptions.contains(HostOptions.AcceptTcp);
	}
}
