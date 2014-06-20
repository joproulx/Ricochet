package com.absolom.ricochet.ui;

public abstract class ViewController implements IViewController {
	private IView m_view;

	public ViewController(IView view) {
		m_view = view;
	}

	@Override
	public IView getView() {
		return m_view;
	}

}
