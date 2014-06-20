package com.absolom.ricochet.ui;

import com.absolom.utility.common.EventArgs;

public class ClaimFormEventArgs extends EventArgs {
	private final int m_claimedCount;

	public int getClaimedCount() {
		return m_claimedCount;
	}

	public ClaimFormEventArgs(int m_claimedCount) {
		this.m_claimedCount = m_claimedCount;
	}
	
	
}
