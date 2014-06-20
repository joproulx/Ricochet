package com.absolom.ricochet.ui;

import com.absolom.ricochet.GameManager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GameBoardFragment extends Fragment {
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		GameBoardView view = new GameBoardView(getActivity(), 
                GameManager.getInstance().getEntityManager(), 
                GameManager.getInstance().getMessageTransceiver());
		
		return view;
	}
	
}
