package tiles;

import gamelogic.Player;

public class TrapTile extends AbstractTile implements Tile {

	@Override
	public String getName() {
		return "trap";
	}

	@Override
	public String getDescription() {
		return "a trap disguised as an item";
	}

	@Override
	public boolean isTraversable(Player p) {
		return false;
	}

}
