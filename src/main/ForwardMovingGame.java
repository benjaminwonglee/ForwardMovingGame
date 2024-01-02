package main;

import frames.IntroScreen;

import javax.swing.*;

/**
 * @author Benjamin Wong-Lee
 */
public class ForwardMovingGame {
	public ForwardMovingGame() {
		new IntroScreen();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(ForwardMovingGame::new);
	}
}
