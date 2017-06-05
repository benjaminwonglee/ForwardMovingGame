package tiles;

import gamelogic.Player;
import items.Item;

/**
 * A Tile containing an Item. Should be made to be a reasonably rare occurrence
 * in the game.
 * 
 * @author Benjamin Wong-Lee
 */
public class ItemTile extends AbstractTile implements Tile {
	private Item item;

	/**
	 * Requires maxInventoryNumber to select an item at random.
	 * 
	 * @param maxInvNum
	 */
	public ItemTile(int maxInvNum) {

	}

	public ItemTile(Item i) {
		this.item = i;
	}

	@Override
	public String getName() {
		return "Item Tile";
	}

	@Override
	public String getDescription() {
		return "A tile with an item on it";
	}

	@Override
	public boolean isTraversable(Player p) {
		return true;
	}

	/**
	 * This method returns the item which is on the tile.
	 * 
	 * @return The Item on the Tile.
	 */
	public Item getItem() {
		return item;
	}

}
