package levels;

import tiles.Tile;

public interface Level {
	/**
	 * Creates the new row of tiles at the top, Tile by tile. Uses the current
	 * pattern of the board to create a single row of tiles. Universal
	 * parameters to keep consistency with other themes that may implement row,
	 * column, and timeRunning.
	 * 
	 * @param row
	 *            The current row for the tile.
	 * @param timeRunning
	 *            The amount of time the game has been running (seconds).
	 * @param item TODO
	 * @param monster
	 *            True if there has been a monster in this row.
	 * @param sea TODO
	 * @param lava TODO
	 * @return The tile in the row that has been passed in as parameter.
	 */
	public Tile board(int row, int timeRunning, boolean item, boolean monster, boolean sea, boolean lava);
}
