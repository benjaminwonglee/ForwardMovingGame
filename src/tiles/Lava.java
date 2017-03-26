package tiles;

import gamelogic.Player;

/**
 * A Lava Tile that cannot be traversed.
 * 
 * @author Benjamin Wong-Lee
 */
public class Lava extends AbstractTile implements Tile {

		@Override
		public String getName() {
			return "Lava";
		}

		@Override
		public String getDescription() {
			return "Hot Lava... stay away";
		}

		@Override
		public boolean isTraversable(Player p) {
			return false;
		}

}
