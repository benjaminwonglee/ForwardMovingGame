package tiles;

import gamelogic.Player;

public class BossTile extends AbstractTile implements Tile {

	@Override
	public String getName() {
		return "boss_tile";
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public boolean isTraversable(Player p) {
		return false;
	}

}
