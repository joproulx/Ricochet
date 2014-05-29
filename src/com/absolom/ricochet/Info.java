//package com.absolom.ricochet;
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//
//import com.absolom.ricochet.ui.GameBoardView;
//import com.absolom.ricochet.ui.IView;
//import com.absolom.utility.messaging.MessageBus;
//
//public class Info extends Activity implements OnClickListener {
//	//private FrameLayout Game;
//	//private GestureDetector m_gestureDetector;
//	//private GameManager m_gameManager;
//	//private Test test;
//
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		Log.w("ricochet", "Started...");
//
//		try {
//			ScheduledExecutorService service = Executors
//					.newSingleThreadScheduledExecutor();
//			MessageBus messageBus = new MessageBus(service);
//			// ViewAgent agent = new ViewAgent(this, messageBus);
//
//			super.setContentView(new GameBoardView(this, messageBus));
//		} catch (Exception e) {
//			Log.w("ricochet", e.toString());
//		}
//
//		// ScheduledExecutorService service =
//		// Executors.newSingleThreadScheduledExecutor();
//		// MessageBus messageBus = new MessageBus(service);
//		// m_gameManager = new GameManager(service,
//		// messageBus, new ViewAgent(this, messageBus));
//		// m_gameManager.ViewChangedEvent.addHandler();
//		// m_gestureDetector = new GestureDetector(new MyGestureListener());
//
//		// requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//		/*
//		 * m_gameController = new GameController(); m_viewManager = new
//		 * ViewManager(this, m_gameController); m_viewManager.setListener(this);
//		 * setContentView(m_viewManager.getCurrentViewController().getView());
//		 */
//		/*
//		 * m_viewController = new GameBoardViewController(board);
//		 * 
//		 * 
//		 * 
//		 * 
//		 * Game = new FrameLayout(this); GameBoardView Gameview = new
//		 * GameBoardView(this, m_viewController); LinearLayout GameWidgets = new
//		 * LinearLayout (this);
//		 * 
//		 * 
//		 * 
//		 * 
//		 * 
//		 * Button EndGameButton = new Button(this); TextView MyText = new
//		 * TextView(this);
//		 * 
//		 * EndGameButton.setWidth(300); EndGameButton.setText("Start Game");
//		 * MyText.setText("rIZ..i");
//		 * 
//		 * GameWidgets.addView(MyText); GameWidgets.addView(EndGameButton);
//		 * 
//		 * Game.addView(Gameview); Game.addView(GameWidgets);
//		 * 
//		 * setContentView(Game); EndGameButton.setOnClickListener(this);
//		 */
//
//		// setContentView(GameWidgets);
//
//		// setContentView(Game);
//	}
//
//	public void onContentViewModified(IView newView) {
//		setContentView(newView);
//	}
//
//	public void setContentView(IView view) {
//		Log.w("myApp", "SetContentView: " + view.getClass().getName());
//		super.setContentView(view.getUIView());
//	}
//
//	public void onClick(View v) {
//		// Game.removeAllViews();
//	}
//
//	/*private class MyGestureListener extends SimpleOnGestureListener {
//		public boolean onSingleTapUp(android.view.MotionEvent e) {
//			Log.w("myApp", "onSingleTapUp");
//			return false;
//		}
//
//		public void onLongPress(android.view.MotionEvent e) {
//			Log.w("myApp", "onLongPress");
//		}
//
//		public boolean onScroll(android.view.MotionEvent e1,
//				android.view.MotionEvent e2, float distanceX, float distanceY) {
//
//			return false;
//		}
//
//		public boolean onFling(android.view.MotionEvent e1,
//				android.view.MotionEvent e2, float velocityX, float velocityY) {
//			Log.w("myApp", "onFling. e1.X:" + e1.getX() + ", e1.Y:" + e1.getY()
//					+ ", e2.X:" + e1.getX() + ", e2.Y:" + e1.getY()
//					+ ", velocityX:" + velocityX + ", velocityY:" + velocityY);
//
//			return false;
//		}
//
//		public void onShowPress(android.view.MotionEvent e) {
//			Log.w("myApp", "onShowPress");
//		}
//
//		public boolean onDown(android.view.MotionEvent e) {
//			Log.w("myApp", "onDown");
//			return false;
//		}
//
//		public boolean onDoubleTap(android.view.MotionEvent e) {
//			Log.w("myApp", "onDoubleTap");
//			return false;
//		}
//
//		public boolean onDoubleTapEvent(android.view.MotionEvent e) {
//			Log.w("myApp", "onDoubleTapEvent");
//			return false;
//		}
//
//		public boolean onSingleTapConfirmed(android.view.MotionEvent e) {
//			Log.w("myApp", "onSingleTapConfirmed");
//			return false;
//		}
//
//	}*/
// }
