package com.absolom.ricochet.model;

import java.util.EnumSet;
import com.absolom.utility.common.Direction;

public class GameBoard {
	private int m_tileCountX;
	private int m_tileCountY;
	private Tile m_tiles[][];

	public GameBoard(int tileCountX, int tileCountY) {
		m_tileCountX = tileCountX;
		m_tileCountY = tileCountY;
		m_tiles = new Tile[m_tileCountX][m_tileCountY];

		for (int x = 0; x < m_tileCountX; x++) {
			for (int y = 0; y < m_tileCountY; y++) {
				addTile(new Tile(x, y));
			}
		}
	}

	private void addTile(Tile cell) {
		m_tiles[cell.getX()][cell.getY()] = cell;
	}

	public int getTileCountX() {
		return m_tileCountX;
	}

	public int getTileCountY() {
		return m_tileCountY;
	}

	public Tile getTile(int x, int y) {
		return m_tiles[x][y];
	}

	public Tile getTile(TileCoordinates coordinates) {
		return m_tiles[coordinates.getX()][coordinates.getY()];
	}

	public void setItemOnTile(GameBoardItem item, TileCoordinates coordinates) {
		setItemOnTile(item, coordinates.getX(), coordinates.getY());
	}

	public void setItemOnTile(GameBoardItem item, int x, int y) {
		Tile tile = getTile(x, y);
		tile.addEntityId(item.getEntityId());
		item.setCoordinates(tile.getCoordinates());
	}

	public void removeItemFromCurrentTile(GameBoardItem item) {
		Tile tile = getTile(item.getCoordinates());
		if (tile != null) {
			tile.removeItem(item.getEntityId());
		}
	}

	public void moveItem(GameBoardItem item, TileCoordinates newCoordinates) {
		removeItemFromCurrentTile(item);
		setItemOnTile(item, newCoordinates);
	}

	public Tile getNextTile(Tile from, Direction direction) {
		int x = from.getX();
		int y = from.getY();

		if (direction == Direction.Up)
			y -= 1;
		if (direction == Direction.Right)
			x += 1;
		if (direction == Direction.Down)
			y += 1;
		if (direction == Direction.Left)
			x -= 1;

		if (x < 0 || y < 0 || x >= getTileCountX() || y >= getTileCountY()) {
			return null;
		}

		return m_tiles[x][y];
	}

	public void setWalled(int x, int y, EnumSet<Direction> directions) {
		Tile tile = getTile(x, y);
		if (tile == null){
			return;
		}
		tile.setWalled(directions);
		for(Direction direction: directions){
			getNextTile(tile, direction).setWalled(direction.reverse());
		}
		
	}

	public Tile[][] getAllTiles() {
		return m_tiles.clone();
	}
}
