package gamelogic;

import frames.GameOverScreen;
import frames.IntroScreen;
import graphics.GameFrame;

public class ForwardMovingGame {
	public ForwardMovingGame() {
		// TODO: Test intro screen
		// TODO: TEST gameover screen
		new IntroScreen();
		//new GameOverScreen();
	}

	public static void main(String args[]) {
		new ForwardMovingGame();
	}
}
