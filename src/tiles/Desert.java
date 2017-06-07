package tiles;

import gamelogic.Player;

/**
 * A simple desert Tile.
 *
 * @author Benjamin Wong-Lee
 */
public class Desert extends AbstractTile implements Tile {

	@Override
	public String getName() {
		return "desert";
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