package tiles;

import gamelogic.Player;

public class SnakeTile extends AbstractTile implements Tile {

	@Override
	public String getName() {
		return "snake_tile";
	}

	@Override
	public String getDescription() {
		return "a tile that contains a snake";
	}

	@Override
	public boolean isTraversable(Player p) {
		return false;
	}

}
