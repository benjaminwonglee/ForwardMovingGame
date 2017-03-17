package items;

public class Sword implements Item {

	@Override
	public String getName() {
		return "sword";
	}

	@Override
	public String getDescription() {
		return "A sharp sword that can vanquish monsters.";
	}

}
