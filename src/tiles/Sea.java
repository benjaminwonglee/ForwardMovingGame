package tiles;

import gamelogic.Player;
import items.Flippers;
import items.Item;

public class Sea extends AbstractTile implements Tile {

	@Override
	public String getName() {
		return "Sea";
	}

	@Override
	public String getDescription() {
		return "A water surface that cannot be traversed.";
	}

	@Override
	public boolean isTraversable(Player p) {
		for (Item i : p.getInventory()) {
			if (i instanceof Flippers) {
				return true;
			}
		}
		return false;
	}
}
