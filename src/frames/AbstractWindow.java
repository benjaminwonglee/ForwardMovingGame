package frames;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gamelogic.ClockRunner;
import gamelogic.Logic;
import graphics.GameFrame;

public abstract class AbstractWindow extends JFrame {
	public void setFrameProperties() {
		setPreferredSize(new Dimension(800, 800));
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	public Set<JButton> createButtons() {
		Set<JButton> buttons = new HashSet<JButton>();

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

}
