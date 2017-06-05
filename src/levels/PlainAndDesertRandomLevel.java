package levels;

import tiles.Desert;
import tiles.Plain;
import tiles.Tile;

public class PlainAndDesertRandomLevel extends AbstractLevel implements Level {

	@Override
	public Tile board(int row, int timeRunning, boolean monster, boolean sea, boolean lava) {
		Tile t = itemGen();
		if (t != null) {
			return t;
		}
		t = monsterGen(monster);
		if (t != null) {
			return t;
		}
		int rand = (int) Math.floor(Math.random() * 2);
		if (rand == 1) {
			return new Desert();
		} else {
			return new Plain();
		}
	}

}
