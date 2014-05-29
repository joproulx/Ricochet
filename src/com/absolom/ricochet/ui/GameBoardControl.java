package com.absolom.ricochet.ui;

import java.util.EnumSet;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.absolom.ricochet.model.RobotAvailableMoves;
import com.absolom.ricochet.model.RobotMove;
import com.absolom.ricochet.model.TileCoordinates;
import com.absolom.ricochet.model.TilePath;
import com.absolom.ricochet.model.common.GameColor;
import com.absolom.utility.common.Direction;

public class GameBoardControl extends UserControl {
	private Paint m_backgroundPaint;

	private Paint m_paint;
	private int m_cellCountX;
	private int m_cellCountY;

	private TileControl[][] m_tiles;

	public GameBoardControl(int cellCountX, int cellCountY) {
		m_cellCountX = cellCountX;
		m_cellCountY = cellCountY;
		m_tiles = new TileControl[cellCountX][cellCountY];
		m_paint = new Paint();
		m_paint.setARGB(255, 255, 255, 255);
		m_paint.setStyle(Paint.Style.FILL);

		m_backgroundPaint = new Paint();
		m_backgroundPaint.setARGB(255, 0, 0, 255);
		m_backgroundPaint.setStyle(Paint.Style.FILL);
		createTiles();
	}

	private void createTiles() {
		for (int x = 0; x < m_cellCountX; x++) {
			for (int y = 0; y < m_cellCountX; y++) {
				m_tiles[x][y] = new TileControl(m_paint);

			}
		}
	}

	@Override
	public void onBoundingBoxUpdated() {
		updateLayout();
	}

	private void updateLayout() {
		float cellWidth = ((1F / (float) m_cellCountX) * getBoundingBox().getWidth());
		float cellHeight = ((1F / (float) m_cellCountY) * getBoundingBox().getHeight());

		for (int x = 0; x < m_cellCountX; x++) {
			for (int y = 0; y < m_cellCountX; y++) {

				float topX = /* getBoundingBox().getLeft() + */cellWidth * (float) x;
				float topY = /* getBoundingBox().getTop() + */cellHeight * (float) y;

				m_tiles[x][y].setBoundingBox(topX, topY, cellWidth, cellHeight);

			}
		}
	}

	public int getCellCountY() {
		return m_cellCountY;
	}

	public int getCellCountX() {
		return m_cellCountX;
	}

	public void draw(Canvas canvas, long elapsedMilliseconds) {
//		canvas.drawRect(0, 0, getBoundingBox().getWidth(), getBoundingBox().getHeight(), m_backgroundPaint);
//
//		for (int x = 0; x < m_cellCountX; x++) {
//			for (int y = 0; y < m_cellCountX; y++) {
//				m_tiles[x][y].draw(canvas, elapsedMilliseconds);
//			}
//		}

		GameColor gameColor = GameColor.Black;
		Paint paint = new Paint();
		paint.setARGB(255, gameColor.getR(), gameColor.getG(), gameColor.getB());
		paint.setStyle(Paint.Style.FILL);

		canvas.drawRect(getBoundingBox().getLeft(), getBoundingBox().getTop(), getBoundingBox().getLeft(), getBoundingBox().getBottom(), m_paint);

		float cellWidth = ((1F / (float) m_cellCountX) * getBoundingBox().getWidth());
		float cellHeight = ((1F / (float) m_cellCountY) * getBoundingBox().getHeight());
		for (int i = 1; i < m_cellCountX; i++) {
			float topX = cellWidth * (float) i;
			float topY = 0;

			float bottomX = topX;
			float bottomY = getBoundingBox().getBottom();

			canvas.drawLine(topX, topY, bottomX, bottomY, m_paint);
		}

		for (int i = 1; i < m_cellCountY; i++) {
			float leftX = 0;
			float leftY = cellHeight * (float) i;

			float rightX = getBoundingBox().getRight();
			float rightY = leftY;

			canvas.drawLine(leftX, leftY, rightX, rightY, m_paint);
		}

		
	}

	public void setWalls(TileCoordinates coordinates, EnumSet<Direction> walls) {
		m_tiles[coordinates.getX()][coordinates.getY()].setWalls(walls);
	}

	public void draw(RobotAvailableMoves moves, Canvas canvas, GameColor gameColor) {
		Paint paint = new Paint();
		paint.setARGB(255, gameColor.getR(), gameColor.getG(), gameColor.getB());
		for (RobotMove move : moves.getAllMoves()) {
			TilePath path = move.getPath();
			PointF previousPoint = null;
			for (TileCoordinates coord : path.getAllWayPoints()) {
				PointF point = getTileCenterPosition(coord);

				if (previousPoint != null) {
					canvas.drawLine(previousPoint.x, previousPoint.y, point.x, point.y, paint);
				}
				previousPoint = point;

			}
		}
	}

	public float getWidth() {
		return getBoundingBox().getWidth();
	}

	public float getHeight() {
		return getBoundingBox().getHeight();
	}

	public float getTileWidth() {
		return getBoundingBox().getWidth() / (float) m_cellCountX;
	}

	public float getTileHeight() {
		return getBoundingBox().getHeight() / (float) m_cellCountY;
	}

	public PointF getTilePosition(TileCoordinates tileCoordinates) {
		float pointX = getBoundingBox().getLeft() + (float) tileCoordinates.getX() * getBoundingBox().getWidth() / (float) m_cellCountX;
		float pointY = getBoundingBox().getTop() + (float) tileCoordinates.getY() * getBoundingBox().getHeight() / (float) m_cellCountY;

		return new PointF(pointX, pointY);
	}

	public PointF getTileCenterPosition(TileCoordinates tileCoordinates) {
		PointF pos = getTilePosition(tileCoordinates);

		return new PointF(pos.x + getTileWidth() / 2F, pos.y + getTileHeight() / 2F);
	}
}
