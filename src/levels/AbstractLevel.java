package levels;

import tiles.ItemTile;
import tiles.Lava;
import tiles.LifeTile;
import tiles.MonsterTile;
import tiles.Sea;
import tiles.Tile;

/**
 * A class to define a Level: a set of steps that the player goes through in the
 * game. The theme changes according to how a level is defined; each level has
 * different patterns. This class holds general methods for all Levels; Strategy
 * Pattern.
 * 
 * @author Benjamin Wong-Lee
 */
public abstract class AbstractLevel {
	/**
	 * If an Item should be created, returns an item tile. The chance of an item
	 * being created is a random chance.
	 * 
	 * @param item
	 *            A flag to say an item should not be created.
	 * @return True if an item should be created
	 */
	public Tile itemGen(boolean item) {
		if (!item) {
			int rand = (int) Math.floor((Math.random() * 10));
			if (rand == 1) {
				return new ItemTile();
			}
		}
		return null;
	}

	/**
	 * Checks whether a life tile should be created or not.
	 * 
	 * @param item
	 *            A flag to say a life tile should not be created.
	 * @return True if an life tile should be created
	 */
	public Tile lifeGen(boolean item) {
		if (!item) {
			int rand = (int) Math.floor((Math.random() * 20));
			if (rand == 1) {
				return new LifeTile();
			}
		}
		return null;
	}

	/**
	 * Checks whether a monster should be created or not.
	 * 
	 * @param monster
	 *            A flag to say a monster should not be created.
	 * @return True if an monster should be created
	 */
	public Tile monsterGen(boolean monster) {
		if (!monster) {
			int rand = (int) Math.floor((Math.random() * 3));
			if (rand == 1) {
				return new MonsterTile();
			}
		}
		return null;
	}

	/**
	 * Checks whether a lava tile should be created or not.
	 * 
	 * @param lava
	 *            A flag to say a lava tile should not be created.
	 * @return True if a lava tile should be created
	 */
	public Tile lavaGen(boolean lava) {
		if (!lava) {
			int rand = (int) Math.floor((Math.random() * 4));
			if (rand == 1) {
				return new Lava();
			}
		}
		return null;
	}

	/**
	 * Checks whether a sea tile should be created or not.
	 * 
	 * @param sea
	 *            A flag to say a sea tile should not be created.
	 * @return True if a sea tile should be created
	 */
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
