package gamelogic;

import frames.GameOverScreen;
import tiles.ItemTile;
import tiles.Tile;

public class Logic {
	private Player currentPlayer;
	private Board board;
	private boolean gameOver = false;

	/**
	 * Constructs the Logic of the game. The Board and Player are also created
	 * here since the Board relies on Logic to function.
	 */
	public Logic() {
		this.board = new Board(this);
		this.currentPlayer = new Player(board.getWidth() / 2, board.getMaxInventorySize());
		if (currentPlayer.getLife() == 0) {
			setGameOver(true);
			new GameOverScreen();
		}
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

}
