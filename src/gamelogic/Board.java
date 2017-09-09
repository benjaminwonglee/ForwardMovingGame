package gamelogic;

import java.util.ArrayList;

import items.Bike;
import items.Flippers;
import items.Item;
import items.Sword;
import levels.LavaLevelOne;
import levels.Level;
import levels.PlainAndDesertAlternatingLevel;
import levels.PlainAndDesertRandomLevel;
import levels.PlainDesertSimpleLevel;
import levels.PlainLevel;
import levels.SeaLevelOne;
import tiles.ItemTile;
import tiles.Lava;
import tiles.LifeTile;
import tiles.MonsterTile;
import tiles.Sea;
import tiles.Tile;

/**
 * A class representing the game board. Responsible for movement of Player and
 * Board. Board represents a Model in the MVC.
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
				tiles[row][col] = new PlainLevel().tileBoardTopRow(row, l.getTimeRunning(), true, true, true, true);
			}
		}
	}

	/**
	 * Creates all the tiles on the Board. Each tile becomes the tile in front
	 * of it; except the top row which is newly tiled. Height is in reverse
	 * positive: Highest value = bottom of board. timeRunning in game is used to
	 * draw the new board and make the new set of tiles. Called from Timer
	 * scheduled every second.
	 * 
	 * @param timeRunning
	 *            The amount of time the game has been running (seconds).
	 */
	public void createTiles() {
		// Shifts all tiles down board by 1 tile
		for (int row = 0; row < width; row++) {
			for (int col = height - 1; col > 0; col--) {
				if (col == height - 1 && row == logic.getPlayer().getXPos()) {
					checkTileEffect(row, col);
				}
				tiles[row][col] = tiles[row][col - 1];
			}
		}

		adjustSidePanelColor();
		Level l = setLevelTheme();
		countObstacles(l);
	}

	/**
	 * Checks the number of obstacles in each row to determine whether the row
	 * is passable without getting hurt too badly
	 */
	private void countObstacles(Level l) {
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
		/*
		 * Counts the number of obstacles in the new row.
		 */
		for (int row = 0; row < width; row++) {
			// Tiles the top row with new tiles
			Tile t = l.tileBoardTopRow(row, logic.getTimeRunning(), item, monster, sea, lava);
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

	/**
	 * Changes the Color of the side panel when levels increase
	 */
	private void adjustSidePanelColor() {
		if (logic.getLevel() == 5) {
			logic.getFrame().getSidePanel().setColorChange1(true);
			logic.getFrame().getSidePanel().repaint();
		} else if (logic.getLevel() == 10) {
			logic.getFrame().getSidePanel().setColorChange1(false);
			logic.getFrame().getSidePanel().setColorChange2(true);
			logic.getFrame().getSidePanel().repaint();
		}
	}

	/**
	 * Set Board pattern and theme here. The level is set by a DrawTask object
	 * which is determined by timer steps. Determined in Logic, updated on
	 * Board.
	 */
	private Level setLevelTheme() {
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
		return l;
	}

	/**
	 * Checks the effect of tile the Player is on.
	 * 
	 * @param row
	 *            The row of the next tile that the player will be on
	 * @param col
	 *            The column of the next tile that the player will be on
	 */
	public void checkTileEffect(int row, int col) {
		Player player = logic.getPlayer();
		// Lose life if the tile is not traversable
		if (!tiles[row][col - 1].isTraversable(player)) {
			// New Tile is not traversable
			player.setLife(player.getLife() - 1);
			// Let the player move there anyway but loses a life
			drawPlayerDamageImage();
			logic.checkGameOver();
		} else if (tiles[row][col - 1] instanceof ItemTile) {
			// New Tile has an item
			logic.pickUpItem((ItemTile) tiles[row][col - 1]);
		} else if (tiles[row][col - 1] instanceof LifeTile) {
			if (player.getLife() != 3) {
				player.setLife(player.getLife() + 1);

			}
		}
		logic.getFrame().getSidePanel().updateLifePanel();
	}

	/**
	 * Draws a new image on the player when indicated that the player has been
	 * damaged. Updates View (Drawing).
	 */
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
		Player player = logic.getPlayer();
		// Change the position of the player and return true
		int newX = x + player.getXPos();
		// Check for invalid inputs
		if (newX > tiles.length - 1) {
			// > width
			return false;
		} else if (newX < 0) {
			// Less than board dimensions
			return false;
		}
		checkTileEffect(newX, height);
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

	public static int getMaxInventorySize() {
		return maxInventorySize;
	}
}
