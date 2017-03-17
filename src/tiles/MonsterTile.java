package tiles;

import gamelogic.Player;
import items.Item;
import items.Sword;

public class MonsterTile extends AbstractTile implements Tile {

	@Override
	public String getName() {
		return "MonsterTile";
	}

	@Override
	public String getDescription() {
		return "A tile with a monster on it";
	}

	@Override
	public boolean isTraversable(Player p) {
		for (Item i : p.getInventory()) {
			if (i instanceof Sword) {
				return true;
			}
		}
		return false;
	}
}