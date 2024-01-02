package frames;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author Benjamin Wong-Lee
 */
public class IntroScreen extends AbstractWindow {

	public IntroScreen() {
		super();

		this.setTitle("Forward Moving Game");
		IntroPanel panel = new IntroPanel();
		this.add(panel);
		setFrameProperties();
	}

	private class IntroPanel extends JPanel {

		public IntroPanel() {
			List<JButton> buttons = createButtons();
			// approx 600 x 600 window
			// 1/4 - 1/2 * btnWd, 3/4 - 1/2 * btnWd
			int btnWd = 160;
			int btnHt = 280;
			this.setLayout(null);

			for (int i = 0; i < buttons.size(); i++) {
				JButton button = buttons.get(i);
				if (i == 0) {
					button.setBounds(new Rectangle(120, 240, btnWd, btnHt));
				}
				if (i == 1) {
					button.setBounds(new Rectangle(370, 240, btnWd, btnHt));
				}
				this.add(button);
			}
		}

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(new Color(150, 0, 0));

			int[] xPoints = new int[3];
			int[] yPoints = new int[3];

			// Unchanging coordinate variables that aren't 0
			yPoints[2] = this.getHeight();

			for (int i = 0; i <= 120; i++) {
				if (i % 20 == 0) {
					if (i == 0) {
						g.setColor(new Color(150, 40, 0));
					} else if (i == 20) {
						g.setColor(new Color(120, 0, 0));
					} else if (i == 40) {
						g.setColor(new Color(100, 0, 0));
					} else if (i == 60) {
						g.setColor(new Color(80, 0, 0));
					} else if (i == 80) {
						g.setColor(new Color(60, 0, 0));
					} else if (i == 100) {
						g.setColor(new Color(40, 0, 0));
					} else if (i == 120) {
						g.setColor(new Color(20, 0, 0));
					}
				}

				xPoints[0] = 0;
				xPoints[1] = this.getWidth() - (3 * i);
				xPoints[2] = this.getWidth();
				yPoints[1] = i;

				g.fillPolygon(xPoints, yPoints, 3);
			}
		}
	}
}
