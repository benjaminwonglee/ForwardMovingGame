package gamelogic;

import java.util.Timer;
import java.util.TimerTask;

import frames.GameOverScreen;
import graphics.GameFrame;
import tiles.ItemTile;
import tiles.Tile;

/**
 * The Logic class constructs the logic of the game. The Board, Player, and
 * Timer are also created here since the Board relies on Logic to function.
 * Other game logic such as Player and Timer logic is also located here.
 *
 * @author Benjamin Wong-Lee
 */
public class Logic {
	private Player currentPlayer;
	private Board board;
	private GameFrame frame;
	private boolean gameOver = false;
	private boolean running = true;

	// Timer variables
	private Timer timer;
	private int timeRunning = -1;
	private String timeString;

	// TODO: Create a player life function

	public Logic() {
		this.board = new Board(this);
		this.currentPlayer = new Player(board.getWidth() / 2, board.getMaxInventorySize());
		setTimer();
	}

	/**
	 * The current actions that the timer is taking every clock tick. Clock tick
	 * delay set by Timer.
	 * 
	 * @author Benjamin Wong-Lee
	 * 
	 */
	public class TTask extends TimerTask {
		private int minutes = -1;
		private Logic logic;

		public TTask(Logic logic) {
			this.logic = logic;
		}

		@Override
		public void run() {
			if (running) {
				timeRunning++;
				timeString = convertTimeToString();
				if (logic.gameOver) {
					timer.cancel();
					timer.purge();
					return;
				}
			} else {
				timer.cancel();
				timer.purge();
				return;
			}
		}

		private String convertTimeToString() {
			if (timeRunning % 60 == 0) {
				minutes++;
			}
			if (timeRunning % 60 < 10) {
				return minutes + ":0" + (timeRunning % 60);
			}
			return minutes + ":" + (timeRunning % 60);
		}
	}

	/**
	 * The redrawing and creation of a new set of tiles that the timer does
	 * every clock tick. Clock tick delay set by Timer.
	 * 
	 * @author Benjamin Wong-Lee
	 * 
	 */
	public class DrawTask extends TimerTask {
		private Logic logic;
		private boolean end = false;

		public DrawTask(Logic l) {
			this.logic = l;
		}

		@Override
		public void run() {
			if (running && !end) {
				logic.board.createTiles(timeRunning);
				logic.getFrame().getDrawing().repaint();
				if (logic.gameOver) {
					timer.cancel();
					timer.purge();
					return;
				}
			} else {
				timer.cancel();
				timer.purge();
				return;
			}
		}

		public void setEnd(boolean end) {
			this.end = end;
		}
	}

	public void setTimer() {
		Timer t = new Timer();
		this.timer = t;
	}

	public boolean pickUpItem() {
		Tile t = checkSquare(board.getHeight(), currentPlayer.getXPos());
		if (t instanceof ItemTile) {
			ItemTile itemTile = (ItemTile) t;
			currentPlayer.addToInventory(itemTile.containedItem());
			return true;
		}
		return false;
	}

	public Tile checkSquare(int row, int col) {
		return board.returnSquare(row, col);
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getPlayerXPos() {
		return currentPlayer.getXPos();
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public GameFrame getFrame() {
		return frame;
	}

	public void setFrame(GameFrame frame) {
		this.frame = frame;
	}

	public Timer getTimer() {
		return timer;
	}

	public int getTimeRunning() {
		return timeRunning;
	}

	public void runTimer() {
		// This version of Timer Task, Delay, Period.
		timer.scheduleAtFixedRate(new TTask(this), 1000, 1000);
	}

	public void runDrawTimer() {
		DrawTask task = new DrawTask(this);
		if (board.getLevel() == 0) {
			timer.scheduleAtFixedRate(task, 1000, 600);
		} else if (board.getLevel() == 2) {
			task.setEnd(true);
			task = new DrawTask(this);
			timer.scheduleAtFixedRate(task, 1000, 400);
		} else if (board.getLevel() == 4) {
			task.setEnd(true);
			task = new DrawTask(this);
			timer.scheduleAtFixedRate(task, 1000, 200);
		}
	}

	public String getTimeString() {
		return timeString;
	}

	public void checkGameOver() {
		if (currentPlayer.getLife() == 0) {
			setGameOver(true);
			setRunning(false);
			new GameOverScreen();
			frame.dispatchClose();
		}
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
