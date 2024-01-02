package frames;

import gamelogic.Logic;
import graphics.GameFrame;
import graphics.ImageHandler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to create a window with general buttons such as start game and quit.
 * Sets actions to those buttons. Window is also sized here, along with other
 * regular setup of frame properties such as visibility, location, and closing
 * defaults.
 *
 *
 * @author Benjamin Wong-Lee
 *
 */
public abstract class AbstractWindow extends JFrame {

	private final ImageHandler imageHandler;

	public AbstractWindow() throws HeadlessException {
		super();

		imageHandler = new ImageHandler();
	}

	/**
	 * For starting a new game. Everything is created here. Put in
	 * AbstractWindow since windows with buttons instantiate the game.
	 *
	 * @param startGame  The button pressed to initialise a new game
	 */
	private void addStartGameAction(JButton startGame) {
		startGame.addActionListener((actionEvent) -> {
			/* Everything begins here */
			Logic l = new Logic();
			GameFrame f = new GameFrame(l, imageHandler);
			l.setFrame(f);
			l.runTimer();
			l.runDrawTimer();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				throw new RuntimeException("Couldn't start game. Initialization interrupted");
			}
			dispatchClose();
		});
	}

	public void setFrameProperties() {
		setPreferredSize(new Dimension(800, 800));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public List<JButton> createButtons() {
		List<JButton> buttons = new ArrayList<>();

		JButton newGame = new JButton("New Game");
		JButton quit = new JButton("Quit");

		buttons.add(newGame);
		addStartGameAction(newGame);
		designButton(newGame, "New Game");

		buttons.add(quit);
		quit.addActionListener((actionEvent) -> {
			UIManager.put("OptionPane.messageFont", new Font ("Lucida Sans Regular", Font.PLAIN, 16));
			JDialog dialog = new JDialog();
			int choice = JOptionPane.showOptionDialog(dialog, "Are you sure you want to quit?", "Quit?",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			dialog.dispose();
			if (choice == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		});
		designButton(quit, "Quit");

		return buttons;
	}

	private void designButton(JButton button, String name) {
		button.setText("");
		Border border = new Border() {
			@Override
			public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
				g.setColor(new Color(170, 80, 80));
				g.fillRect(x, y, width, height);
				g.setColor(new Color(150, 60, 60));
				g.fillRect(x + 10, y + 10, width - 20, height - 20);
				g.setColor(new Color(250, 250, 255));
				g.fillRect(x + 20, y + 20, width - 40, height - 40);
				g.setFont(new Font("Lucida Sans", Font.BOLD, 18));
				g.setColor(new Color(100, 0, 0));
				g.drawString(name, width / 2 - (g.getFontMetrics().stringWidth(name) / 2), height / 2 + 8);
			}

			@Override
			public boolean isBorderOpaque() {
				return false;
			}

			@Override
			public Insets getBorderInsets(Component c) {
				return new Insets(0, 0, 0, 0);
			}
		};
		button.setBorder(border);
	}

	public void dispatchClose() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

}
