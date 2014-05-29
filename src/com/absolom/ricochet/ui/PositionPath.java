package com.absolom.ricochet.ui;

import java.util.List;

import android.graphics.PointF;
import android.util.Log;

import com.absolom.utility.common.Path;
import com.absolom.utility.common.PointCoordinates;

public class PositionPath extends Path<PositionPath, Float, PointCoordinates> {

	private float m_cachedLength = -1F;
	
	private static final long serialVersionUID = 1L;

	public PositionPath() {
		super();
	}
	
	public PositionPath(PositionPath copy) {
		super(copy);
	}
	
	public PositionPath(List<PointCoordinates> positions) {
		super(positions);
	}

	public PositionPath(PointCoordinates firstWayPoint) {
		super(firstWayPoint);
	}
	
	@Override
	public void addWayPoint(PointCoordinates coordinates) {
		m_cachedLength = -1F;
		super.addWayPoint(coordinates);
	}

	@Override
	protected PositionPath create(List<PointCoordinates> positions) {
		return new PositionPath(positions);
	}

	public float getLength() {
		if (m_cachedLength > -0.5F){
			return m_cachedLength;
		}
			
		float length = 0;
		PointCoordinates previousCoordinates = null;
		for(PointCoordinates coordinates: m_wayPoints){
			if (previousCoordinates != null){
				length += getLength(previousCoordinates, coordinates);
			}
			previousCoordinates = coordinates;
		}
		
		m_cachedLength = length;
		return length;
	}

	private double getLength(PointCoordinates previousCoordinates, PointCoordinates coordinates) {
		double y = Math.pow(coordinates.getY() - previousCoordinates.getY(), 2);
		double x = Math.pow(coordinates.getX() - previousCoordinates.getX(), 2);
		return Math.sqrt(x + y);
	}

	public PointF getPositionFromRatio(float ratio) {
		float length = ratio * getLength();
		
		PointCoordinates previousCoordinates = null;
		for(PointCoordinates coordinates: m_wayPoints){
			if (previousCoordinates != null){
				float segmentLength = (float)getLength(previousCoordinates, coordinates);
				
				
				//Log.w("ratio", "Length:" + getLength() + " SegmentLength:" + segmentLength + " SegmentRatio:" + segmentRatio + " Ratio:" + newRatio);
				
				if (length < segmentLength || Math.abs(length - segmentLength) < 0.00001F){
					PointF point = getPosition(length / segmentLength, previousCoordinates, coordinates);
					Log.w("ratio", point.toString());
					return point;
				}
				
				length -= segmentLength;
			}
			previousCoordinates = coordinates;
		}
		return m_wayPoints.get(0).toPointF();
	}

	private PointF getPosition(float ratio, PointCoordinates previousCoordinates, PointCoordinates coordinates) {
		return new PointF((ratio * (coordinates.getX() - previousCoordinates.getX())) + previousCoordinates.getX(),
				          (ratio * (coordinates.getY() - previousCoordinates.getY())) + previousCoordinates.getY());
	}
}