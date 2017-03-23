package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class IntroScreen extends JFrame {

	public IntroScreen() {
		setPreferredSize(new Dimension(800, 800));
		this.setTitle("Game Over");
		IntroPanel panel = new IntroPanel();
		this.add(panel);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private class IntroPanel extends JPanel {

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
			// TODO: Draw random red triangles here
			g.setColor(new Color(150, 0, 0));
			int[] xPoints = new int[3];
			int[] yPoints = new int[3];

			g.fillPolygon(xPoints, yPoints, 3);

		}
	}
}
