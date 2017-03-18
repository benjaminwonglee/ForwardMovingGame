package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.text.html.ImageView;

import gamelogic.Logic;
import tiles.*;

public class Drawing extends JPanel {

	private static final long serialVersionUID = 6414312707379660792L;

	private int boardWide;
	private int boardHigh;
	private Logic game; // Controller
	private int colorTone = 0;
	private int colorToneChange = 3;
	private boolean reverse = false;
	private boolean debug = true;

	public Drawing(int width, int height, int boardWide, int boardHigh, Logic game) {
		this.setPreferredSize(new Dimension(width, height));
		this.boardWide = boardWide;
		this.boardHigh = boardHigh;
		this.game = game;
	}

	@Override
	public void paintComponent(Graphics g) {
		if (colorTone < 30 && !reverse) {
			// ColorTone 0 to 30
			colorTone += colorToneChange;
		} else if (colorTone > -15) {
			// colorTone 30 to -15 (reverse)
			reverse = true;
			colorTone -= colorToneChange;
		} else {
			// colorTone equals 0
			reverse = false;
			colorTone += colorToneChange;
		}
		if (debug) {
			System.out.println("ColorTone: " + colorTone);
		}
		super.paintComponent(g);
		double sqH = this.getHeight() / boardHigh;
		double sqW = this.getWidth() / boardWide;
		int h = (int) sqH;
		int w = (int) sqW;
		for (int row = 0; row < boardWide; row++) {
			for (int col = 0; col < boardHigh; col++) {
				if (game.checkSquare(row, col) instanceof Plain) {
					// Draw an empty plain
					g.setColor(new Color(0, 180 + colorTone, 20 + colorTone));
					g.fillRect(row * w, (col * h), w, h);
				} else if (game.checkSquare(row, col) instanceof Desert) {
					g.setColor(new Color(220 + colorTone, 220 + colorTone, 0));
					g.fillRect(row * w, (col * h), w, h);
				} else if (game.checkSquare(row, col) instanceof Sea) {
					// Draw a sea panel
					g.setColor(new Color(0, 0, 220 + colorTone));
					g.fillRect(row * w, (col * h), w, h);
				} else if (game.checkSquare(row, col) instanceof MonsterTile) {
					// Draw a monster
					java.awt.Image monsterImg = null;
					try {
						monsterImg = ImageIO.read(new File("images/monster.bmp"));
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
		try {
			img = ImageIO.read(new File("images/person.bmp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(img, x * w, (boardHigh - 1) * h, w, h, null);
	}
}
