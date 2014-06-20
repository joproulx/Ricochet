package com.absolom.ricochet.ui.controls;

import java.util.EnumSet;

import com.absolom.ricochet.model.RicochetType;
import com.absolom.ricochet.model.common.GameColor;
import com.absolom.utility.common.Direction;
import android.graphics.Canvas;
import android.graphics.Paint;

public class TileControl extends UserControl {

	Paint m_fillPaint;
	Paint m_wallPaint;
	EnumSet<Direction> m_walls;
	RicochetType m_type;
	GameColor m_ricochetColor;

	public TileControl(Paint fillPaint) {
		m_type = RicochetType.None;
		m_fillPaint = fillPaint;
		m_wallPaint = GameColor.White.toStrokePaint();

		m_walls = EnumSet.noneOf(Direction.class);
	}

	@Override
	public void draw(Canvas canvas, long elapsedMilliseconds) {
		
		if (!m_walls.contains(Direction.Up)){
			canvas.drawLine(getBoundingBox().getLeft(), getBoundingBox().getTop(), getBoundingBox().getRight(), getBoundingBox().getTop(),  m_wallPaint);
		}
		
		if (!m_walls.contains(Direction.Left)){
			canvas.drawLine(getBoundingBox().getLeft(), getBoundingBox().getTop(), getBoundingBox().getLeft(), getBoundingBox().getBottom(),  m_wallPaint);
		}
		
		if (!m_walls.contains(Direction.Right)){
			canvas.drawLine(getBoundingBox().getRight(), getBoundingBox().getTop(), getBoundingBox().getRight(), getBoundingBox().getBottom(),  m_wallPaint);
		}
		
		if (!m_walls.contains(Direction.Down)){
			canvas.drawLine(getBoundingBox().getLeft(), getBoundingBox().getBottom(), getBoundingBox().getRight(), getBoundingBox().getTop(),  m_wallPaint);
		}
		
		
//		canvas.drawRect(getBoundingBox().getLeft() + 1,
//				 getBoundingBox().getTop() + 1,
//				 getBoundingBox().getLeft() + getBoundingBox().getWidth() - 1,
//				 getBoundingBox().getTop() + getBoundingBox().getHeight() - 1,
//				 m_fillPaint);
		
//		Rectangle box = getBoundingBox();
//		
//		float halfHallWidth = 8;
//		
//		if (!m_walls.contains(Direction.Up)){
//			canvas.drawLine(box.getCenterX() - halfHallWidth, box.getTop(), box.getCenterX() - halfHallWidth, box.getCenterY() - halfHallWidth, m_wallPaint);
//			canvas.drawLine(box.getCenterX() + halfHallWidth, box.getTop(), box.getCenterX() + halfHallWidth, box.getCenterY() - halfHallWidth, m_wallPaint);
//		}
//		else{
//			canvas.drawLine(box.getCenterX() - halfHallWidth, box.getCenterY() - halfHallWidth, box.getCenterX()+ halfHallWidth, box.getCenterY() - halfHallWidth, m_wallPaint);
//		}
//		
//		if (!m_walls.contains(Direction.Left)){
//			canvas.drawLine(box.getLeft(), box.getCenterY() - halfHallWidth, box.getCenterX() - halfHallWidth, box.getCenterY() - halfHallWidth, m_wallPaint);
//			canvas.drawLine(box.getLeft(), box.getCenterY() + halfHallWidth, box.getCenterX() - halfHallWidth, box.getCenterY() + halfHallWidth, m_wallPaint);
//		}
//		else{
//			canvas.drawLine(box.getCenterX() - halfHallWidth, box.getCenterY() - halfHallWidth, box.getCenterX()- halfHallWidth, box.getCenterY() + halfHallWidth, m_wallPaint);
//		}
//		
//		if (!m_walls.contains(Direction.Right)){
//			canvas.drawLine(box.getRight(), box.getCenterY() - halfHallWidth, box.getCenterX() + halfHallWidth, box.getCenterY() - halfHallWidth, m_wallPaint);
//			canvas.drawLine(box.getRight(), box.getCenterY() + halfHallWidth, box.getCenterX() + halfHallWidth, box.getCenterY() + halfHallWidth, m_wallPaint);
//		}
//		else{
//			canvas.drawLine(box.getCenterX() + halfHallWidth, box.getCenterY() - halfHallWidth, box.getCenterX() + halfHallWidth, box.getCenterY() + halfHallWidth, m_wallPaint);
//		}
//		
//		if (!m_walls.contains(Direction.Down)){
//			canvas.drawLine(box.getCenterX() - halfHallWidth, box.getBottom(), box.getCenterX() - halfHallWidth, box.getCenterY() + halfHallWidth, m_wallPaint);
//			canvas.drawLine(box.getCenterX() + halfHallWidth, box.getBottom(), box.getCenterX() + halfHallWidth, box.getCenterY() + halfHallWidth, m_wallPaint);
//		}
//		else{
//			canvas.drawLine(box.getCenterX() - halfHallWidth, box.getCenterY() + halfHallWidth, box.getCenterX() + halfHallWidth, box.getCenterY() + halfHallWidth, m_wallPaint);
//		}
		
//		//Path path = new Path();
//
//		//float wallWidth = getBoundingBox().getWidth() * 0.8F;
//		//float wallHeight = getBoundingBox().getHeight() * 0.8F;
//		float wallThickness = getBoundingBox().getWidth() * 0.05F;
//
//		float noWallWidth = getBoundingBox().getWidth() * 0.05F;
//		float noWallHeight = getBoundingBox().getHeight() * 0.05F;
//
//		int margin = 3;
//		
//		float left = getBoundingBox().getLeft() + margin;
//		float right = getBoundingBox().getRight() - margin;
//		float top = getBoundingBox().getTop() + margin;
//		float bottom = getBoundingBox().getBottom() - margin;
//		//path.setFillType(FillType.WINDING);
//		//path.moveTo(left, top);
//
//		if (m_walls.contains(Direction.Up)) {
//			float leftWall = left + noWallWidth;
//			float rightWall = right - noWallWidth;
//			float topWall = top + wallThickness;
//
//			canvas.drawRect(leftWall,
//					 top,
//					 rightWall,
//					 topWall, m_wallPaint);
//			
////			path.lineTo(leftWall, top);
////			path.lineTo(leftWall, topWall);
////			path.lineTo(rightWall, topWall);
////			path.lineTo(rightWall, top);
//		}
//
//		//path.lineTo(right, top);
//
//		if (m_walls.contains(Direction.Right)) {
//			float topWall = top + noWallHeight;
//			float bottomWall = bottom - noWallHeight;
//			float leftWall = right - wallThickness;
//			
//			canvas.drawRect(leftWall,
//					topWall,
//					 right,
//					 bottomWall, m_wallPaint);
//
////			path.lineTo(right, topWall);
////			path.lineTo(leftWall, topWall);
////			path.lineTo(leftWall, bottomWall);
////			path.lineTo(right, bottomWall);
//		}
//
//		//path.lineTo(right, bottom);
//
//		if (m_walls.contains(Direction.Down)) {
//			float leftWall = left + noWallWidth;
//			float rightWall = right - noWallWidth;
//			float bottomWall = bottom - wallThickness;
//			canvas.drawRect(leftWall,
//					bottomWall,
//					rightWall,
//					 bottom, m_wallPaint);
////			path.lineTo(rightWall, bottom);
////			path.lineTo(rightWall, bottomWall);
////			path.lineTo(leftWall, bottomWall);
////			path.lineTo(leftWall, bottom);
//		}
//
//		//path.lineTo(left, bottom);
//		
//		if (m_walls.contains(Direction.Left)) {
//			float topWall = top + noWallHeight;
//			float bottomWall = bottom - noWallHeight;
//			float rightWall = left + wallThickness;
//			canvas.drawRect(left,
//					topWall,
//					rightWall,
//					bottomWall, m_wallPaint);
////			path.lineTo(left, bottomWall);
////			path.lineTo(rightWall, bottomWall);
////			path.lineTo(rightWall, topWall);
////			path.lineTo(left, topWall);
//		}
//		//path.lineTo(left, top);
//		
//		//path.close();
//
//		//canvas.drawPath(path, m_fillPaint);

		 
		
	}

	public void setWalls(EnumSet<Direction> walls) {
		m_walls = walls;
	}

}
