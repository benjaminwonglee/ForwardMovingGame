package graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamelogic.Board;

/**
 * The KeyListener for the game. Mainly used for movement of Player.
 * 
 * @author Benjamin Wong-Lee
 */
public class WalkKeyListener implements KeyListener {
	Board board = null;
	Drawing drawing = null;

	public WalkKeyListener(Board b, Drawing d) {
		this.board = b;
		this.drawing = d;
	}

	@Override
	public void keyPressed(KeyEvent k) {
		System.out.println("Pressed");
		switch (k.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if (board.movePlayerInDirection("left")) {
				drawing.repaint();
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (board.movePlayerInDirection("right")) {
				drawing.repaint();
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent k) {
		System.out.println("Typed");
		switch (k.getKeyChar()) {
		case 'a':
		case 'A':
		case '4':
			if (board.movePlayerInDirection("left")) {
				drawing.repaint();
			}
			break;
		case 'd':
		case 'D':
		case '6':
			if (board.movePlayerInDirection("right")) {
				drawing.repaint();
			}
			break;
		}
	}
}
