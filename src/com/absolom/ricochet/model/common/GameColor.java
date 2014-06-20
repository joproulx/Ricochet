package com.absolom.ricochet.model.common;

import java.io.Serializable;

import com.google.common.base.Objects;
import android.graphics.Paint;

public class GameColor implements Serializable {

	private static final long serialVersionUID = 1L;
	private final int m_r;
	private final int m_g;
	private final int m_b;

	public static final GameColor White = new GameColor(255, 255, 255);
	public static final GameColor Black = new GameColor(0, 0, 0);
	public static final GameColor Grey = new GameColor(125, 125, 125);
	public static final GameColor LightSlateGray = new GameColor(119, 136, 153);
	public static final GameColor LightSteelBlue = new GameColor(176, 196, 222);
	public static final GameColor LightSeaGreen = new GameColor(32,178,170);
	public static final GameColor Red = new GameColor(255, 0, 0);
	public static final GameColor Blue = new GameColor(0, 0, 255);

	public GameColor(int r, int g, int b) {
		m_r = r;
		m_g = g;
		m_b = b;
	}

	public GameColor(GameColor copy) {
		this(copy.getR(), copy.getG(), copy.getB());
	}

	public int getR() {
		return m_r;
	}

	public int getG() {
		return m_g;
	}

	public int getB() {
		return m_b;
	}
	
	public Paint toFillPaint(){
		Paint paint = new Paint();
		paint.setARGB(255, getR(), getG(), getB());
		paint.setStyle(Paint.Style.FILL);
		return paint;
	}
	
	public Paint toStrokePaint(){
		Paint paint = new Paint();
		paint.setARGB(255, getR(), getG(), getB());
		paint.setStyle(Paint.Style.STROKE);
		return paint;
	}

	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof GameColor) {
			GameColor that = (GameColor) other;
			result = (this.getR() == that.getR() && this.getG() == that.getG() && this.getB() == that.getB());
		}
		return result;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getR(), getG(), getB());
	}
}
