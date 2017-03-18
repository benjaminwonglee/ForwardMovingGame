package gamelogic;

import graphics.Frame;

public class ClockRunner {

	/**
	 * Creates a clock 'tick' for the game progression.
	 * 
	 * @param frame
	 */
	public ClockRunner(Frame frame) {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			frame.getDrawing().repaint();
			if (frame.getLogic().isGameOver()) {
				return;
			}
		}
	}
}
