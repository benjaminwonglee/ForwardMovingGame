package gamelogic;

import frames.GameOverScreen;
import tiles.*;

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
		// plainBoard();
		plainAndDesert();
	}

	public void plainBoard() {
		/* Construct a running board */
		for (int row = 0; row < width; row++) {
			for (int col = 0; col < height; col++) {
				tiles[row][col] = new Plain();
				// TODO: Make more complex
				// TODO: Make all ItemTiles created with MaxInventoryNumber as
				// parameter.
			}
		}
	}

	public void plainAndDesert() {
		/* Construct a running board */
		for (int row = 0; row < width; row++) {
			for (int col = 0; col < height; col++) {
				if ((row + col) % 2 == 1) {
					tiles[row][col] = new Desert();
				} else {
					tiles[row][col] = new Plain();
				}
				// TODO: Make more complex
				// TODO: Make all ItemTiles created with MaxInventoryNumber as
				// parameter.
			}
		}
	}

	public void createNextTiles(int timeRunning) {
		// TODO: Make this work properly
		for (int row = 0; row < width; row++) {
			for (int col = height - 1; col > 0; col--) {
				// Every tile becomes its successor.
				tiles[row][col] = tiles[row][col - 1];
			}
		}

		// TODO: Add this in later
		for (int row = width - 1; row >= 0; row--) {
			tiles[row][0] = createNewTiles(row, timeRunning);
		}
	}

	private Tile createNewTiles(int row, int timeRunning) {
		if ((row + height + timeRunning) % 2 == 1) {
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
