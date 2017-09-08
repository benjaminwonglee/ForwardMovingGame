package graphics;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GameWindowListener implements WindowListener {
	private GameFrame frame;

	public GameWindowListener(GameFrame frame) {
		this.frame = frame;
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		if (!frame.getLogic().isGameOver()) {
			System.exit(1);
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}
}
