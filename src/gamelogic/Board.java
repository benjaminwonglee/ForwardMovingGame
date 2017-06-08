package gamelogic;

import java.util.ArrayList;

import items.Bike;
import items.Flippers;
import items.Item;
import items.Sword;
import levels.*;
import tiles.ItemTile;
import tiles.Lava;
import tiles.MonsterTile;
import tiles.Sea;
import tiles.Tile;

/**
 * A class representing the game board. Responsible for movement of Player and
 * Board. Board represents a Controller in the MVC, with an additional function
 * to store the tiles of the board. Do not store other things here; store in
 * Logic, which represents the Model.
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
	// Level Flags
	private int rowMonsterCount = 0;
	private int rowLavaCount = 0;
	private int rowSeaCount = 0;

	/**
	 * Constructs a board, calls a method to fill the board with tiles.
	 */
	public Board(Logic l) {
		this.logic = l;
		tiles = new Tile[width][height];
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new Bike());
		items.add(new Flippers());
		items.add(new Sword());
		ItemTile.setItems(items);

		// Setup initial board
		for (int col = 0; col < height; col++) {
			for (int row = 0; row < width; row++) {
				tiles[row][col] = new PlainLevel().board(row, l.getTimeRunning(), true, true, true, true);
			}
		}
	}

	/**
	 * Creates all the tiles on the Board after 1 iteration. Every tile becomes
	 * its successor. Height is in reverse positive: Highest value = bottom of
	 * board. timeRunning in game is used to draw the new board and make the new
	 * set of tiles. Called from Timer scheduled every second. The level is set
	 * in the DrawTask based on number of steps in game.
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
					// Lose life if the tile is not traversable
					if (!tiles[row][col - 1].isTraversable(player)) {
						player.setLife(player.getLife() - 1);
						drawPlayerDamageImage();
						logic.checkGameOver();
					}
				}
				tiles[row][col] = tiles[row][col - 1];
			}
		}

		/*
		 * Cannot have too many monsters/lava or a monster/lava tile on 3
		 * consecutive rows
		 */
		boolean item = false;
		boolean monster = false;
		boolean sea = false;
		boolean lava = false;

		if (rowMonsterCount == 2 || rowSeaCount == 2 || rowLavaCount == 2) {
			item = true;
			monster = true;
			sea = true;
			lava = true;
			item = true;
			rowMonsterCount = 0;
			rowLavaCount = 0;
			rowSeaCount = 0;
		}

		// Change the Color of the side panel when levels increase
		if (logic.getLevel() == 5) {
			logic.getFrame().getSidePanel().setColorChange1(true);
			logic.getFrame().getSidePanel().repaint();
		} else if (logic.getLevel() == 10) {
			logic.getFrame().getSidePanel().setColorChange1(false);
			logic.getFrame().getSidePanel().setColorChange2(true);
			logic.getFrame().getSidePanel().repaint();
		}

		/*
		 * Set Board pattern and theme here. The level is set by a DrawTask
		 * object which is determined by timer steps.
		 */
		Level l = null;
		switch (logic.getLevel()) {
		case 1:
			l = new PlainLevel();
			break;
		case 2:
		case 3:
			l = new PlainDesertSimpleLevel();
			break;
		case 4:
		case 5:
			l = new PlainAndDesertAlternatingLevel();
			break;
		case 6:
		case 7:
			l = new PlainAndDesertRandomLevel();
			break;
		case 8:
		case 9:
		case 10:
			l = new SeaLevelOne();
			break;
		case 11:
		case 12:
		case 13:
			l = new LavaLevelOne();
			break;
		default:
			l = new LavaLevelOne();
			break;
		}

		for (int row = 0; row < width; row++) {
			Tile t = l.board(row, timeRunning, item, monster, sea, lava);
			if (t instanceof MonsterTile) {
				monster = true;
				rowMonsterCount++;
			}
			if (t instanceof Lava) {
				lava = true;
				rowLavaCount++;
			}
			if (t instanceof Sea) {
				sea = true;
				rowSeaCount++;
			}
			if (t instanceof ItemTile) {
				item = true;
			}
			tiles[row][0] = t;
		}

	}

	public void drawPlayerDamageImage() {
		logic.getFrame().getDrawing().setPlayerDamaged(true);
		logic.getFrame().getDrawing().repaint();
	}

	public Tile monsterGen(boolean monster) {
		if (!monster) {
			int rand = (int) Math.floor((Math.random() * 3));
			if (rand == 1) {
				return new MonsterTile();
			}
		}
		return null;
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
		} else if (tiles[newX][height - 1] instanceof ItemTile) {
			// New Tile has an item
			logic.pickUpItem(newX);
			// Ask the inventory panel to update
			logic.getFrame().invUpdate();
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

	public int getMaxInventorySize() {
		return maxInventorySize;
	}
}
