package com.absolom.ricochet.ui.controls;

import android.graphics.PointF;

import com.absolom.utility.common.Rectangle;

public abstract class UserControl implements IUserControl {
	private Rectangle m_boundingBox;
	private boolean m_isSelected;
	
	
	public UserControl() {
		m_boundingBox = new Rectangle(0, 0, 1, 1);
	}

	public float getWidth() {
		return m_boundingBox.getWidth();
	}

	public float getHeight() {
		return m_boundingBox.getHeight();
	}

	public void setSize(float width, float height) {
		m_boundingBox.setWidth(width);
		m_boundingBox.setHeight(height);
		onBoundingBoxUpdated();
	}

	public void setPosition(float x, float y) {
		m_boundingBox.setTop(y);
		m_boundingBox.setLeft(x);
		onBoundingBoxUpdated();
	}

	public Rectangle getBoundingBox(){
		return m_boundingBox;
	}
	
	public void onBoundingBoxUpdated(){
		
	}
	
	public void setPosition(PointF point) {
		setPosition(point.x, point.y);
	}
	
	public void setBoundingBox(float top, float left, float width, float height) {
		setPosition(top, left);
		setSize(width, height);
	}

	public boolean isInside(float x, float y) {
		return m_boundingBox.isInside(x, y);
	}
	
	public void setSelected(boolean selected) {
		m_isSelected = selected;
	}
	
	public boolean isSelected() {
		return m_isSelected;
	}
	
}
