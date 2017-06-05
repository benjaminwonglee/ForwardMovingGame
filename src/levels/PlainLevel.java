package levels;

import tiles.Plain;
import tiles.Tile;

public class PlainLevel extends AbstractLevel implements Level {
	@Override
	public Tile board(int row, int timeRunning, boolean monster, boolean sea, boolean lava) {
		Tile t = monsterGen(monster);
		if (t != null) {
			return t;
		}
		return new Plain();
	}
}
