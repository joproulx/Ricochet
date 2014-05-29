package com.absolom.ricochet.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class StartGameView extends Activity {

	public StartGameView() {

	}

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

		setContentView(gameWidgets);
	}

}
