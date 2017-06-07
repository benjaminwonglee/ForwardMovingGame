package tiles;

import gamelogic.Player;
import items.Item;
import items.Sword;

/**
 * A Tile that can only be traversed if the Player has the Sword object.
 *
 * @author Benjamin Wong-Lee
 */
public class MonsterTile extends AbstractTile implements Tile {

	@Override
	public String getName() {
		return "monster_tile";
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