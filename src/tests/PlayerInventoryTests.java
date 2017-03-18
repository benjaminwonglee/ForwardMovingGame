package tests;

import org.junit.Test;

import gamelogic.Logic;
import gamelogic.Player;
import items.*;

public class PlayerInventoryTests {

	private Logic logic;
	private boolean debug = true;

	/*
	 * Adds two Sword Items to inventory. Discards second sword item. Inventory
	 * count should end up as 1 (Not including blanks).
	 */
	@Test
	public void invalidPlayerInventoryAddDuplicatesTest() {
		reset();
		Player p = logic.getCurrentPlayer();
		p.addToInventory(new Sword());
		p.addToInventory(new Sword());
		int count = 0;
		if (debug) {
			System.out.println("Total inventory: " + p.getInventory().size());
		}
		assert p.getInventory().size() > 0;
		for (int i = 0; i < p.getInventory().size(); i++) {
			if (debug) {
				System.out.println(p.getInventory().get(i).getName());
			}
			if (p.getInventory().get(i) instanceof Blank) {
				/* Don't count blanks */
				continue;
			} else {
				/* Increment if it is an Item and not a blank */
				count++;
			}
		}
		if (debug) {
			System.out.println("count: " + count);
		}
		assert count == 1;
		if (debug) {
			System.out.println("-----------------------------------------------");
		}
	}

	/*
	 * Adds two items to inventory: Sword and Flippers. Inventory count should
	 * end up as 2 (Not including blanks).
	 */
	@Test
	public void validPlayerInventoryAddTest() {
		reset();
		Player p = logic.getCurrentPlayer();
		p.addToInventory(new Sword());
		p.addToInventory(new Flippers());
		int count = 0;
		if (debug) {
			System.out.println("Total inventory: " + p.getInventory().size());
		}
		assert p.getInventory().size() > 0;
		for (int i = 0; i < p.getInventory().size(); i++) {
			if (debug) {
				System.out.println(p.getInventory().get(i).getName());
			}
			if (p.getInventory().get(i) instanceof Blank) {
				/* Don't count blanks */
				continue;
			} else {
				/* Increment if it is an Item and not a blank */
				count++;
			}
		}
		if (debug) {
			System.out.println("count: " + count);
		}
		assert count == 2;
		if (debug) {
			System.out.println("-----------------------------------------------");
		}
	}

	/*
	 * Adds three items to inventory: Sword, Bike, and Flippers. Inventory count
	 * should end up as 3.
	 */
	@Test
	public void validFullInventoryTest() {
		reset();
		Player p = logic.getCurrentPlayer();
		p.addToInventory(new Sword());
		p.addToInventory(new Flippers());
		p.addToInventory(new Bike());
		int count = 0;
		if (debug) {
			System.out.println("Total inventory: " + p.getInventory().size());
		}
		assert p.getInventory().size() > 0;
		for (int i = 0; i < p.getInventory().size(); i++) {
			if (debug) {
				System.out.println(p.getInventory().get(i).getName());
			}
			if (p.getInventory().get(i) instanceof Blank) {
				/* Don't count blanks */
				continue;
			} else {
				/* Increment if it is an Item and not a blank */
				count++;
			}
		}
		if (debug) {
			System.out.println("count: " + count);
		}
		assert count == 3;
		if (debug) {
			System.out.println("-----------------------------------------------");
		}

	}

	/** Creates a new Logic and sets it as the logic field */
	public void reset() {
		/* Logic contains both the Board and the Player */
		this.logic = new Logic();
	}

}
