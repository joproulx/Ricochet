package com.absolom.ricochet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.absolom.ricochet.ui.IView;
import com.absolom.ricochet.ui.StartGameActivity;

public class MainActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		try {
			
			
			// ViewAgent agent = new ViewAgent(this, messageBus);

		} catch (Exception e) {
			Log.w("ricochet", e.toString());
		}

		LinearLayout gameWidgets = new LinearLayout(this);
//
		Button hostGameButton = new Button(this);
		hostGameButton.setText("Start");
		hostGameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		

		
		
//		Button joinGameButton = new Button(this);
//		joinGameButton.setText("Join game");
//
		gameWidgets.addView(hostGameButton);
//		gameWidgets.addView(joinGameButton);

		Intent intent = new Intent(MainActivity.this, StartGameActivity.class);
		startActivity(intent);

		
		

		setContentView(gameWidgets);
	}

	public void onContentViewModified(IView newView) {
		super.setContentView(newView.getUIView());
	}
}