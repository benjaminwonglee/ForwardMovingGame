package items;

/**
 * A Sword allows the Player to traverse MonsterTile Tiles.
 * 
 * @author Benjamin Wong-Lee
 */
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
