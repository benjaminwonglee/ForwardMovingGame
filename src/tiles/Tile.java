package tiles;

import gamelogic.Player;

/**
 * A simple interface defining a Tile object in the game. Tiles are the squares
 * on the Board.
 * 
 * @author Benjamin Wong-Lee
 */
public interface Tile {
	/**
	 * Specify the name of the Terrain.
	 * 
	 * @return String Name of the Terrain
	 */
	public String getName();

	/**
	 * Specify the description of the Terrain.
	 * 
	 * @return String Description of the Terrain
	 */
	public String getDescription();

	/**
	 * Specify whether the Terrain can be traversed.
	 * 
	 * @return True if the Terrain can be traversed
	 */
	public boolean isTraversable(Player p);
}
