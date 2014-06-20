package com.absolom.ricochet.ui;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.absolom.ricochet.GameManager;
import com.absolom.utility.common.WorkScheduler;
import com.absolom.utility.common.WorkerThread;
import com.absolom.utility.messaging.MessageBus;
import com.absolom.utility.messaging.MessageTransceiver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class StartGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout gameWidgets = new LinearLayout(this);

		Button hostGameButton = new Button(this);
		hostGameButton.setText("Host game");
		Button joinGameButton = new Button(this);
		joinGameButton.setText("Join game");

		gameWidgets.addView(hostGameButton);
		gameWidgets.addView(joinGameButton);

		hostGameButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				InitializeGameManagerAsHost();
				Intent intent = new Intent(StartGameActivity.this, GameConfigurationActivity.class);
			    startActivity(intent);

			}
		});
		
		setContentView(gameWidgets);
	}

	private void InitializeGameManagerAsHost(){
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		WorkerThread workerThread = new WorkerThread(service);
		WorkScheduler scheduler = new WorkScheduler(service);
		MessageBus messageBus = new MessageBus(workerThread);
		MessageTransceiver messageTransceiver = new MessageTransceiver(messageBus);
		
		GameManager gameManager = GameManager.getInstance(); 
		gameManager.Initialize(workerThread, scheduler, messageTransceiver);
		gameManager.hostGame();
	
	}
	
}
