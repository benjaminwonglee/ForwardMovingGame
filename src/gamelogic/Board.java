package gamelogic;

import frames.GameOverScreen;
import tiles.Desert;
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

	/**
	 * Constructs a board, calls a method to fill the board with tiles.
	 */
	public Board(Logic l) {
		this.logic = l;
		tiles = new Tile[width][height];

		// Setup initial board
		for (int col = 0; col < height; col++) {
			for (int row = 0; row < width; row++) {
				tiles[row][col] = plainBoard(row, l.getTimeRunning());
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
		for (int row = 0; row < width; row++) {
			for (int col = height - 1; col > 0; col--) {
				tiles[row][col] = tiles[row][col - 1];
			}
		}

		int level = getCurrentBoardTheme(timeRunning);
		// Set Board pattern and theme here
		switch (level) {
		case 1:
			for (int row = width - 1; row >= 0; row--) {
				tiles[row][0] = plainBoard(row, timeRunning);
			}
			break;
		case 2:
			for (int row = width - 1; row >= 0; row--) {
				tiles[row][0] = plainAndDesertSimple(row, timeRunning);
			}
			break;
		case 3:
			for (int row = width - 1; row >= 0; row--) {
				tiles[row][0] = plainAndDesertAlt(row, timeRunning);
			}
			break;
		case 4:
			for (int row = width - 1; row >= 0; row--) {
				tiles[row][0] = plainAndDesertRandom(row, timeRunning);
			}
			break;
		default:
			for (int row = width - 1; row >= 0; row--) {
				tiles[row][0] = plainAndDesertSimple(row, timeRunning);
			}
			break;
		}
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
	 * @return The tile in the row that has been passed in as parameter.
	 */
	public Tile plainBoard(int row, int timeRunning) {
		return new Plain();
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
	 * @return The tile in the row that has been passed in as parameter.
	 */
	public Tile plainAndDesertSimple(int row, int timeRunning) {
		if (timeRunning % 2 == 1) {
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
	 * @return The tile in the row that has been passed in as parameter.
	 */
	public Tile plainAndDesertAlt(int row, int timeRunning) {
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
	 * @return The tile in the row that has been passed in as parameter.
	 */
	private Tile plainAndDesertRandom(int row, int timeRunning) {
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
			System.out.println("LEFT");
			return movePlayer(-1);
		case "right":
			System.out.println("RIGHT");
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
			// new Tile is not traversable
			new GameOverScreen();
			return false;
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
		} else if (timeRunning < 50) {
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

	public Player getPlayer() {
		return logic.getCurrentPlayer();
	}

	public int getMaxInventorySize() {
		return maxInventorySize;
	}

}
