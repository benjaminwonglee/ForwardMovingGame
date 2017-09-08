package tiles;

import gamelogic.Player;

public class LifeTile extends AbstractTile implements Tile {

	@Override
	public String getName() {
		return "life_tile";
	}

	@Override
	public String getDescription() {
		return "A tile that grants an extra life to the player";
	}

	@Override
	public boolean isTraversable(Player p) {
		return true;
	}
}