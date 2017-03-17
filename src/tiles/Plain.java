package tiles;

import gamelogic.Player;

public class Plain extends AbstractTile implements Tile {

	@Override
	public String getName() {
		return "Plain";
	}

	@Override
	public String getDescription() {
		return "A grassy plain which is traversable";
	}

	@Override
	public boolean isTraversable(Player p) {
		return true;
	}
}
