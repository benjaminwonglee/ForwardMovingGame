package items;

public class Bike implements Item {

	@Override
	public String getName() {
		return "bike";
	}

	@Override
	public String getDescription() {
		return "Allows the travelling of mountains";
	}

}
