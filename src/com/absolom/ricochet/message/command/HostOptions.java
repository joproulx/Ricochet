package com.absolom.ricochet.message.command;

import java.util.EnumSet;

public enum HostOptions {
	None, AcceptTcp, AcceptBluetooth;

	static public EnumSet<HostOptions> TcpAndBluetooth = EnumSet.of(HostOptions.AcceptTcp, HostOptions.AcceptBluetooth);
	static public EnumSet<HostOptions> Tcp = EnumSet.of(HostOptions.AcceptTcp);
	static public EnumSet<HostOptions> Bluetooth = EnumSet.of(HostOptions.AcceptBluetooth);
}
