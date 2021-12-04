package pirate;

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
	private static final String CAN_T_FIND_THE_PIRATE = "[Error] Can't find the Pirate : ";

	/**
	 * An adjacency matrix that represents the agreement between each member of the crew.
	 * If 2 pirates, i and j, do not like each other, relationship.get(i).get(j) = ONE.
	 * Else it's ZERO.
	 */
	private ArrayList<ArrayList<Integer>> relationship;
	private HashMap<String, Pirate> equipage;
	private ArrayList<Loot> lootToShareList;

	/**
	 * Basic Crew Constructor
	 */
	public Crew() {
		this.relationship = new ArrayList<>();
		this.equipage = new HashMap<>();
		this.lootToShareList = new ArrayList<>();
	}

	/**
	 * Constructor who initializes a crew of n members and where nobody hates anybody.
	 * Assigns an automatic name to the pirate. Ex : A, B, C, etc.
	 *
	 * @param n the number of pirates in the crew.
	 */
	public Crew(int n) {
		this();
		// creation of all the pirates of the equipage
		System.out.println("Initialization of the crew : "); // DEBUG
		for(int i = 0; i < n; i++) {
			String lettre = "" + (char) (A_IN_ASCII + i);
			equipage.put(lettre, new Pirate(lettre));
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
		return equipage.size();
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
		} else {
			throw new Exception(CAN_T_FIND_THE_PIRATE + a + " or " + b);
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
	 * @return the list of pirates and the loot they have in a String.
	 */
	public String getCrewLoot() {
		StringBuilder res = new StringBuilder();
		equipage.forEach((s, pirate) -> {
			res.append(s).append(":");
			if(pirate.getLoot() != null) res.append(pirate.getLoot().getLabel());
			else {
				System.out.println("[Info] " + pirate.getName() + " doesn't have a loot.");
				res.append("null");
			}
			res.append("\n");
		});
		return res.toString();
	}

	/**
	 * Display the list of pirates and the loot they have.
	 */
	public void showCrewLoot() {
		System.out.println("\nThe loot of each pirate : ");
		System.out.print(getCrewLoot());
	}

	/**
	 * Check if all pirate favourite loot list is complete.
	 *
	 * @return true if all pirate favourite loot list is complete,
	 * else false if there is at least one list that is incomplete
	 */
	public boolean allPirateFavListIsComplete() {
		for(Pirate p : equipage.values()) {
			if(!p.favListIsComplete(this.getNbPirate())) {
				System.out.println("[Error] Favourite loot list of the pirate " + p.getName() + " is not complete !");
				return false;
			}
		}
		return true;
	}

	/**
	 * Automatic loot attribution to each member of the crew.
	 * Each pirate (in the order of their ID) receives his favourite object if it is available, otherwise
	 * his second favourite item if it is available, etc.
	 */
	public void autoLootAttribution() {
		List<Loot> givedLootList = new ArrayList<>();
		for(Pirate p : equipage.values()) {
			for(Loot loot : p.getFavList()) {
				if(!givedLootList.contains(loot)) {
					p.setLoot(loot);
					givedLootList.add(loot);
					break;
				}
			}
		}
	}

	/**
	 * // TODO PARTIE II Automatisation
	 * @throws Exception
	 */
	public void autoLootAttributionSmart() {
		autoLootAttribution();
		int s1=calcultateCost();
			for(Pirate p1 : equipage.values()) {
				for(Pirate p2 : equipage.values()) {
					if(p1.getId() != p2.getId() && relationship.get(p1.getId()).get(p2.getId()) != ZERO) {
						try{
							exchangeLoot(p1.getName(),p2.getName());
							int s2=calcultateCost();
							if(s2>s1) {
								exchangeLoot(p1.getName(),p2.getName());
							}
							else {
								s1=s2;
							}
							}catch(Exception e) {
								System.out.println(e.getMessage());
							}

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
			Loot tmp = p2.getLoot();
			p2.setLoot(p.getLoot());
			p.setLoot(tmp);
			System.out.println("Successful exchange of loot between " + a + " and " + b + ".");
		} else {
			throw new Exception(CAN_T_FIND_THE_PIRATE + a + " or " + b);
		}
	}

	/**
	 * Calculate and return how many pirates are jealous (the cost)
	 * in the crew for the current attribution of loot.
	 *
	 * @return the cost of the current attribution of loot in the crew (number of pirates jealous of another).
	 */
	public int calcultateCost() {
		int cost = 0;
		for(Pirate p : equipage.values()) {                 // for each pirate
			List<Loot> morePrefList = p.getMoreFavList();   // get the list of loots he would have preferred to have
			if(!morePrefList.isEmpty()) {                   // if it's not empty
				for(Pirate anotherP : equipage.values()) {  // again for each pirate
					// if "anotherP" is not "p", they hate each other, and he has an object that "p" prefers
					if(p.getId() != anotherP.getId() && relationship.get(p.getId()).get(anotherP.getId()) != ZERO
							&& morePrefList.contains(anotherP.getLoot())) {
						cost++; // increment by 1
						break;
					}
				}
			}
		}
		return cost;
	}

	/**
	 * Add a pirate to the crew. The relationship table is updated automatically.
	 *
	 * @param name the pirate's name.
	 */
	public void addPirate(String name) throws Exception {
		if(!equipage.containsKey(name)) {
			equipage.put(name, new Pirate(name));
			addPirateInRelationship();
		} else throw new Exception("[Error] Pirate " + name + " is already added !");
		showRelation(); // DEBUG
	}

	/**
	 * Add a new pirate to the relationship matrix.
	 * By default, his relationships are set to ZERO, which means that nobody hates him.
	 */
	private void addPirateInRelationship() {
		relationship.add(new ArrayList<>());
		for(int i = 0; i < getNbPirate(); i++) {
			relationship.get(i).add(ZERO);
			if(i == getNbPirate() - 1) {
				for(int j = 1; j < getNbPirate(); j++) {
					relationship.get(i).add(ZERO);
				}
			}
		}
	}

	/**
	 * Add a new loot in lootToShareList.
	 *
	 * @param label loot's label.
	 */
	public void addLoot(String label) {
		lootToShareList.add(new Loot(label));
	}

	/**
	 * Add a favourite loot in the favList of a pirate.
	 * It's added at the last position in the list.
	 *
	 * @param pirateName the name of the pirate concerned.
	 * @param lootIdx    the loot's index in the lootToShareList.
	 * @throws Exception the loot is not in the lootToShareList
	 *                   or is already present in his favourite loot list.
	 */
	public void addFavLootToPirate(String pirateName, int lootIdx) throws Exception {
		Pirate p = getPirate(pirateName);
		if(p != null) p.addFavLoot(lootToShareList.get(lootIdx - 1));
		else throw new Exception(CAN_T_FIND_THE_PIRATE + pirateName);
	}

	/**
	 * Searches for and returns the ID of the loot with the specified name. Returns -1 if not found.
	 *
	 * @param lootName the loot's name
	 * @return the id of the loot with the specified name. If not found, return -1.
	 */
	public int findLootIdByName(String lootName) {
		for(Loot l : lootToShareList) {
			if(l.getLabel().equals(lootName)) {
				return l.getId();
			}
		}
		return -1;
	}
}
