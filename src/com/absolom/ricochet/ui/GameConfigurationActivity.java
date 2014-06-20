package com.absolom.ricochet.ui;

import com.absolom.ricochet.GameManager;
import com.absolom.ricochet.R;
import com.absolom.ricochet.message.command.RegisterPlayerCommand;
import com.absolom.ricochet.message.command.StartNewGameCommand;
import com.absolom.ricochet.model.PlayerId;
import com.absolom.utility.messaging.MessageTransceiver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GameConfigurationActivity extends Activity {

	MessageTransceiver m_messageTransceiver;
	
	public GameConfigurationActivity(){
		m_messageTransceiver = GameManager.getInstance().getMessageTransceiver();
		
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_configuration);
		
			
		m_messageTransceiver.postAndWait(this, new RegisterPlayerCommand(PlayerId.generate(), "Player 1", true));
		m_messageTransceiver.postAndWait(this, new StartNewGameCommand());
		
			
		Intent intent = new Intent(GameConfigurationActivity.this, GameActivity.class);
	    startActivity(intent);
	    
	    
	}
	
	
	
}
