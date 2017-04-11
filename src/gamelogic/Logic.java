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
	private int level = 0;

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

		/**
		 * Converts the integer timeRunning into a string.
		 * 
		 * @return The string that contains the time played.
		 */
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
		private int count = 0;

		public DrawTask(Logic l) {
			this.logic = l;
			this.count = 9;
		}

		@Override
		public void run() {
			if (running && count >= 0) {
				logic.board.createTiles(timeRunning);
				logic.getFrame().getDrawing().repaint();
				count--;
				if (logic.gameOver) {
					timer.cancel();
					timer.purge();
					return;
				}
			} else {
				this.cancel();
				logic.runDrawTimer();
				logic.setLevel(logic.getLevel() + 1);
				return;
			}
		}
	}

	/**
	 * Creates new timer and assigns it to the timer field in Logic.
	 */
	public void setTimer() {
		Timer t = new Timer();
		this.timer = t;
	}

	/**
	 * Logic for the player picking up an item.
	 * 
	 * @return true if the item has been picked up (added to inventory)
	 */
	public boolean pickUpItem() {
		Tile t = checkSquare(board.getHeight(), currentPlayer.getXPos());
		if (t instanceof ItemTile) {
			ItemTile itemTile = (ItemTile) t;
			currentPlayer.addToInventory(itemTile.containedItem());
			return true;
		}
		return false;
	}

	/**
	 * Returns the Tile at the designated row and column of the board.
	 * 
	 * @param row
	 *            specified row on the board
	 * @param col
	 *            specified column on the board
	 * @return Tile object at the designated row and column of the board
	 */
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

	/**
	 * Sets the timer, and a task that controls the clock on the side panel.
	 */
	public void runTimer() {
		// This version of Timer Task, Delay, Period.
		timer.scheduleAtFixedRate(new TTask(this), 1000, 1000);
	}

	/**
	 * Sets the timer task that controls the drawing delay.
	 */
	public void runDrawTimer() {
		int speed = 1000 - (timeRunning * 10);
		if (speed < 200) {
			speed = 200;
		}
		timer.scheduleAtFixedRate(new DrawTask(this), 0, speed);
	}

	public String getTimeString() {
		return timeString;
	}

	/**
	 * Checks if the game is over by checking if the Player has 0 life
	 * remaining. Stops the game and brings up a GameOverScreen.
	 */
	public void checkGameOver() {
		if (currentPlayer.getLife() == 0) {
			setGameOver(true);
			setRunning(false);
			new GameOverScreen();
			frame.dispatchClose();
		}
	}

	/**
	 * Sets the running boolean to say whether the timers should still continue
	 * or stop.
	 * 
	 * @param running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
