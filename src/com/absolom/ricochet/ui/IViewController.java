package com.absolom.ricochet.ui;

import com.absolom.utility.messaging.IMessageEndPoint;

public interface IViewController extends IMessageEndPoint {
	public IView getView();

}
