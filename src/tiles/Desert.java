package tiles;

import gamelogic.Player;

public class Desert extends AbstractTile implements Tile {

	@Override
	public String getName() {
		return "Desert";
	}

	@Override
	public String getDescription() {
		return "A dry desert which can be traversed";
	}

	@Override
	public boolean isTraversable(Player p) {
		return true;
	}
}
