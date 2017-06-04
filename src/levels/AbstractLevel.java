package levels;

import tiles.MonsterTile;
import tiles.Tile;

public abstract class AbstractLevel {
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
			int rand = (int) Math.floor((Math.random() * 3));
			if (rand == 1) {
				return new MonsterTile();
			}
		}
		return null;
	}
}
