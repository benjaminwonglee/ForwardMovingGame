package timertasks;

import java.util.TimerTask;

import gamelogic.Logic;

/**
 * The redrawing and creation of a new set of tiles that the timer does every
 * clock tick. Clock tick delay set by Timer.
 * 
 * @author Benjamin Wong-Lee
 * 
 */
public class DrawTask extends TimerTask {
	private Logic logic;
	private int count = 0;

	public DrawTask(Logic l) {
		this.logic = l;
		this.count = 9;
	}

	@Override
	public void run() {
		if (logic.isRunning() && count >= 0) {
			logic.getBoard().createTiles(logic.getTimeRunning());
			logic.getFrame().getDrawing().repaint();
			count--;
			if (logic.isGameOver()) {
				logic.getTimer().cancel();
				logic.getTimer().purge();
				return;
			}
		} else {
			this.cancel();
			logic.runDrawTimer();
			// Level is set here!
			logic.setLevel(logic.getLevel() + 1);
			return;
		}
	}
}