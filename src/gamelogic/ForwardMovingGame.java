package gamelogic;

import frames.GameOverScreen;
import frames.IntroScreen;
import graphics.Frame;

public class ForwardMovingGame {
	public ForwardMovingGame() {
		// TODO: Test intro screen
		// TODO: TEST gameover screen
		//new IntroScreen();
		 new GameOverScreen();

		// Logic l = new Logic();
		// Frame f = new Frame(l);
		// ClockRunner clock = new ClockRunner(f);
		// f.setClock(clock);
	}

	public static void main(String args[]) {
		new ForwardMovingGame();
	}
}
