package gamelogic;

import graphics.Frame;

public class ForwardMovingGame {
	public ForwardMovingGame(){
		Logic l = new Logic();
		Frame f = new Frame(l);
		ClockRunner clock = new ClockRunner(f);
		f.setClock(clock);
	}

	public static void main(String args[]) {
		new ForwardMovingGame();
	}
}
