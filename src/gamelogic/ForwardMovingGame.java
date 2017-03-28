package gamelogic;

import frames.GameOverScreen;
import frames.IntroScreen;

/**
 * @author Benjamin Wong-Lee
 */
public class ForwardMovingGame {
	public ForwardMovingGame() {
		new IntroScreen();
		new GameOverScreen();
	}

	public static void main(String args[]) {
		new ForwardMovingGame();
	}
}
