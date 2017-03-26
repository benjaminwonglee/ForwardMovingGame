package gamelogic;

import java.util.Timer;
import java.util.TimerTask;

import graphics.GameFrame;

public class ClockRunner {

	private int timeRunning = 0;

	/**
	 * Creates a clock 'tick' for the game progression.
	 * 
	 * @param frame
	 */
	public ClockRunner(Logic l) {
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				timeRunning++;
				l.getFrame().getDrawing().repaint();
				if (l.getFrame().getLogic().isGameOver()) {
					return;
				}
			}
		}, 1000, 1000);
	}

	public int getTimeRunning() {
		return timeRunning;
	}
}
