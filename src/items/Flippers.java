package items;

/**
 * Allows Player to traverse Sea tiles.
 * 
 * @author Benjamin Wong-Lee
 */
public class Flippers implements Item {

	@Override
	public String getName() {
		return "flippers";
	}

	@Override
	public String getDescription() {
		return "Allows the travelling of water";
	}

}
