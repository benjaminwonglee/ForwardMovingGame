package items;

/**
 * A blank space in the Player inventory.
 * 
 * @author Benjamin Wong-Lee
 */
public class Blank implements Item{

	@Override
	public String getName() {
		return "blank";
	}

	@Override
	public String getDescription() {
		return "No item";
	}

}
