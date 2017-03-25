package frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gamelogic.ClockRunner;
import gamelogic.Logic;
import graphics.GameFrame;

public abstract class AbstractWindow extends JFrame {
	private static final long serialVersionUID = 2938146749853698812L;

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

		JButton startGame = new JButton("New Game");
		JButton quit = new JButton("Quit");

		buttons.add(startGame);
		buttons.add(quit);

		startGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Logic l = new Logic();
				GameFrame f = new GameFrame(l);
				ClockRunner clock = new ClockRunner(f);
				f.setClock(clock);
				
				//dispatchClose();
			}
		});

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

		for (JButton j : buttons) {
			j.setPreferredSize(new Dimension(150, 50));
		}
		return buttons;
	}

	public void dispatchClose() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

}
