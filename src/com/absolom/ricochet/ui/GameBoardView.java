package com.absolom.ricochet.ui;

import java.util.*;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.*;
import android.view.ViewGroup.LayoutParams;

import com.absolom.ricochet.message.command.ClaimSolutionCommand;
import com.absolom.ricochet.message.command.MoveRobotCommand;
import com.absolom.ricochet.message.event.*;
import com.absolom.ricochet.model.*;
import com.absolom.ricochet.ui.controls.EntityUserControl;
import com.absolom.ricochet.ui.controls.GameBoardControl;
import com.absolom.ricochet.ui.controls.RobotControl;
import com.absolom.ricochet.ui.controls.UserControl;
import com.absolom.ricochet.ui.mappers.*;
import com.absolom.utility.common.Direction;
import com.absolom.utility.data.DataModifiedEvent;
import com.absolom.utility.messaging.*;

public class GameBoardView extends SurfaceView implements SurfaceHolder.Callback, IViewController, IDrawingThreadListener, IView
{
	private GestureDetectorCompat m_gestureDetector;
	private DrawingThread m_drawingThread;
    
	// private GameBoard m_gameBoard;
    private RobotMover m_mover;

	private EntityManager m_entityManager;

	private MessageTransceiver m_messageTransceiver;

	private GameBoardControl m_gameBoardControl;
	private HashMap<EntityId, EntityUserControl> m_itemControls;

	private PlayerId m_currentPlayerId;
	
	protected GameBoardView(Context context) {
		this(context, null, null);
	}

	public GameBoardView(Context context, EntityManager entityManager, MessageTransceiver messagetransceiver) {
		super(context);

		m_entityManager = entityManager;
		m_messageTransceiver = messagetransceiver;
		Log.w("ricochet", "MySurfaceView constructor");
		setFocusable(true);
		
		// ==================================
		// TODO: remove
		m_currentPlayerId = PlayerId.generate();
		m_messageTransceiver.postAndWait(this, new ClaimSolutionCommand(m_currentPlayerId, 100));
		// ==================================
		
		
		m_gameBoardControl = null;
		m_itemControls = new HashMap<EntityId, EntityUserControl>();

		m_messageTransceiver.registerMessageReceiver(DataModifiedEvent.class, onDataModified);
		m_messageTransceiver.registerMessageReceiver(RobotMovedEvent.class, onRobotMoved);

		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

	}

	private IMessageHandler onDataModified = new MessageHandler<DataModifiedEvent>(this) {
		public @Override
		IMessageResult onMessage(DataModifiedEvent message) {
			// TODO: validate that modifications impacts UI before invalidating
			invalidateModel();
		    invalidateLayout();
			return MessageResult.success();
		}
	};

	private IMessageHandler onRobotMoved = new MessageHandler<RobotMovedEvent>(this) {
		public @Override
		IMessageResult onMessage(RobotMovedEvent event) {
			// RobotMove move = event.getMove();
			// Path itinerary = robotMovedEvent.getPath();
			return MessageResult.success();
		}
	};

	public @Override
	void surfaceCreated(SurfaceHolder holder) {
		Log.w("myApp", "surfaceCreated");
		onShow();
		m_drawingThread = new DrawingThread(holder, this);
		m_drawingThread.start();
	}

	@Override 
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
		
		// Force view to be a square
		
		LayoutParams params = this.getLayoutParams();
		params.width = parentWidth < parentHeight ? parentWidth : parentHeight;
		params.height = parentWidth < parentHeight ? parentWidth : parentHeight;
		this.setMeasuredDimension(params.width, params.height);
		this.setLayoutParams(params);
		Log.w("onMeasure", "widthMeasureSpec:" + widthMeasureSpec + ", heightMeasureSpec:" + heightMeasureSpec + ", parentWidth:" + parentWidth + ", parentHeight:" + parentHeight);
	}
	
	public @Override
	void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.w("myApp", "surfaceChanged " + width + " " + height);
		
		
		invalidateLayout();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.w("myApp", "surfacDestroyed");
		m_drawingThread.stopAndWait();
	}

	@Override
	public EndPointId getEndPointId() {
		return m_messageTransceiver.getEndPointId();
	}

	public GameBoard getCurrentGameBoard() {
		// GetGameBoardCommand.Result result = (GetGameBoardCommand.Result)
		// m_messageTransceiver.postAndWait(new GetGameBoardCommand());
		// return result.getValue();

		return m_entityManager.getBoard();
	}

	@Override
	public void drawCanvas(Canvas canvas, long elapsedMilliseconds) {

		if (!validateDrawingState()) {
			return;
		}

		canvas.save();

		m_gameBoardControl.draw(canvas, elapsedMilliseconds);

		m_mover.update(elapsedMilliseconds);
				
		for (EntityUserControl control: m_itemControls.values()) {
			control.draw(canvas, elapsedMilliseconds);
			
			if (control instanceof RobotControl){
				RobotControl robotControl = (RobotControl)control;
								
				if (!robotControl.isMoving() && robotControl.isSelected()){
					m_gameBoardControl.draw(robotControl.getAvailableMoves(), canvas, robotControl.getColor());
				}
			}
		}

		canvas.restore();
		
	}

	private boolean validateDrawingState() {
		return m_gameBoardControl != null;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (m_gestureDetector == null) {
			m_gestureDetector = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener()
	        {
				@Override
				public boolean onDown(MotionEvent e) {
					return true;
				}
				
				@Override
				public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
					return true;
				}
	        	@Override
	        	public boolean onSingleTapUp(MotionEvent e) {
	        		selectRobot(e);
	        		
	        		return true;
	        	}

	        	@Override
	        	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	        		RobotControl control = selectRobot(e1);
	        		if (control != null){
	        			Direction direction = m_mover.fling(control, velocityX, velocityY);
	        			if (direction != Direction.None){
	        				m_messageTransceiver.postAndWait(this, new MoveRobotCommand(m_currentPlayerId, control.getRobotId(), direction));
	        			}
	        			
	        			// TODO: Handle errors
	        		}
	        		
	        		return true;
	        	}

	        });
	    }
		
	    m_gestureDetector.onTouchEvent(event);
	    
	    return true;
	}
	
	private RobotControl selectRobot(MotionEvent e) {
		EntityUserControl selectedControl = null;
		for (EntityUserControl control: m_itemControls.values()) {
			if (!(control instanceof RobotControl)){
				continue;
			}
			
			boolean isSelected = control.isInside(e.getX(), e.getY());
			control.setSelected(isSelected);
			
			if (isSelected){
				selectedControl = control;
			}
		}
		return (RobotControl)selectedControl;
	}

	@Override
	public View getUIView() {
		return this;
	}

	@Override
	public IView getView() {
		return this;
	}

	@Override
	public void onShow() {
		invalidateModel();
		invalidateLayout();
	}

	public void invalidateModel() {
		GameBoard gameBoard = getCurrentGameBoard();

		if (m_gameBoardControl == null) {
			m_gameBoardControl = new GameBoardControl(gameBoard.getTileCountX(), gameBoard.getTileCountY());
			
			Tile[][] tiles = gameBoard.getAllTiles();
			
			for(Tile[] tilesX: tiles){
				for(Tile tile: tilesX){
					m_gameBoardControl.setWalls(tile.getCoordinates(), tile.getWalls());
				}
			}
			
			m_mover = new RobotMover(m_gameBoardControl);
		}

		mapEntityToControl(m_entityManager.getAllEntitiesByType(Robot.class), new RobotToControlMapper(m_entityManager));
		mapEntityToControl(m_entityManager.getAllEntitiesByType(Target.class), new TargetToControlMapper());
		mapEntityToControl(m_entityManager.getAllEntitiesByType(Ricochet.class), new RicochetToControlMapper());
	}

	@SuppressWarnings("unchecked")
	private <T extends Entity<U>, U extends EntityId, V extends EntityUserControl> void mapEntityToControl(List<T> entities, IEntityToControlMapper<T, U, V> mapper) {

		for(T entity: entities){
			EntityUserControl control = m_itemControls.get(entity.getEntityId());
			boolean newControl = (control == null);
			
			control = mapper.mapEntityToControl(entity, (V)control);
			
			if (newControl) {
				m_itemControls.put(entity.getEntityId(), control);
			}
		}
	}

	public void invalidateLayout() {
		GameBoard gameBoard = getCurrentGameBoard();

		if (m_gameBoardControl == null || gameBoard == null) {
			return;
		}

		m_gameBoardControl.setSize(super.getWidth(), super.getHeight());

		for (EntityUserControl control: m_itemControls.values()) {
			GameBoardEntity<?> item = (GameBoardEntity<?>) m_entityManager.getEntity(control.getEntityId());
			updatePosition(control, item);
		}
	}

	private <T extends Entity<U>, U extends EntityId> void updatePosition(UserControl control, GameBoardEntity<U> item) {
		PointF position = m_gameBoardControl.getTilePosition(item.getCoordinates());
		control.setPosition(position);
		control.setSize(m_gameBoardControl.getTileWidth(), m_gameBoardControl.getTileHeight());
	}

}