package levels;

import tiles.Desert;
import tiles.Plain;
import tiles.Tile;

public class PlainDesertSimpleLevel extends AbstractLevel implements Level {

	@Override
	public Tile board(int row, int timeRunning, boolean item, boolean monster, boolean sea, boolean lava) {
		Tile t = lifeGen(item);
		if (t != null) {
			return t;
		}
		t = itemGen(item);
		if (t != null) {
			return t;
		}
		t = monsterGen(monster);
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
