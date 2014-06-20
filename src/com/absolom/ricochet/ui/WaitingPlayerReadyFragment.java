package com.absolom.ricochet.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.absolom.ricochet.GameManager;
import com.absolom.ricochet.message.command.SetPlayerReadyCommand;
import com.absolom.ricochet.model.Player;
import com.absolom.ricochet.model.PlayerId;

public class WaitingPlayerReadyFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);
		
		Button button1 = new Button(getActivity());
		button1.setText("Ready!");
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				List<PlayerId> players = getLocalPlayers();
				for(PlayerId playerId: players) {
					GameManager.getInstance().getMessageTransceiver().postAndWait(WaitingPlayerReadyFragment.this, new SetPlayerReadyCommand(playerId));
				}
			}
		});
		
		layout.addView(button1);
		
		
		return layout;
	}
	
	private List<PlayerId> getLocalPlayers(){
		List<Player> players = GameManager.getInstance().getEntityManager().getAllEntitiesByType(Player.class);
		List<PlayerId> localPlayers = new ArrayList<PlayerId>(); 
		for(Player player: players){
			localPlayers.add(player.getEntityId());
		}
		return localPlayers;
	}
}
