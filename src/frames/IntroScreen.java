package frames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * @author Benjamin Wong-Lee
 */
public class IntroScreen extends AbstractWindow {

	private static final long serialVersionUID = -1212029084642314569L;

	public IntroScreen() {
		this.setTitle("Forward Moving Game");
		IntroPanel panel = new IntroPanel();
		this.add(panel);
		setFrameProperties();
	}

	private class IntroPanel extends JPanel {

		private static final long serialVersionUID = 7543777692609420096L;

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
