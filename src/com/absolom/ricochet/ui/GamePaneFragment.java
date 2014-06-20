package com.absolom.ricochet.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class GamePaneFragment extends Fragment {
	private FrameLayout m_layout;
	
	@SuppressWarnings("unused")
	private FragmentManager m_fragmentManager;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		FragmentSwitcher.createInstance(getActivity().getSupportFragmentManager(), m_layout.getId());
		m_fragmentManager = new FragmentManager();
		
		FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
		transaction.add(m_layout.getId(), new WaitingPlayerReadyFragment());
		transaction.commit();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		m_layout = new FrameLayout(getActivity());
		m_layout.setId(View.generateViewId());
		m_layout.setLayoutParams(new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));
		
		return m_layout;
	}
}
