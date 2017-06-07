package tiles;

import gamelogic.Player;

/**
 * A grassy plain. Can be traversed. The most simple tile in the game.
 *
 * @author Benjamin Wong-Lee
 */
public class Plain extends AbstractTile implements Tile {

	@Override
	public String getName() {
		return "plain";
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
