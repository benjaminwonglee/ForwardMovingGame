package gamelogic;

import graphics.GameFrame;

public class ClockRunner {

	private int timeRunning = 0;

	/**
	 * Creates a clock 'tick' for the game progression.
	 * 
	 * @param frame
	 */
	public ClockRunner(GameFrame frame) {
		while (true) {
			try {
				Thread.sleep(980);
				// Allow 20ms for repaint
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timeRunning++;
			frame.getDrawing().repaint();
			if (frame.getLogic().isGameOver()) {
				return;
			}
		}
	}
	
	public int getTimeRunning(){
		return timeRunning;
	}
}
