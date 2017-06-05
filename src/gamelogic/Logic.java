package gamelogic;

import java.util.Timer;
import java.util.TimerTask;

import frames.GameOverScreen;
import graphics.GameFrame;
import tiles.ItemTile;
import tiles.Tile;
import timertasks.DrawTask;
import timertasks.TickTask;

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

	public Logic() {
		this.board = new Board(this);
		this.currentPlayer = new Player(board.getWidth() / 2, board.getMaxInventorySize());
		setTimer();
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
			currentPlayer.addToInventory(itemTile.getItem());
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

	/**
	 * Sets the timer, and a task that controls the clock on the side panel.
	 */
	public void runTimer() {
		// This version of Timer Task, Delay, Period.
		timer.scheduleAtFixedRate(new TickTask(this), 1000, 1000);
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

	public boolean isRunning() {
		return running;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public void setTimeRunning(int timeRunning) {
		this.timeRunning = timeRunning;
	}

	public int getTimeRunning() {
		return timeRunning;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

	public String getTimeString() {
		return timeString;
	}
}
