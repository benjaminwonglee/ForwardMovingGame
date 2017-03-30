package tiles;

import gamelogic.Player;
import items.Bike;
import items.Item;

/**
 * A Mountain Tile which can be traversed only with the Bike item.
 * 
 * @author Benjamin Wong-Lee
 */
public class Mountain extends AbstractTile implements Tile {

	@Override
	public String getName() {
		return "Mountain";
	}

	@Override
	public String getDescription() {
		return "A tall mountain which can be traversed only with a bike";
	}

	@Override
	public boolean isTraversable(Player p) {
		for (Item i : p.getInventory()) {
			if (i instanceof Bike) {
				return true;
			}
		}
		return false;
	}
}
