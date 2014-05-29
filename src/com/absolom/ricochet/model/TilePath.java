package com.absolom.ricochet.model;

import java.util.List;

import com.absolom.utility.common.*;

public class TilePath extends Path<TilePath, Integer, TileCoordinates> {

	private static final long serialVersionUID = 1L;

	public TilePath() {
		super();
	}
	
	
	public TilePath(TilePath copy) {
		super(copy);
	}
	
	public TilePath(List<TileCoordinates> positions) {
		super(positions);
	}

	public TilePath(TileCoordinates firstWayPoint) {
		super(firstWayPoint);
	}

	@Override
	protected TilePath create(List<TileCoordinates> positions) {
		return new TilePath(positions);
	}
	
	
//	private static final long serialVersionUID = 1L;
//	private ArrayList<TileCoordinates> m_wayPoints;
//
//	public TilePath() {
//		m_wayPoints = new ArrayList<TileCoordinates>();
//	}
//
//	public static TilePath createEmpty() {
//		return new TilePath();
//	}
//
//	public TilePath(TilePath copy) {
//		this(copy.m_wayPoints);
//	}
//
//	public TilePath(List<TileCoordinates> positions) {
//		this();
//		m_wayPoints.addAll(positions);
//	}
//
//	public TilePath(TileCoordinates firstWayPoint) {
//		this();
//		addWayPoint(firstWayPoint);
//	}
//
//	public int getWayPointCount() {
//		return m_wayPoints.size();
//	}
//
//	public boolean isRevert(TilePath other) {
//		if (getWayPointCount() <= 1) {
//			return false;
//		}
//
//		if (getWayPointCount() != other.getWayPointCount()) {
//			return false;
//		}
//
//		for (int i = 0, j = getWayPointCount() - 1; i < getWayPointCount(); i++, j--) {
//			if (!m_wayPoints.get(i).equals(other.m_wayPoints.get(j))) {
//				return false;
//			}
//		}
//
//		return true;
//	}
//
//	public TileCoordinates getLastWayPoint() {
//		if (m_wayPoints.isEmpty()) {
//			return null;
//		}
//
//		return m_wayPoints.get(m_wayPoints.size() - 1);
//	}
//
//	public void addWayPoint(TileCoordinates coordinates) {
//		if (!m_wayPoints.isEmpty()) {
//			TileCoordinates previous = m_wayPoints.get(m_wayPoints.size() - 1);
//
//			if (coordinates.equals(previous)) {
//				return;
//			}
//		}
//
//		m_wayPoints.add(coordinates);
//	}
//
//	public boolean isComplete() {
//		return m_wayPoints.size() <= 1;
//	}
//
//	public TilePath reverse() {
//		List<TileCoordinates> tiles = new ArrayList<TileCoordinates>(m_wayPoints);
//		Collections.reverse(tiles);
//		return new TilePath(tiles);
//	}
}
