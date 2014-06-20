package com.absolom.ricochet.ui;

import java.util.HashMap;

import android.support.v4.app.Fragment;
import com.absolom.ricochet.GameManager;
import com.absolom.ricochet.message.event.StateChangedEvent;
import com.absolom.ricochet.model.common.StateType;
import com.absolom.utility.messaging.IMessageHandler;
import com.absolom.utility.messaging.IMessageResult;
import com.absolom.utility.messaging.MessageHandler;
import com.absolom.utility.messaging.MessageTransceiver;

public class FragmentManager {
	private HashMap<StateType, Fragment> m_fragments;
	private MessageTransceiver m_messageTransceiver;
	private Fragment m_currentFragment;
	public FragmentManager(){
		m_fragments = new HashMap<StateType, Fragment>();
		m_fragments.put(StateType.ShowingNextTarget, new FindSolutionFragment());
		m_fragments.put(StateType.ShowSolution, new ShowingSolutionFragment());
		m_fragments.put(StateType.WaitingAllPlayersReady, new WaitingPlayerReadyFragment());
		
		m_messageTransceiver = GameManager.getInstance().getMessageTransceiver();
		m_messageTransceiver.registerMessageReceiver(StateChangedEvent.class, OnStateChangedEvent);
	}
	
	private IMessageHandler OnStateChangedEvent = new MessageHandler<StateChangedEvent>(this){
		@Override
		public IMessageResult onMessage(StateChangedEvent message) {
			Fragment fragment = getFragmentForState(message.getNewStateType());

			if (fragment != null && m_currentFragment != fragment) {
				if (m_currentFragment != null) {
					m_messageTransceiver.pauseMessageHandling(m_currentFragment);
				}

				m_currentFragment = fragment;
				m_messageTransceiver.resumeMessageHandling(fragment);

				FragmentSwitcher.getInstance().gotoFragment(fragment);
			}
			
			
			return super.onMessage(message);
		};
		
	};
	
	private Fragment getFragmentForState(StateType state) {
		return m_fragments.get(state);
	}

	
}
