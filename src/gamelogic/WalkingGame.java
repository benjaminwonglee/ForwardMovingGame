package gamelogic;

import graphics.Frame;

public class WalkingGame {
	public WalkingGame(){
		Logic l = new Logic();
		new Frame(l);
	}

	public static void main(String args[]) {
		new WalkingGame();
	}
}
