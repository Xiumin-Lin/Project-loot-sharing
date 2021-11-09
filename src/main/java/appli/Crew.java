package appli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Crew class represents a pirate crew and their agreements with each other.
 *
 * @author Xiumin Lin
 * @author Céline Li
 * @version 1.0
 */
public class Crew {

	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int A_IN_ASCII = 65;
	/**
	 * An adjacency matrix that represents the agreement between each member of the crew.
	 * If 2 pirates, i and j, do not like each other, relationship.get(i).get(j) = ONE.
	 * Else it's ZERO.
	 */
	private ArrayList<ArrayList<Integer>> relationship;
	private int nbPirate;
	private HashMap<String, Pirate> equipage;
	private ArrayList<Loot> lootList;

	public Crew() {
		this.nbPirate = 0;
		this.relationship = new ArrayList<>();
		this.equipage = new HashMap<>();
		this.lootList = new ArrayList<>();
	}

	/**
	 * Constructor who initializes a crew of n members and where nobody hates anybody.
	 *
	 * @param n the number of pirates in the crew.
	 */
	public Crew(int n) {
		this();
		this.nbPirate = n;
		// creation of all the pirates of the equipage
		System.out.println("Initialization of the crew : "); // DEBUG
		for(int i = 0; i < nbPirate; i++) {
			String lettre = "" + (char) (A_IN_ASCII + i);
			equipage.put(lettre, new Pirate(lettre));
			System.out.println("\t" + equipage.get(lettre)); // DEBUG
		}
		initCrewRelation(n);
	}

	/**
	 * Initialise the relationship table where nobody hates anybody.
	 *
	 * @param n the number of pirates in the crew.
	 */
	private void initCrewRelation(int n) {
		for(int i = 0; i < n; i++) {
			relationship.add(new ArrayList<>());
			for(int j = 0; j < n; j++) {
				relationship.get(i).add(ZERO);
			}
		}
//		showRelation(); // DEBUG
	}

	/**
	 * Display the Adjacency Matrix of Relationship. (Used in initRelation & addRelation)
	 */
	public void showRelation() { // DEBUG
		System.out.println("Adjacency Matrix of Relationship : ");
		for(int i = 0; i < relationship.size(); i++) {
			for(int j = 0; j < relationship.size(); j++) {
				System.out.print(relationship.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}

	/**
	 * Return the number of pirates in the crew.
	 *
	 * @return the number of pirates in the crew.
	 */
	public int getNbPirate() {
		return nbPirate;
	}

	/**
	 * Searches for and returns the pirate with the specified name. Returns null if not found.
	 *
	 * @param name the pirate's name.
	 * @return the pirate with the specified name or null if not found.
	 */
	public Pirate getPirate(String name) {
		return equipage.getOrDefault(name, null);
	}

	/**
	 * Adds a relationship between pirate A and pirate B indicating that they do not like each other.
	 *
	 * @param a the pirate whose relationship is involved.
	 * @param b the other pirate whose relationship is involved.
	 * @throws Exception if pirate A or B doesn't exist.
	 */
	public void addRelation(String a, String b) throws Exception {
		if(equipage.containsKey(a) && equipage.containsKey(b)) {
			int pirateID = equipage.get(a).getId();
			int pirateID2 = equipage.get(b).getId();
			relationship.get(pirateID).set(pirateID2, ONE);
			relationship.get(pirateID2).set(pirateID, ONE);
			System.out.println("Success in adding the relationship between " + a + " and " + b + ".");
//			showRelation(); // DEBUG
		} else {
			throw new Exception("[Error] Pirate " + a + " or " + b + " doesn't exist !");
		}
	}

	/**
	 * Display the information of all crew members.
	 */
	public void showCrew() {
		System.out.println("Crew : ");
		equipage.forEach((s, pirate) -> System.out.println("\t" + pirate));
	}

	/**
	 * Display the list of pirates and the loot they have.
	 */
	public void showCrewLoot() {
		System.out.println("\nThe loot of each pirate : ");
		equipage.forEach((s, pirate) -> System.out.println(s + ":o" + pirate.getLoot()));
	}

	/**
	 * Check if all pirate favourite loot list is complete.
	 *
	 * @throws Exception if there is at least one list that is incomplete.
	 * @return
	 */
	public boolean allPirateFavListIsComplete() throws Exception {
		for(Pirate p : equipage.values()) {
			if(!p.favListIsComplete(nbPirate)) {
				throw new Exception("[Error] Favourite loot list of the pirate " + p.getName() + " is not complete !");
			}
		}
		return true;
	}

	/**
	 * // TODO PARTIE II Automatisation
	 * Automatic loot attribution to each member of the crew.
	 * Each pirate (in the order of their ID) receives his favourite object if it is available, otherwise
	 * his second favourite item if it is available, etc.
	 *
	 * @throws Exception if a loot to attribuate is not in the list of loot to be shared.
	 */
	public void autoLootAttribution() throws Exception {
		List<Integer> givedLootList = new ArrayList<>();
		for(Pirate p : equipage.values()) {
			for(int loot : p.getFavList()) {
				if(!givedLootList.contains(loot)) {
					p.setLoot(loot, nbPirate);
					givedLootList.add(loot);
					break;
				}
			}
		}
	}

	/**
	 * Exchange the loots of 2 pirates.
	 *
	 * @param a the pirate involved.
	 * @param b the other pirate involved.
	 * @throws Exception if pirate A or B doesn't exist.
	 */
	public void exchangeLoot(String a, String b) throws Exception {
		Pirate p = equipage.get(a);
		Pirate p2 = equipage.get(b);
		if(p != null && p2 != null) {
			int tmp = p2.getLoot();
			p2.setLoot(p.getLoot(), nbPirate);
			p.setLoot(tmp, nbPirate);
			System.out.println("Successful exchange of loot between " + a + " and " + b + ".");
		} else {
			throw new Exception("[Error] Pirate " + a + " or " + b + " doesn't exist !");
		}
	}

	/**
	 * Returns the pirate with the specified ID. OR null if not found.
	 *
	 * @param id the pirate's ID that we are looking for.
	 * @return the wanted pirate ou null if not found.
	 */
	public Pirate findPirateByID(int id) {
		String name = "" + (char) (A_IN_ASCII + id);
		return equipage.getOrDefault(name, null);
	}

	/**
	 * Calculate and return how many pirates are jealous (the cost)
	 * in the crew for the current attribution of loot.
	 *
	 * @return the cost of the current attribution of loot in the crew (number of pirates jealous of another).
	 */
	public int calcultateCost() {
		int cost = 0;
		for(Pirate p : equipage.values()) {     // pour chaque pirate
			List<Integer> morePrefList = p.getMoreFavList(); // recup list des loots qu'il aurait pref avoir
			if(!morePrefList.isEmpty()) {       // si la liste n'est pas vide
				for(int i = 0; i < nbPirate; i++) { // pour chaque pirate
					if(i != p.getId() && relationship.get(p.getId()).get(i) != ZERO) { // s'il est diff de p && que p le desteste
						Pirate dislikeP = findPirateByID(i);    // recup le pirate detesté
						if(dislikeP != null && morePrefList.contains(dislikeP.getLoot())) { // s'il est pas null && qu'il a l'obj pref de p
							cost++; // on increment de 1
							break;
						}
					}
				}
			}
		}
		return cost;
	}

	public void addPirate(String name) {
		Pirate p = this.equipage.put(name, new Pirate(name));
		if(p == null) {
			this.nbPirate++;
			this.addInRelation();
		} else System.out.println("Pirate " + name + " is already added !");
		showRelation(); // debug
	}

	private void addInRelation() { // TODO avoir un nom plus clair
		relationship.add(new ArrayList<>());
		for(int i = 0; i < nbPirate; i++) { // TODO modifier le nbPirate en equipage.size()
			relationship.get(i).add(ZERO);
			if(i == nbPirate - 1) {
				for(int j = 1; j < nbPirate; j++) {
					relationship.get(i).add(ZERO);
				}
			}
		}
	}

	public void addLoot(String label) {
		lootList.add(new Loot(label));
	}
}
