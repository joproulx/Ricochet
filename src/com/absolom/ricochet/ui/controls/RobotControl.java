package com.absolom.ricochet.ui.controls;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.absolom.ricochet.model.RobotAvailableMoves;
import com.absolom.ricochet.model.RobotId;
import com.absolom.ricochet.model.common.GameColor;

public class RobotControl extends MovableEntityUserControl {
	private Paint m_paint;
	private GameColor m_robotColor;
	private RobotId m_robotId;
	private RobotAvailableMoves m_availableMoves;
	
	public RobotControl(RobotId robotId, GameColor color) {
		super(robotId);
		// m_itineraries = new ArrayList<Path>();
		m_robotId = robotId;
		m_robotColor = color;
		m_paint = new Paint();
		m_paint.setARGB(255, color.getR(), color.getG(), color.getB());
		m_availableMoves = new RobotAvailableMoves();
	}

	public RobotId getRobotId() {
		return m_robotId;
	}

	public GameColor getColor() {
		return m_robotColor;
	}

	
	@Override
	public void draw(Canvas canvas, long elapsedMilliseconds) {
		float width = getBoundingBox().getWidth();
		float height = getBoundingBox().getHeight();

		float radius = width < height ? width : height;
		radius = radius / 2F;
		canvas.drawCircle(getBoundingBox().getCenterX(), getBoundingBox().getCenterY(), radius, m_paint);
	}

	public void setAvailableMoves(RobotAvailableMoves availableMoves) {
		m_availableMoves = availableMoves;
	}

	public RobotAvailableMoves getAvailableMoves() {
		return m_availableMoves;
	}

}
