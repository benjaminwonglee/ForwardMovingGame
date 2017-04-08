package gamelogic;

import graphics.Drawing;
import tiles.Desert;
import tiles.MonsterTile;
import tiles.Plain;
import tiles.Tile;

/**
 * A class representing the game board. Responsible for movement of Player and
 * Board.
 * 
 * @author Benjamin Wong-Lee
 *
 */
public class Board {
	private Logic logic = null;
	private Tile[][] tiles;
	private int width = 3;
	private int height = 6;
	private static final int maxInventorySize = 3;
	private int rowMonsterCount = 0;
	private int level = 0;

	/**
	 * Constructs a board, calls a method to fill the board with tiles.
	 */
	public Board(Logic l) {
		this.logic = l;
		tiles = new Tile[width][height];

		// Setup initial board
		for (int col = 0; col < height; col++) {
			for (int row = 0; row < width; row++) {
				tiles[row][col] = plainBoard(row, l.getTimeRunning(), true);
			}
		}
	}

	/**
	 * Creates all the tiles on the Board after 1 iteration. Every tile becomes
	 * its successor. Height is in reverse positive: Highest value = bottom of
	 * board. timeRunning in game is used to draw the new board and make the new
	 * set of tiles. Called from Timer scheduled every second.
	 * 
	 * @param timeRunning
	 *            The amount of time the game has been running (seconds).
	 */
	public void createTiles(int timeRunning) {
		// Shifts all tiles down board by 1 tile
		Player player = logic.getCurrentPlayer();
		for (int row = 0; row < width; row++) {
			for (int col = height - 1; col > 0; col--) {
				if (col == height - 1 && row == logic.getCurrentPlayer().getXPos()) {
					if (!tiles[row][col - 1].isTraversable(player)) {
						player.setLife(player.getLife() - 1);
						drawPlayerDamageImage();
						logic.checkGameOver();
					}
				}
				tiles[row][col] = tiles[row][col - 1];
			}
		}
		int level = getCurrentBoardTheme(timeRunning);
		this.level = level;
		// Cannot have too many monsters or 1 on 3 consecutive rows
		boolean monster = false;
		if (rowMonsterCount == 2) {
			monster = true;
			rowMonsterCount = 0;
		}

		if (level == 2) {
			logic.getFrame().getSidePanel().setColorChange1(true);
			logic.getFrame().getSidePanel().repaint();
		} else if (level == 4) {
			logic.getFrame().getSidePanel().setColorChange1(false);
			logic.getFrame().getSidePanel().setColorChange2(true);
			logic.getFrame().getSidePanel().repaint();
		}

		// Set Board pattern and theme here
		switch (level) {
		case 1:
			for (int row = 0; row < width; row++) {
				Tile t = plainBoard(row, timeRunning, monster);
				if (t instanceof MonsterTile) {
					monster = true;
					rowMonsterCount++;
				}
				tiles[row][0] = t;
			}
			break;
		case 2:
			for (int row = 0; row < width; row++) {
				Tile t = plainAndDesertSimple(row, timeRunning, monster);
				if (t instanceof MonsterTile) {
					monster = true;
					rowMonsterCount++;
				}
				tiles[row][0] = t;
			}
			break;
		case 3:
			for (int row = 0; row < width; row++) {
				Tile t = plainAndDesertAlt(row, timeRunning, monster);
				if (t instanceof MonsterTile) {
					monster = true;
					rowMonsterCount++;
				}
				tiles[row][0] = t;
			}
			break;
		case 4:
			for (int row = 0; row < width; row++) {
				Tile t = plainAndDesertRandom(row, timeRunning, monster);
				if (t instanceof MonsterTile) {
					monster = true;
					rowMonsterCount++;
				}
				tiles[row][0] = t;
			}
			break;
		default:
			for (int row = 0; row < width; row++) {
				Tile t = plainAndDesertSimple(row, timeRunning, monster);
				if (t instanceof MonsterTile) {
					monster = true;
					rowMonsterCount++;
				}
				tiles[row][0] = t;
			}
			break;
		}
	}

	public void drawPlayerDamageImage() {
		logic.getFrame().getDrawing().setPlayerDamaged(true);
		logic.getFrame().getDrawing().repaint();
	}

	/**
	 * Creates the new row of tiles at the top, Tile by tile. Uses the current
	 * pattern of the board to create a single row of tiles. Universal
	 * parameters to keep consistency with other themes that may implement row,
	 * column, and timeRunning. Plain style.
	 * 
	 * @param row
	 *            The current row for the tile.
	 * @param timeRunning
	 *            The amount of time the game has been running (seconds).
	 * @param monster
	 *            True if there has been a monster in this row.
	 * @return The tile in the row that has been passed in as parameter.
	 */
	public Tile plainBoard(int row, int timeRunning, boolean monster) {
		Tile t = monsterGen(monster);
		if (t != null) {
			return t;
		}
		return new Plain();
	}

	private Tile monsterGen(boolean monster) {
		if (!monster) {
			int rand = (int) Math.floor((Math.random() * 3));
			if (rand == 1) {
				return new MonsterTile();
			}
		}
		return null;
	}

	/**
	 * Creates the new row of tiles at the top, Tile by tile. Uses the current
	 * pattern of the board to create a single row of tiles. Plain and desert
	 * Simple pattern style.
	 * 
	 * @param row
	 *            The current row for the tile.
	 * @param timeRunning
	 *            The amount of time the game has been running (seconds).
	 * @param monster
	 *            True if there has been a monster in this row.
	 * @return The tile in the row that has been passed in as parameter.
	 */
	public Tile plainAndDesertSimple(int row, int timeRunning, boolean monster) {
		Tile t = monsterGen(monster);
		if (t != null) {
			return t;
		}
		if (timeRunning % 2 == 0) {
			return new Desert();
		} else {
			return new Plain();
		}
	}

	/**
	 * Creates the new row of tiles at the top, Tile by tile. Uses the current
	 * pattern of the board to create a single row of tiles. Plain and desert
	 * Alt pattern style.
	 * 
	 * @param row
	 *            The current row for the tile.
	 * @param timeRunning
	 *            The amount of time the game has been running (seconds).
	 * @param monster
	 *            True if there has been a monster in this row.
	 * @return The tile in the row that has been passed in as parameter.
	 */
	public Tile plainAndDesertAlt(int row, int timeRunning, boolean monster) {
		Tile t = monsterGen(monster);
		if (t != null) {
			return t;
		}
		if ((row + timeRunning) % 2 == 1) {
			return new Desert();
		} else {
			return new Plain();
		}
	}

	/**
	 * Creates the new row of tiles at the top, Tile by tile. Uses the current
	 * pattern of the board to create a single row of tiles. Universal
	 * parameters to keep consistency with other themes that may implement row,
	 * column, and timeRunning. Plain and desert randomised style.
	 * 
	 * @param row
	 *            The current row for the tile.
	 * @param timeRunning
	 *            The amount of time the game has been running (seconds).
	 * @param monster
	 *            True if there has been a monster in this row.
	 * @return The tile in the row that has been passed in as parameter.
	 */
	private Tile plainAndDesertRandom(int row, int timeRunning, boolean monster) {
		Tile t = monsterGen(monster);
		if (t != null) {
			return t;
		}
		int rand = (int) Math.floor(Math.random() * 2);
		if (rand == 1) {
			return new Desert();
		} else {
			return new Plain();
		}
	}

	/**
	 * Given a string "up", "down", "left", or "right", as well as a player to
	 * move, interprets the direction the player should move in accordance to
	 * the board.
	 * 
	 * @param dir
	 *            The direction to move.
	 * @return true if the player has been moved.
	 */
	public boolean movePlayerInDirection(String dir) {
		switch (dir) {
		case "left":
			return movePlayer(-1);
		case "right":
			return movePlayer(1);
		default:
			throw new Error("Unexpected argument: " + dir);
		}
	}

	/**
	 * Moves the given player in a direction specified by the x parameter.
	 * Positive x indicates right, negative x is left. Sets the players new
	 * position to the changed x position if true is returned, else doesn't move
	 * the player if false is returned.
	 * 
	 * @param x
	 *            Horizontal movement value.
	 * @return true if player can move to the new position.
	 */
	private boolean movePlayer(int x) {
		Player player = logic.getCurrentPlayer();
		int newX = x + player.getXPos();
		// Check for invalid inputs
		if (newX > tiles.length - 1) {
			// > width
			return false;
		} else if (newX < 0) {
			// Less than board dimensions
			return false;
		}
		if (!tiles[newX][height - 1].isTraversable(player)) {
			// New Tile is not traversable
			player.setLife(player.getLife() - 1);
			logic.checkGameOver();
			// Let the player move there anyway but loses a life
			drawPlayerDamageImage();
			player.setXPos(newX);
			return true;
		}
		// Change the position of the player and return true
		player.setXPos(newX);
		return true;
	}

	public int getCurrentBoardTheme(int timeRunning) {
		if (timeRunning < 10) {
			return 1;
		} else if (timeRunning < 30) {
			return 2;
		} else if (timeRunning < 40) {
			return 3;
		} else {
			return 4;
		}
		// TODO: Update this after more themes made
	}

	/**
	 * Returns the tile at the given coordinate (row and column).
	 * 
	 * @param row
	 *            The row coordinate. (x)
	 * @param col
	 *            The column coordinate. (y)
	 * @return the Tile that is at the coordinate.
	 */
	public Tile returnSquare(int row, int col) {
		return this.tiles[row][col];
	}

	/**
	 * Returns the Board of Tiles in a 2D array form.
	 * 
	 * @return the Board of Tiles in a 2D array form.
	 */
	public Tile[][] getTiles() {
		return tiles;
	}

	/**
	 * Returns the width of the board in squares across.
	 * 
	 * @return Integer counting the number of squares across
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the board in squares down.
	 * 
	 * @return Integer counting the number of squares down
	 */
	public int getHeight() {
		return height;
	}

	public int getMaxInventorySize() {
		return maxInventorySize;
	}

	public int getLevel() {
		return level;
	}

}
