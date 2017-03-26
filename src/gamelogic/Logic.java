package gamelogic;

import java.util.Timer;
import java.util.TimerTask;

import frames.GameOverScreen;
import graphics.GameFrame;
import tiles.ItemTile;
import tiles.Tile;

public class Logic {
	private Player currentPlayer;
	private Board board;
	private GameFrame frame;
	private boolean gameOver = false;

	// Timer variables
	private Timer timer;
	private int timeRunning = 0;
	
	// TODO: Create a player life function

	/**
	 * Constructs the Logic of the game. The Board, Player, and ClockRunner
	 * (Timer) are also created here since the Board relies on Logic to
	 * function. Other game logic such as ClockRunner logic is also here.
	 * 
	 * @author Benjamin Wong-Lee
	 */
	public Logic() {
		this.board = new Board(this);
		this.currentPlayer = new Player(board.getWidth() / 2, board.getMaxInventorySize());
		this.timer = setTimer();
		if (currentPlayer.getLife() == 0) {
			setGameOver(true);
			new GameOverScreen();
		}
	}

	
	/**
	 * 
	 * 
	 * @author Benjamin Wong-Lee
	 * 
	 */
	public class TTask extends TimerTask {
		Logic logic;

		public TTask(Logic l) {
			this.logic = l;
		}

		@Override
		public void run() {
			timeRunning++;
			logic.getFrame().getDrawing().repaint();
			if (logic.isGameOver()) {
				return;
			}
		}
	}

	public Timer setTimer() {
		Timer t = new Timer();
		// This version of Timer Task, Delay, Period. 
		t.scheduleAtFixedRate(new TTask(this), 1000, 1000);
		return t;
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
}
