package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import gamelogic.Logic;
import tiles.Desert;
import tiles.ItemTile;
import tiles.Lava;
import tiles.MonsterTile;
import tiles.Mountain;
import tiles.Plain;
import tiles.Sea;

/**
 * 
 * @author Benjamin Wong-Lee
 */
public class Drawing extends JPanel {

	private static final long serialVersionUID = 6414312707379660792L;

	private int boardWide;
	private int boardHigh;
	private Logic game; // Controller

	private boolean playerDamaged = false;
	private int damageIter;

	public Drawing(int width, int height, int boardWide, int boardHigh, Logic game) {
		this.setPreferredSize(new Dimension(width, height));
		this.boardWide = boardWide;
		this.boardHigh = boardHigh;
		this.game = game;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		double sqH = this.getHeight() / boardHigh;
		double sqW = this.getWidth() / boardWide;
		int h = (int) sqH;
		int w = (int) sqW;
		for (int row = 0; row < boardWide; row++) {
			for (int col = 0; col < boardHigh; col++) {
				if (game.checkSquare(row, col) instanceof Plain) {
					// Draw an empty plain
					g.setColor(new Color(0, 180, 20));
					g.fillRect(row * w, col * h, w, h);
				} else if (game.checkSquare(row, col) instanceof Desert) {
					g.setColor(new Color(180, 180, 0));
					g.fillRect(row * w, col * h, w, h);
				} else if (game.checkSquare(row, col) instanceof Sea) {
					g.setColor(new Color(0, 0, 220));
					g.fillRect(row * w, col * h, w, h);
					g.setColor(new Color(170, 170, 230));
					for (int i = 0; i < 20; i++) {
						int xPos = (int) (Math.random() * w);
						int yPos = (int) (Math.random() * h);
						if (xPos + 16 > w) {
							xPos -= 20;
						}
						if (yPos - 16 < h) {
							yPos += 20;
						}
						// Draw sea wave lines
						g.drawLine((row * w) + xPos, (col * h) + yPos, (row * w) + xPos + 8, (col * h) + yPos - 8);
						g.drawLine((row * w) + xPos + 8, (col * h) + yPos - 8, (row * w) + xPos + 16, (col * h) + yPos);
						g.drawLine((row * w) + xPos + 1, (col * h) + yPos - 1, (row * w) + xPos + 9,
								(col * h) + yPos - 9);
						g.drawLine((row * w) + xPos + 9, (col * h) + yPos - 9, (row * w) + xPos + 17,
								(col * h) + yPos - 1);
					}
				} else if (game.checkSquare(row, col) instanceof Lava) {
					g.setColor(new Color(200, 30, 30));
					g.fillRect(row * w, col * h, w, h);
				} else if (game.checkSquare(row, col) instanceof ItemTile) {
					ItemTile item = (ItemTile) game.checkSquare(row, col);
					java.awt.Image itemImg = null;
					try {
						itemImg = ImageIO.read(new File("images/" + item.getItem().getName() + ".png"));
					} catch (IOException e) {
						System.err.println("Couldn't read image file of " + item.getName());
						e.printStackTrace();
					}
					g.drawImage(itemImg, row * w, (col * h), w, h, this);

				} else if (game.checkSquare(row, col) instanceof MonsterTile) {
					// Draw a monster
					java.awt.Image monsterImg = null;
					try {
						monsterImg = ImageIO.read(new File("images/monster.png"));
					} catch (IOException e) {
						System.err.println("Couldn't read image file of monster");
						e.printStackTrace();
					}
					g.drawImage(monsterImg, row * w, (col * h), w, h, this);
				} else if (game.checkSquare(row, col) instanceof Mountain) {
					// Draw a mountain
					g.setColor(new Color(150, 120, 50));
					g.fillRect(row * w, col * h, w, h);
					g.setColor(new Color(180, 120, 50));
					g.fillRect(row * w + w / 5, col * h + h / 5, w - (w / 5 * 2), h - (h / 5 * 2));
					g.setColor(new Color(245, 245, 245));
					g.fillOval(row * w + (w / 5 * 2), col * h + (h / 5 * 2), w - (w / 5 * 4), h - (h / 5 * 4));
				}
				// Draw grid by outlining individual square
				g.setColor(new Color(70, 70, 70));
				g.drawRect(row * w, (col * h), w, h);
			}
		}

		// Draw current player
		int x = game.getPlayerXPos();
		java.awt.Image img = null;
		String personImg = "";
		if (playerDamaged) {
			damageIter--;
			if (damageIter == 0) {
				playerDamaged = false;
			}
			personImg = "images/person_damage.png";
		} else {
			personImg = "images/person.png";
		}
		try {
			img = ImageIO.read(new File(personImg));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 7; i >= 0; i--) {
			g.drawImage(img, x * w, (boardHigh - 1) * h, w - i, h, null);
		}
	}

	public void setPlayerDamaged(boolean damaged) {
		this.playerDamaged = damaged;
		this.damageIter = 2;
	}

}
