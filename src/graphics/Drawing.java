package graphics;

import gamelogic.Logic;
import tiles.*;

import javax.swing.*;
import java.awt.*;

/**
 * The main view of the game. Draws everything in the main game.
 *
 * @author Benjamin Wong-Lee
 */
public class Drawing extends JPanel {

	private boolean playerDamaged = false;
	private int damageIter;

	/* Auxiliary */
	private final Logic game; // Controller
	private final ImageHandler imageHandler;

	private int boardWd;
	private int boardHt;



	public Drawing(int width, int height, int boardWd, int boardHt, Logic game, ImageHandler imageHandler) {
		this.game = game;
		this.imageHandler = imageHandler;

		this.setPreferredSize(new Dimension(width, height));
		this.boardWd = boardWd;
		this.boardHt = boardHt;

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		double sqH = this.getHeight() / boardHt;
		double sqW = this.getWidth() / boardWd;
		int ht = (int) sqH;
		int wd = (int) sqW;
		for (int row = 0; row < boardWd; row++) {
			for (int col = 0; col < boardHt; col++) {
				if (game.checkSquare(row, col) instanceof Plain) {
					drawPlain(g, row, col, wd, ht);
				} else if (game.checkSquare(row, col) instanceof Desert) {
					drawDesert(g, row, col, wd, ht);
				} else if (game.checkSquare(row, col) instanceof Sea) {
					drawSea(g, row, col, wd, ht);
				} else if (game.checkSquare(row, col) instanceof Lava) {
					drawLava(g, row, col, wd, ht);
				} else if (game.checkSquare(row, col) instanceof ItemTile) {
					drawItem(g, row, col, wd, ht);
				} else if (game.checkSquare(row, col) instanceof LifeTile) {
					drawLife(g, row, col, wd, ht);
				} else if (game.checkSquare(row, col) instanceof MonsterTile) {
					drawMonster(g, row, col, wd, ht);
				} else if (game.checkSquare(row, col) instanceof Mountain) {
					drawMountain(g, row, col, wd, ht);
				}
				// Draw grid by outlining individual square
				g.setColor(new Color(70, 70, 70));
				g.drawRect(row * wd, (col * ht), wd, ht);
			}
		}
		drawPlayer(g, wd, ht);
	}

	/**
	 * Draws the player on the tile
	 *
	 * @param g
	 *            The Graphics object
	 * @param wd
	 *            The width of the square
	 * @param ht
	 *            The height of the square
	 */
	private void drawPlayer(Graphics g, int wd, int ht) {
		// Draws the player on a tile
		int x = game.getPlayerXPos();
		Image personImg;
		if (playerDamaged) {
			damageIter--;
			if (damageIter == 0) {
				playerDamaged = false;
			}
			personImg = imageHandler.loadImage(AssetConstants.PERSON_DAMAGE_IMG_NAME);
		} else {
			personImg = imageHandler.loadImage(AssetConstants.PERSON_IMG_NAME);
		}

		for (int i = 7; i >= 0; i--) {
			g.drawImage(personImg, x * wd, (boardHt - 1) * ht, wd - i, ht, null);
		}
	}

	/**
	 * Draw a plain of grass on the tile.
	 *
	 * @param g
	 *            Graphics object of the component
	 * @param row
	 *            The current row
	 * @param col
	 *            The current column
	 * @param wd
	 *            The width of the square
	 * @param ht
	 *            The height of the square
	 */
	private void drawPlain(Graphics g, int row, int col, int wd, int ht) {
		g.setColor(new Color(0, 180, 20));
		g.fillRect(row * wd, col * ht, wd, ht);
	}

	/**
	 * Draw desert terrain on the tile.
	 *
	 * @param g
	 *            Graphics object of the component
	 * @param row
	 *            The current row
	 * @param col
	 *            The current column
	 * @param wd
	 *            The width of the square
	 * @param ht
	 *            The height of the square
	 */
	private void drawDesert(Graphics g, int row, int col, int wd, int ht) {
		g.setColor(new Color(180, 180, 0));
		g.fillRect(row * wd, col * ht, wd, ht);
	}

	/**
	 * Draw a sea pattern on the tile.
	 *
	 * @param g
	 *            Graphics object of the component
	 * @param row
	 *            The current row
	 * @param col
	 *            The current column
	 * @param wd
	 *            The width of the square
	 * @param ht
	 *            The height of the square
	 */
	private void drawSea(Graphics g, int row, int col, int wd, int ht) {
		g.setColor(new Color(0, 0, 220));
		g.fillRect(row * wd, col * ht, wd, ht);
		g.setColor(new Color(170, 170, 230));
		for (int i = 0; i < 10; i++) {
			int xPos = (int) (Math.random() * wd);
			int yPos = (int) (Math.random() * ht);
			if (xPos + 16 > wd) {
				xPos -= 20;
			}
			if (yPos - 16 < ht) {
				yPos += 20;
			}
			// Draw sea wave lines
			g.drawLine((row * wd) + xPos, (col * ht) + yPos, (row * wd) + xPos + 8, (col * ht) + yPos - 8);
			g.drawLine((row * wd) + xPos + 8, (col * ht) + yPos - 8, (row * wd) + xPos + 16, (col * ht) + yPos);
			g.drawLine((row * wd) + xPos + 1, (col * ht) + yPos - 1, (row * wd) + xPos + 9, (col * ht) + yPos - 9);
			g.drawLine((row * wd) + xPos + 9, (col * ht) + yPos - 9, (row * wd) + xPos + 17, (col * ht) + yPos - 1);
		}
	}

	/**
	 * Draw a lava pattern on the tile.
	 *
	 * @param g
	 *            Graphics object of the component
	 * @param row
	 *            The current row
	 * @param col
	 *            The current column
	 * @param wd
	 *            The width of the square
	 * @param ht
	 *            The height of the square
	 */
	private void drawLava(Graphics g, int row, int col, int wd, int ht) {
		g.setColor(new Color(200, 30, 30));
		g.fillRect(row * wd, col * ht, wd, ht);
		g.setColor(new Color(200, 150, 150));
		for (int i = 0; i < 5; i++) {
			int xPos = (int) (Math.random() * wd);
			int yPos = (int) (Math.random() * ht);
			int bubSize = 20;
			if (xPos + bubSize > wd) {
				xPos -= (bubSize + 4);
			}
			if (yPos - bubSize < ht) {
				yPos += (bubSize + 4);
			}
			// Draw lava bubbles
			g.fillOval((row * wd) + xPos, (col * ht) + yPos, bubSize, bubSize);
		}
	}

	/**
	 * Draws an item on the tile.
	 *
	 * @param g
	 *            Graphics object of the component
	 * @param row
	 *            The current row
	 * @param col
	 *            The current column
	 * @param wd
	 *            The width of the square
	 * @param ht
	 *            The height of the square
	 */
	private void drawItem(Graphics g, int row, int col, int wd, int ht) {
		ItemTile item = (ItemTile) game.checkSquare(row, col);
		if (item != null) {
			String itemName = item.getItemName();
			Image itemImg = imageHandler.loadImage(itemName);
			g.drawImage(itemImg, row * wd, (col * ht), wd, ht, this);
		}
	}

	/**
	 * Draws an life item on the tile.
	 *
	 * @param g
	 *            Graphics object of the component
	 * @param row
	 *            The current row
	 * @param col
	 *            The current column
	 * @param wd
	 *            The width of the square
	 * @param ht
	 *            The height of the square
	 */
	private void drawLife(Graphics g, int row, int col, int wd, int ht) {
		g.drawImage(imageHandler.loadImage(AssetConstants.LIFE_ITEM_IMG_NAME), row * wd, (col * ht), wd, ht, this);
	}

	/**
	 * Draws a monster image on the tile. Relies on monster image to be in the
	 * root images directory of the project.
	 *
	 * @param g
	 *            Graphics object of the component
	 * @param row
	 *            The current row
	 * @param col
	 *            The current column
	 * @param wd
	 *            The width of the square
	 * @param ht
	 *            The height of the square
	 */
	private void drawMonster(Graphics g, int row, int col, int wd, int ht) {
		g.drawImage(imageHandler.loadImage(AssetConstants.MONSTER_IMG_NAME), row * wd, (col * ht), wd, ht, this);
	}

	/**
	 * Draws a mountain terrain on the tile.
	 *
	 * @param g
	 *            Graphics object of the component
	 * @param row
	 *            The current row
	 * @param col
	 *            The current column
	 * @param wd
	 *            The width of the square
	 * @param ht
	 *            The height of the square
	 */
	private void drawMountain(Graphics g, int row, int col, int wd, int ht) {
		g.setColor(new Color(150, 120, 50));
		g.fillRect(row * wd, col * ht, wd, ht);
		g.setColor(new Color(180, 120, 50));
		g.fillRect(row * wd + wd / 5, col * ht + ht / 5, wd - (wd / 5 * 2), ht - (ht / 5 * 2));
		g.setColor(new Color(245, 245, 245));
		g.fillOval(row * wd + (wd / 5 * 2), col * ht + (ht / 5 * 2), wd - (wd / 5 * 4), ht - (ht / 5 * 4));
	}


	public void setPlayerDamaged(boolean damaged) {
		this.playerDamaged = damaged;
		this.damageIter = 2;
	}
}
