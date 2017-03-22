package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameOverScreen extends JFrame {
	private static final long serialVersionUID = -4909362608024151100L;

	public GameOverScreen() {
		setPreferredSize(new Dimension(800, 800));
		this.setTitle("Game Over");
		GameOverPanel panel = new GameOverPanel();
		this.add(panel);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private class GameOverPanel extends JPanel {
		private static final long serialVersionUID = -8583261398361793225L;

		@Override
		public void paintComponent(Graphics g) {
			int inward = 0;
			for (int i = 0; i < 7; i++) {
				if (inward % 2 == 1) {
					g.setColor(new Color(0, 0, 100));
				} else {
					g.setColor(new Color(0, 0, 50));
				}
				g.fillRect(0 + inward, 0 + inward, this.getWidth() - (inward * 2), this.getHeight() - (inward * 2));
				inward += 41;
			}
			//TODO: Draw random red triangles here
			g.setColor(new Color(150, 0, 0));
			//g.fillPolygon(xPoints, yPoints, nPoints);
			
		}
	}
}
