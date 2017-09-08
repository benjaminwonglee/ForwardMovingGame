package tiles;

import gamelogic.Player;

public class Road extends AbstractTile implements Tile {

	@Override
	public String getName() {
		return "road";
	}

	@Override
	public String getDescription() {
		return "A walkable tarmac road";
	}

	@Override
	public boolean isTraversable(Player p) {
		return true;
	}

}
