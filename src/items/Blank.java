package items;

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
