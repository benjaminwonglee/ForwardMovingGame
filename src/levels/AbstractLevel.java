package levels;

import java.util.ArrayList;

import items.Item;
import tiles.ItemTile;
import tiles.Lava;
import tiles.MonsterTile;
import tiles.Sea;
import tiles.Tile;

public abstract class AbstractLevel {
	private static ArrayList<Item> items = new ArrayList<Item>();

	public Tile itemGen(boolean item) {
		if (!item) {
			int rand = (int) Math.floor((Math.random() * 9));
			if (rand == 1) {
				Item i = null;
				for(Item item : items){
					
				}
				return new ItemTile(i);
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
