package gamelogic;

import java.util.ArrayList;
import java.util.List;
import items.*;

/**
 * Represents the player in the game. Has inventory and life as main functions.
 * 
 * @author Benjamin Wong-Lee
 */
public class Player {
	private int xPos;
	private List<Item> inventory;
	private int maxInventorySize;
	private int life = 3;

	/**
	 * Creates a new Player with parameters of the Player's initial x position
	 * (in squares) on the board, and the total number of items allowed in the
	 * player inventory (specified by total number of items in the game
	 * excluding the Blank item, duplicated from Board). Does not check for
	 * creation of Player outside board dimensions since Player object does not
	 * know Board object.
	 * 
	 * @param x
	 *            The initial position of the player in squares on the board (y
	 *            is automatically set).
	 * @param maxInventorySize
	 *            Total number of items allowed in the player inventory.
	 */
	public Player(int x, int maxInventorySize) {
		this.xPos = x;
		this.maxInventorySize = maxInventorySize;
		inventory = new ArrayList<Item>();
		for (int i = 0; i < maxInventorySize; i++) {
			this.inventory.add(new Blank());
		}
	}

	/**
	 * Adds an item to the inventory. Checks if item is null and exits the
	 * method if it is, checks for maximum number allowed in inventory, and if
	 * the Player already contains the item, no duplicates will be added.
	 * Overwrites the first Blank in the Player's inventory.
	 * 
	 * @param item
	 */
	public void addToInventory(Item item) {
		if (item == null) {
			return;
		}
		if (!checkMax()) {
			return;
		}
		if (!checkDuplicates(item)) {
			return;
		}
		/* Add the item overwriting the first blank */
		for (int i = 0; i < maxInventorySize; i++) {
			if (this.inventory.get(i).getName().equals("blank")) {
				this.inventory.remove(i);
				this.inventory.add(i, item);
				return;
			}
		}
	}

	/**
	 * Checks if the inventory there is space in inventory after addition of new
	 * item. Checks for Blank spaces in the Player inventory.
	 * 
	 * @return true if inventory is not full. i.e. There is a Blank in the
	 *         inventory.
	 */
	private boolean checkMax() {
		for (int index = 0; index < maxInventorySize; index++) {
			String name = inventory.get(index).getName();
			if (name.equals("blank")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if Item in parameter already exists in the Player Inventory. This
	 * game doesn't allow duplicates of items in the inventory.
	 * 
	 * @param i
	 *            The item which was picked up by Player.
	 * @return true if the item is already in the inventory. False if it isn't
	 *         in the inventory.
	 */
	private boolean checkDuplicates(Item i) {
		for (Item item : inventory) {
			String s = item.getName();
			if (s.equals(i.getName())) {
				return false;
			}
		}
		return true;
	}

	public int getXPos() {
		return xPos;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public List<Item> getInventory() {
		return inventory;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
}
