package levels;

import tiles.Desert;
import tiles.Plain;
import tiles.Tile;

public class PlainAndDesertAlternatingLevel extends AbstractLevel implements Level {

	@Override
	public Tile board(int row, int timeRunning, boolean monster, boolean lava) {
		Tile t = monsterGen(monster);
		if (t != null) {
			return t;
		}
		if ((row + timeRunning) % 2 == 1) {
			return new Desert();
		} else {
			return new Plain();
		}
	}

}
