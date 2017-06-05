package levels;

import tiles.Desert;
import tiles.Plain;
import tiles.Tile;

public class PlainDesertSimpleLevel extends AbstractLevel implements Level {

	@Override
	public Tile board(int row, int timeRunning, boolean monster, boolean sea, boolean lava) {
		Tile t = monsterGen(monster);
		if (t != null) {
			return t;
		}
		if (timeRunning % 2 == 0) {
			return new Desert();
		} else {
			return new Plain();
		}
	}

}
