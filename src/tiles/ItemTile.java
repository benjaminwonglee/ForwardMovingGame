package tiles;

import gamelogic.Player;
import items.Item;

import java.util.ArrayList;

/**
 * A Tile containing an Item. Should be made to be a reasonably rare occurrence
 * in the game.
 *
 * @author Benjamin Wong-Lee
 */
public class ItemTile extends AbstractTile implements Tile {
	private Item item;
	private static ArrayList<Item> items = new ArrayList<Item>();

	public ItemTile() {
		for (;;) {
			for (Item it : items) {
				double p = Math.random();
				if (p < 0.333) {
					this.item = it;
					break;
				}
			}
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
		return "item_tile";
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

	/**
	 * This method returns the item which is on the tile.
	 *
	 * @return The Item on the Tile.
	 */
	public String getItemName() {
		return item == null ? null : item.getName();
	}

	public static ArrayList<Item> getItems() {
		return items;
	}

	public static void setItems(ArrayList<Item> items) {
		ItemTile.items = items;
	}

}
