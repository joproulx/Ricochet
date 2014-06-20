package com.absolom.ricochet.ui;

import java.util.List;

import com.absolom.ricochet.GameManager;
import com.absolom.ricochet.message.command.ClaimSolutionCommand;
import com.absolom.ricochet.model.EntityManager;
import com.absolom.ricochet.model.Player;
import com.absolom.utility.common.IEventHandler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class FindSolutionFragment extends Fragment {
	private LinearLayout m_layout;
	
	public FindSolutionFragment(){
		super();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		m_layout = new LinearLayout(getActivity());
		m_layout.setOrientation(LinearLayout.VERTICAL);
		
		EntityManager entityManager = GameManager.getInstance().getEntityManager();
		List<Player> players = entityManager.getAllEntitiesByType(Player.class);

		for (final Player player : players) {
			Button button = new Button(getActivity());
			button.setText(player.getPlayerName());
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ClaimCountForm form = new ClaimCountForm();
					form.OnClaimedSolution.addHandler(new IEventHandler<ClaimFormEventArgs>(){
						@Override
						public void onEvent(Object source, ClaimFormEventArgs eventArg) {
							GameManager.getInstance().getMessageTransceiver().postAndWait(this,  new ClaimSolutionCommand(player.getEntityId(), eventArg.getClaimedCount()));
						}
					}); 
					
					FragmentSwitcher.getInstance().gotoFragment(form);
					
				}
			});

			m_layout.addView(button);
		}
		return m_layout;
	}

}
