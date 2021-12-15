package pirate;

/**
 * The Loot class represents a loot with its id (automatically assigned in the constructor) and its label.
 * @author Xiumin Lin
 * @author Céline Li
 * @version 1.0
 */
public class Loot {
	private static int num = 1;
	private final int id;
	private String label;

	// constructor
	public Loot(String label) {
		this.id = num++;
		this.label = label;
	}

	/**
	 * @return the loot's id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the loot's label.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return a string in the format --> o:lootName;
	 */
	@Override
	public String toString() {
		return "o" + id + ":" + label;
	}
}
