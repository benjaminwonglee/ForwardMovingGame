package levels;

import tiles.ItemTile;
import tiles.Lava;
import tiles.LifeTile;
import tiles.MonsterTile;
import tiles.Sea;
import tiles.Tile;

public abstract class AbstractLevel {
	private int maxInvNum = 3;

	public Tile itemGen(boolean item) {
		if (!item) {
			int rand = (int) Math.floor((Math.random() * 40));
			if (rand == 1) {
				return new ItemTile(maxInvNum);
			}
		}
		return null;
	}

	public Tile lifeGen(boolean item) {
		if (!item) {
			int rand = (int) Math.floor((Math.random() * 50));
			if (rand == 1) {
				return new LifeTile();
			}
		}
		return null;
	}

	public Tile monsterGen(boolean monster) {
		if (!monster) {
			int rand = (int) Math.floor((Math.random() * 3));
			if (rand == 1) {
				return new MonsterTile();
			}
		}
		return null;
	}

	public Tile lavaGen(boolean lava) {
		if (!lava) {
			int rand = (int) Math.floor((Math.random() * 4));
			if (rand == 1) {
				return new Lava();
			}
		}
		return null;
	}

	public Tile seaGen(boolean sea) {
		if (!sea) {
			int rand = (int) Math.floor((Math.random() * 4));
			if (rand == 1) {
				return new Sea();
			}
		}
		return null;
	}
}
