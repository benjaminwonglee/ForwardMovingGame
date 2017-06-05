package tiles;

import java.util.ArrayList;

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
	private static ArrayList<Item> items = new ArrayList<Item>();

	/**
	 * Requires maxInventoryNumber to select an item at random.
	 * 
	 * @param maxInvNum
	 */
	public ItemTile(int maxInvNum) {
		for (;;) {
			System.out.println("This is happening");
			for (Item it : items) {
				double p = Math.random();
				if (p < 0.333) {
					this.item = it;
					break;
				}
			}
			System.out.println(this.item);
			if (this.item != null) {
				break;
			}
		}
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

	public static ArrayList<Item> getItems() {
		return items;
	}

	public static void setItems(ArrayList<Item> items) {
		ItemTile.items = items;
	}

}
