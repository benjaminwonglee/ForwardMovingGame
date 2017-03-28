package frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

import gamelogic.Logic;
import graphics.GameFrame;

/**
 * Used to create a window with general buttons such as start game and quit.
 * Sets actions to those buttons. Window is also sized here, along with other
 * regular setup of frame properties such as visibility, location, and closing
 * defaults.
 * 
 * @author Benjamin Wong-Lee
 *
 */
public abstract class AbstractWindow extends JFrame {
	private static final long serialVersionUID = 2938146749853698812L;

	/**
	 * For starting a new game. Everything is created here. Put in
	 * AbstractWindow since windows with buttons instantiate the game.
	 * 
	 * @param startGame
	 *            The button pressed to initialise a new game
	 */
	private void addStartGameAction(JButton startGame) {
		startGame.addActionListener(new ActionListener() {
			/* Everything begins here */
			@Override
			public void actionPerformed(ActionEvent e) {
				Logic l = new Logic();
				GameFrame f = new GameFrame(l);
				l.setFrame(f);
				l.setTimer();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispatchClose();
			}
		});
	}

	public void setFrameProperties() {
		setPreferredSize(new Dimension(800, 800));
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				e.getWindow().dispose();
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public List<JButton> createButtons() {
		List<JButton> buttons = new ArrayList<JButton>();

		JButton newGame = new JButton("New Game");
		JButton quit = new JButton("Quit");

		buttons.add(newGame);
		buttons.add(quit);

		this.addStartGameAction(newGame);

		designButton(newGame, "New Game");
		designButton(quit, "Quit");

		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showOptionDialog(new JDialog(), "Are you sure you want to quit?", "Quit?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (choice == JOptionPane.YES_OPTION) {
					System.exit(1);
				}
			}
		});

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
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_DEICONIFIED));
	}

}
