package com.absolom.ricochet.ui;

import com.absolom.ricochet.model.EntityId;
import com.absolom.utility.common.Rectangle;

import android.graphics.PointF;

public abstract class MovableEntityUserControl extends EntityUserControl {
	private PointF m_movingPosition;
	private boolean m_isMoving;
	
	public MovableEntityUserControl(EntityId entityId){
		super(entityId);
	}
	
	public boolean isMoving() {
		return m_isMoving;
	}

	public void setMoving(boolean moving) {
		m_isMoving = moving;
	}
	
	public void setMovingPosition(PointF movingPosition) {
		m_movingPosition = movingPosition;
	}
	
	public PointF getMovingPosition(PointF movingPosition) {
		return m_movingPosition;
	}
	 
	@Override
	public Rectangle getBoundingBox() {
		Rectangle boundingBox = super.getBoundingBox();
		
		if (!isMoving() || m_movingPosition == null){
			return boundingBox;
		}
		
		return new Rectangle(m_movingPosition.x, m_movingPosition.y, boundingBox.getWidth(), boundingBox.getHeight());
	}

}
