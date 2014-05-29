package com.absolom.ricochet.ui;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.absolom.ricochet.model.TargetId;
import com.absolom.ricochet.model.common.GameColor;

public class TargetControl extends EntityUserControl {
	private Paint m_paint;
	private TargetId m_targetId;

	public TargetControl(GameColor color, TargetId targetId) {
		super(targetId);
		m_paint = new Paint();
		m_paint.setARGB(255, color.getR(), color.getG(), color.getB());
		m_targetId = targetId;
	}

	public TargetId getTargetId() {
		return m_targetId;
	}

	public void draw(Canvas canvas, long elapsedMilliseconds) {
		canvas.drawRect(getBoundingBox().getLeft(), getBoundingBox().getTop(), getBoundingBox().getRight(), getBoundingBox().getBottom(), m_paint);
	}
}
