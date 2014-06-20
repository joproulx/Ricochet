package com.absolom.ricochet.ui.controls;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.absolom.ricochet.model.RicochetId;
import com.absolom.ricochet.model.RicochetType;
import com.absolom.ricochet.model.common.GameColor;

public class RicochetControl extends EntityUserControl {
	Paint m_paint;
	RicochetType m_type;
	GameColor m_ricochetColor;
	RicochetId m_id;

	public RicochetControl(RicochetId id, GameColor color, RicochetType type) {
		super(id);
		m_id = id;
		m_ricochetColor = color;
		m_type = type;
		m_paint = new Paint();
		m_paint.setARGB(255, color.getR(), color.getG(), color.getB());
		m_paint.setStrokeWidth(2);
	}

	public RicochetId getRicochetId() {
		return m_id;
	}

	public GameColor getColor() {
		return m_ricochetColor;
	}

	@Override
	public void draw(Canvas canvas, long elapsedMilliseconds) {
		if (m_type == RicochetType.Backslash) {
			canvas.drawLine(getBoundingBox().getLeft(), getBoundingBox().getTop(), getBoundingBox().getRight(), getBoundingBox().getBottom(), m_paint);
		} else {
			canvas.drawLine(getBoundingBox().getRight(), getBoundingBox().getTop(), getBoundingBox().getLeft(), getBoundingBox().getBottom(), m_paint);
		}
	}
}
