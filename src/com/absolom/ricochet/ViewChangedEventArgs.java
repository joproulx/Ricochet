package com.absolom.ricochet;

import com.absolom.ricochet.ui.IView;
import com.absolom.utility.common.EventArgs;

public class ViewChangedEventArgs extends EventArgs {
	private final IView m_view;

	public ViewChangedEventArgs(IView view) {
		m_view = view;
	}

	public IView getView() {
		return m_view;
	}

}
