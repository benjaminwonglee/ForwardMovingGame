package timertasks;

import java.util.TimerTask;

import gamelogic.Logic;

/**
 * The current actions that the timer is taking every clock tick. Clock tick
 * delay set by Timer.
 * 
 * @author Benjamin Wong-Lee
 * 
 */
public class TickTask extends TimerTask {
	private int minutes = -1;
	private Logic logic;

	public TickTask(Logic logic) {
		this.logic = logic;
	}

	@Override
	public void run() {
		if (logic.isRunning()) {
			// Update timer by 1 second
			logic.setTimeRunning(logic.getTimeRunning() + 1);
			// Display the new time
			logic.setTimeString(convertTimeToString());
			if (logic.isGameOver()) {
				// End state, Game Over.
				logic.getTimer().cancel();
				logic.getTimer().purge();
				return;
			}
		} else {
			// Ended state, After Game Over.
			logic.getTimer().cancel();
			logic.getTimer().purge();
			return;
		}
	}

	/**
	 * Converts the integer timeRunning into a string.
	 * 
	 * @return The string that contains the time played.
	 */
	private String convertTimeToString() {
		int tRun = logic.getTimeRunning();
		if (tRun % 60 == 0) {
			minutes++;
		}
		if (tRun % 60 < 10) {
			return minutes + ":0" + (tRun % 60);
		}
		return minutes + ":" + (tRun % 60);
	}
}
