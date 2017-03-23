package frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class IntroScreen extends JFrame {

	private static final long serialVersionUID = -1212029084642314569L;

	public IntroScreen() {
		setPreferredSize(new Dimension(800, 800));
		this.setTitle("Forward Moving Game");
		IntroPanel panel = new IntroPanel();
		this.add(panel);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private class IntroPanel extends JPanel {

		private static final long serialVersionUID = 7543777692609420096L;

		public IntroPanel() {
			createButtons();
		}

		private void createButtons() {

		}

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(new Color(150, 0, 0));
			int[] xPoints = new int[3];
			int[] yPoints = new int[3];

			// Unchanging variables
			yPoints[0] = 0;
			yPoints[2] = this.getHeight();

			for (int i = 0; i < 120; i++) {
				if (i == 0) {
					g.setColor(new Color(150, 40, 0));
				} else if (i == 20) {
					g.setColor(new Color(6 * i, 0, 0));
				} else if (i == 40) {
					g.setColor(new Color(3 * i, 0, 0));
				} else if (i == 60) {
					g.setColor(new Color(i, 0, 0));
				} else if (i == 80) {
					g.setColor(new Color(i - 20, 0, 0));
				} else if (i == 100) {
					g.setColor(new Color(i - 60, 0, 0));
				} else if (i == 120) {
					g.setColor(new Color(i - 100, 0, 0));
				}
				xPoints[0] = 0;
				xPoints[1] = this.getWidth() - (3 * i);
				xPoints[2] = this.getWidth();
				yPoints[1] = 0 + i;

				g.fillPolygon(xPoints, yPoints, 3);
			}
		}
	}
}
