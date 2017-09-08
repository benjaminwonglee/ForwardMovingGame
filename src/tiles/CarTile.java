package tiles;

import gamelogic.Player;

public class CarTile extends AbstractTile implements Tile {

	@Override
	public String getName() {
		return "car";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTraversable(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

}
