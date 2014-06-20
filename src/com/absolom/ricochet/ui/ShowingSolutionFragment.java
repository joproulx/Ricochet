package com.absolom.ricochet.ui;

import com.absolom.ricochet.GameManager;
import com.absolom.ricochet.model.EntityManager;
import com.absolom.ricochet.model.GameStateEntity;
import com.absolom.ricochet.model.GameStateEntityId;
import com.absolom.ricochet.model.Player;
import com.absolom.ricochet.model.PlayerClaimedSolution;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowingSolutionFragment extends Fragment  {
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout layout = new LinearLayout(getActivity());
		
		EntityManager entityManager = GameManager.getInstance().getEntityManager();
		GameStateEntity gameState = entityManager.getEntity(GameStateEntityId.getInstance());
		PlayerClaimedSolution currentSolution = gameState.getCurrentSolution();
		if (currentSolution == null){
			return layout;
		}
		
		Player player = entityManager.getEntity(currentSolution.getPlayerId());
		
		if (player != null){
			TextView text = new TextView(getActivity());
			text.setText("Player showing solution: " + player.getPlayerName());
			layout.addView(text);
			
			text = new TextView(getActivity());
			text.setText("Claimed count: " + currentSolution.getClaimedMoveCount());
			layout.addView(text);
		}
		
		
		
		
		return layout;
	}

}
