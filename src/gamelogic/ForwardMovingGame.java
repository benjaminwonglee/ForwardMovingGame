package gamelogic;

import frames.GameOverScreen;
import graphics.Frame;

public class ForwardMovingGame {
	public ForwardMovingGame(){
		//TODO: TEST gameover screen
		//TODO: Test intro screen
		new GameOverScreen();

//		Logic l = new Logic();
//		Frame f = new Frame(l);
//		ClockRunner clock = new ClockRunner(f);
//		f.setClock(clock);
	}

	public static void main(String args[]) {
		new ForwardMovingGame();
	}
}
