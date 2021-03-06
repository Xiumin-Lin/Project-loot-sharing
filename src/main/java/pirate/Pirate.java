package pirate;

import java.util.ArrayList;
import java.util.List;

/**
 * The Pirate class represents a pirate with his id, his name,
 * his list of favourite treasures (loot) and his treasure if he has received one.
 *
 * @author Xiumin Lin
 * @author Céline Li
 * @version 1.0
 */
public class Pirate {

	// attributes
	/**
	 * Counter that gives a unique id to a pirate when it is created.
	 */
	private static int cptId = 0;
	private final int id;
	private String name;
	private Loot loot;
	/**
	 * List of her favourite loot in order of the most to the least desired
	 */
	private ArrayList<Loot> favList;

	// constructor
	Pirate(String name) {
		this.id = cptId++;
		this.name = name;
		this.loot = null; // null = no loot
		this.favList = new ArrayList<>();
	}

	/**
	 * Return the pirate's ID.
	 *
	 * @return pirate's ID.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Return the pirate's name.
	 *
	 * @return pirate's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the pirate's loot.
	 *
	 * @param loot the loot to be given to the pirate.
	 */
	public void setLoot(Loot loot) {
		this.loot = loot;
	}

	/**
	 * Return the loot that the pirate has.
	 *
	 * @return the pirate's loot, if the pirate doesn't have one, return -1.
	 */
	public Loot getLoot() {
		return loot;
	}

	/**
	 * Add a loot to the pirate's favourite loot list.
	 *
	 * @param loot loot to be added.
	 * @throws Exception if the loot is already present in his favourite loot list.
	 */
	public void addFavLoot(Loot loot) throws Exception {
		if(favList.contains(loot)) {
			this.favList.clear();
			throw new Exception("[Error] Can't add in the favourite loot list : the loot " + loot + " is already added ! " +
					"The favList is cleaned, please re-enter the pirate's favList.");
		} else
			this.favList.add(loot);
	}

	/**
	 * Checks if the pirate's favourite loot list is complete.
	 *
	 * @param limit the max number of loots to share.
	 * @return True if it's complete else False.
	 */
	public boolean favListIsComplete(int limit) {
		return favList.size() == limit;
	}

	/**
	 * Return a clone of the favList.
	 *
	 * @return a clone of the favList.
	 */
	public List<Loot> getFavList() {
		return new ArrayList<>(favList);
	}

	/**
	 * Returns the sub-list of loot that the pirate would have preferred to have.
	 * If the pirate has not yet received any loot, the entire favourite list is returned.
	 *
	 * @return the sub-list of loot that the pirate would have preferred to have.
	 */
	public List<Loot> getMoreFavList() {
		int lootIdx = (this.loot != null) ? favList.indexOf(this.loot) : favList.size();
		return favList.subList(0, lootIdx);
	}

	@Override
	public String toString() {
		String lootInfo = (loot != null) ? "" + loot.getId() : "null";
		return "Pirate{" + "id=" + id + ", name=" + name + ", loot=" + lootInfo + ", favList=" + favList + '}';
	}
}
