package gamelogic;

import graphics.Frame;

public class ClockRunner {
	public ClockRunner(Frame frame) {
		while (true) {
			try {
				Thread.sleep(2000);
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
