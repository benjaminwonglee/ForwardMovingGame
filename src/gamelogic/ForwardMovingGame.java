package gamelogic;

import graphics.Frame;

public class ForwardMovingGame {
	public ForwardMovingGame(){
		Logic l = new Logic();
		new ClockRunner(new Frame(l));
	}

	public static void main(String args[]) {
		new ForwardMovingGame();
	}
}
