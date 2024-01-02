package frames;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author Benjamin Wong-Lee
 *
 */
public class GameOverScreen extends AbstractWindow {

	public GameOverScreen() {
		super();

		this.setTitle("Game Over");
		GameOverPanel panel = new GameOverPanel();
		this.add(panel);
		setFrameProperties();
	}

	private class GameOverPanel extends JPanel {

		public GameOverPanel() {
			List<JButton> buttons = createButtons();
			// approx 600 x 600 window
			// 1/4 - 1/2 * btnWd, 3/4 - 1/2 * btnWd
			int btnWd = 160;
			int btnHt = 280;
			this.setLayout(null);
			for (int i = 0; i < buttons.size(); i++) {
				JButton button = buttons.get(i);
				if (i == 0) {
					button.setBounds(new Rectangle(300, 240, btnWd, btnHt));
				}
				if (i == 1) {
					button.setBounds(new Rectangle(550, 240, btnWd, btnHt));
				}
				this.add(button);
			}
		}

		public void paintComponent(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(new Color(150, 0, 0));
			int[] xPoints = new int[3];
			int[] yPoints = new int[3];

			// Unchanging coordinate variables that are no 0
			yPoints[1] = this.getHeight();
			yPoints[2] = this.getHeight();

			for (int i = 0; i <= 120; i++) {
				if (i % 20 == 0) {
					if (i == 0) {
						g.setColor(new Color(0, 40, 150));
					} else if (i == 20) {
						g.setColor(new Color(0, 0, 120));
					} else if (i == 40) {
						g.setColor(new Color(0, 0, 100));
					} else if (i == 60) {
						g.setColor(new Color(0, 0, 80));
					} else if (i == 80) {
						g.setColor(new Color(0, 0, 60));
					} else if (i == 100) {
						g.setColor(new Color(0, 0, 40));
					}
				}
				xPoints[2] = this.getWidth() - (i * 6);
				g.fillPolygon(xPoints, yPoints, 3);
			}
		}
	}
}
