package com.absolom.ricochet;

import java.util.HashMap;

import android.content.Context;
import android.view.View;

import com.absolom.ricochet.message.event.StateChangedEvent;
import com.absolom.ricochet.model.common.StateType;
import com.absolom.ricochet.ui.IView;
import com.absolom.ricochet.ui.IViewController;
import com.absolom.utility.common.Event;
import com.absolom.utility.messaging.IMessageHandler;
import com.absolom.utility.messaging.IMessageResult;
import com.absolom.utility.messaging.MessageHandler;
import com.absolom.utility.messaging.MessageResult;
import com.absolom.utility.messaging.MessageTransceiver;

public class ViewAgent implements IViewAgent {
	// ----------------
	// Events
	// ----------------
	public Event<ViewChangedEventArgs> ViewChangedEvent = new Event<ViewChangedEventArgs>();

	// ----------------
	// Fields
	// ----------------
	private IViewController m_currentViewController;
	private HashMap<StateType, IViewController> m_viewControllers;
	private MessageTransceiver m_messageTransceiver;
	
	public ViewAgent(Context context, MessageTransceiver messageTransceiver) {
		m_messageTransceiver = messageTransceiver;
		m_messageTransceiver.registerMessageReceiver(StateChangedEvent.class, onStateChanged);

		m_viewControllers = new HashMap<StateType, IViewController>();
		// m_viewControllers.put(StateType.ShowSolution, new
		// GameBoardView(context, messageBroker));

		// updateView(getCurrentState());
		updateView(StateType.ShowSolution);
	}

	/*
	 * private StateType getCurrentState() { GetCurrentStateCommand.Result
	 * result = (GetCurrentStateCommand.Result) postAndWait(new
	 * GetCurrentStateCommand()); return result.getValue(); }
	 */

	private IMessageHandler onStateChanged = new MessageHandler<StateChangedEvent>(this) {
		public @Override
		IMessageResult onMessage(StateChangedEvent event) {
			StateType state = event.getNewStateType();
			updateView(state);
			return MessageResult.success();
		}
	};

	private void updateView(StateType stateType) {
		IViewController newViewController = getViewControllerForState(stateType);

		if (newViewController != null && m_currentViewController != newViewController) {
			if (m_currentViewController != null) {
				getCurrentUIView().setVisibility(View.GONE);
				m_messageTransceiver.pauseMessageHandling(m_currentViewController);
			}

			m_currentViewController = newViewController;

			m_messageTransceiver.resumeMessageHandling(m_currentViewController.getEndPointId());

			ViewChangedEvent.triggers(this, new ViewChangedEventArgs(newViewController.getView()));
			getCurrentUIView().setVisibility(View.VISIBLE);
			getCurrentView().onShow();
		}
	}

	private IViewController getViewControllerForState(StateType state) {
		return m_viewControllers.get(state);
	}

	public IViewController getCurrentViewController() {
		return m_currentViewController;
	}

	public IView getCurrentView() {
		return m_currentViewController != null ? m_currentViewController.getView() : null;
	}

	public View getCurrentUIView() {
		return m_currentViewController != null ? m_currentViewController.getView().getUIView() : null;
	}
}
