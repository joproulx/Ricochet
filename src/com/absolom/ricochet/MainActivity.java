package com.absolom.ricochet;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.absolom.ricochet.ui.GameBoardView;
import com.absolom.ricochet.ui.IView;
import com.absolom.utility.common.WorkScheduler;
import com.absolom.utility.common.WorkerThread;
import com.absolom.utility.messaging.MessageBus;
import com.absolom.utility.messaging.MessageTransceiver;

public class MainActivity extends Activity {
	// private FrameLayout Game;
	// private GestureDetector m_gestureDetector;
	private GameManager m_gameManager;
	private MessageTransceiver m_messageTransceiver;
	// private Test test;
	
	private WorkerThread m_workerThread;
	private WorkScheduler m_scheduler;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.w("ricochet", "Started...");

		try {
			ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
			m_workerThread = new WorkerThread(service);
			m_scheduler = new WorkScheduler(service);
			
			MessageBus messageBus = new MessageBus(m_workerThread);
			m_messageTransceiver = new MessageTransceiver(messageBus);
			
			// ViewAgent agent = new ViewAgent(this, messageBus);

		} catch (Exception e) {
			Log.w("ricochet", e.toString());
		}

		// m_gameManager = new GameManager(service,
		// messageBus, new ViewAgent(this, messageBus));

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
				m_gameManager = new GameManager(m_workerThread, m_scheduler, m_messageTransceiver);
				m_gameManager.hostGame();
				FrameLayout layout = new FrameLayout(MainActivity.this);
				layout.addView(new GameBoardView(MainActivity.this, m_gameManager.getEntityManager(), m_messageTransceiver));
				setContentView(layout);
			}
		});

		setContentView(gameWidgets);

		// m_gameManager.ViewChangedEvent.addHandler();
		// m_gestureDetector = new GestureDetector(new MyGestureListener());

		// requestWindowFeature(Window.FEATURE_NO_TITLE);

		/*
		 * m_gameController = new GameController(); m_viewManager = new
		 * ViewManager(this, m_gameController); m_viewManager.setListener(this);
		 * setContentView(m_viewManager.getCurrentViewController().getView());
		 */
		/*
		 * m_viewController = new GameBoardViewController(board);
		 * 
		 * 
		 * 
		 * 
		 * Game = new FrameLayout(this); GameBoardView Gameview = new
		 * GameBoardView(this, m_viewController); LinearLayout GameWidgets = new
		 * LinearLayout (this);
		 * 
		 * 
		 * 
		 * 
		 * 
		 * Button EndGameButton = new Button(this); TextView MyText = new
		 * TextView(this);
		 * 
		 * EndGameButton.setWidth(300); EndGameButton.setText("Start Game");
		 * MyText.setText("rIZ..i");
		 * 
		 * GameWidgets.addView(MyText); GameWidgets.addView(EndGameButton);
		 * 
		 * Game.addView(Gameview); Game.addView(GameWidgets);
		 * 
		 * setContentView(Game); EndGameButton.setOnClickListener(this);
		 */

		// setContentView(GameWidgets);

		// setContentView(Game);
	}

	public void onContentViewModified(IView newView) {
		setContentView(newView);
	}

	public void setContentView(IView view) {
		Log.w("myApp", "SetContentView: " + view.getClass().getName());
		super.setContentView(view.getUIView());
	}

	public void onClick(View v) {
		// Game.removeAllViews();
	}

	/*
	 * private class MyGestureListener extends SimpleOnGestureListener { public
	 * boolean onSingleTapUp(android.view.MotionEvent e) { Log.w("myApp",
	 * "onSingleTapUp"); return false; }
	 * 
	 * public void onLongPress(android.view.MotionEvent e) { Log.w("myApp",
	 * "onLongPress"); }
	 * 
	 * public boolean onScroll(android.view.MotionEvent e1,
	 * android.view.MotionEvent e2, float distanceX, float distanceY) {
	 * 
	 * return false; }
	 * 
	 * public boolean onFling(android.view.MotionEvent e1,
	 * android.view.MotionEvent e2, float velocityX, float velocityY) {
	 * Log.w("myApp", "onFling. e1.X:" + e1.getX() + ", e1.Y:" + e1.getY() +
	 * ", e2.X:" + e1.getX() + ", e2.Y:" + e1.getY() + ", velocityX:" +
	 * velocityX + ", velocityY:" + velocityY);
	 * 
	 * return false; }
	 * 
	 * public void onShowPress(android.view.MotionEvent e) { Log.w("myApp",
	 * "onShowPress"); }
	 * 
	 * public boolean onDown(android.view.MotionEvent e) { Log.w("myApp",
	 * "onDown"); return false; }
	 * 
	 * public boolean onDoubleTap(android.view.MotionEvent e) { Log.w("myApp",
	 * "onDoubleTap"); return false; }
	 * 
	 * public boolean onDoubleTapEvent(android.view.MotionEvent e) {
	 * Log.w("myApp", "onDoubleTapEvent"); return false; }
	 * 
	 * public boolean onSingleTapConfirmed(android.view.MotionEvent e) {
	 * Log.w("myApp", "onSingleTapConfirmed"); return false; }
	 * 
	 * }
	 */
}

// package com.absolom.ricochet;
//
// import android.os.Bundle;
// import android.support.v4.app.Fragment;
// import android.support.v7.app.ActionBarActivity;
// import android.view.LayoutInflater;
// import android.view.Menu;
// import android.view.MenuItem;
// import android.view.View;
// import android.view.ViewGroup;
// import android.widget.Toast;
//
// import com.absolom.utility.Test;
//
// public class MainActivity extends ActionBarActivity {
//
// @Override
// protected void onCreate(Bundle savedInstanceState) {
// super.onCreate(savedInstanceState);
// setContentView(R.layout.activity_main);
//
// if (savedInstanceState == null) {
// getSupportFragmentManager().beginTransaction()
// .add(R.id.container, new PlaceholderFragment()).commit();
// }
//
// int duration = Toast.LENGTH_SHORT;
// Test test = new Test();
// Toast toast = Toast.makeText(this, test.getText(), duration);
// toast.show();
// }
//
// @Override
// public boolean onCreateOptionsMenu(Menu menu) {
//
// // Inflate the menu; this adds items to the action bar if it is present.
// getMenuInflater().inflate(R.menu.main, menu);
// return true;
// }
//
// @Override
// public boolean onOptionsItemSelected(MenuItem item) {
// // Handle action bar item clicks here. The action bar will
// // automatically handle clicks on the Home/Up button, so long
// // as you specify a parent activity in AndroidManifest.xml.
// int id = item.getItemId();
// if (id == R.id.action_settings) {
// return true;
// }
// return super.onOptionsItemSelected(item);
// }
//
// /**
// * A placeholder fragment containing a simple view.
// */
// public static class PlaceholderFragment extends Fragment {
//
// public PlaceholderFragment() {
// }
//
// @Override
// public View onCreateView(LayoutInflater inflater, ViewGroup container,
// Bundle savedInstanceState) {
// View rootView = inflater.inflate(R.layout.fragment_main, container,
// false);
// return rootView;
// }
// }
//
// }
