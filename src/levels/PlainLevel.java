package levels;

import tiles.Plain;
import tiles.Tile;

public class PlainLevel extends AbstractLevel implements Level {
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
		return new Plain();
	}
}
