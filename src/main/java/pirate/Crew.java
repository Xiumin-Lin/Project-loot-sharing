package pirate;

import java.util.*;

/**
 * The Crew class represents a pirate crew and their agreements with each other.
 *
 * @author Xiumin Lin
 * @author Céline Li
 * @version 1.0
 */
public class Crew {
	private static final Random RAND_GENERATOR = new Random();
	private static final int LIKE = 0;
	private static final int HATE = 1;
	private static final String CAN_T_FIND_THE_PIRATE = "[Error] Can't find the Pirate : ";

	/**
	 * An adjacency matrix that represents the agreement between each member of the crew.
	 * If 2 pirates, i and j, do not like each other, relationship.get(i).get(j) = {@link #HATE}.
	 * Else it's {@link #LIKE}.
	 */
	private ArrayList<ArrayList<Integer>> relationship;
	private LinkedHashMap<String, Pirate> equipage;
	private ArrayList<Loot> lootToShareList;

	/**
	 * Basic Crew Constructor
	 */
	public Crew() {
		this.relationship = new ArrayList<>();
		this.equipage = new LinkedHashMap<>();
		this.lootToShareList = new ArrayList<>();
	}

	/**
	 * Display the Adjacency Matrix of Relationship. (Used in initRelation & addRelation)
	 */
	public void showRelation() {
		System.out.println("Adjacency Matrix of Relationship : ");
		for(int i = 0; i < relationship.size(); i++) {
			for(int j = 0; j < relationship.size(); j++) {
				System.out.print(relationship.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}

	/**
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
	 * Add a pirate to the crew. The relationship table is updated automatically.
	 *
	 * @param name the pirate's name.
	 */
	public void addPirate(String name) throws Exception {
		if(!equipage.containsKey(name)) {
			equipage.put(name, new Pirate(name));
			addPirateInRelationship();
		} else throw new Exception("[Error] Pirate " + name + " is already added !");
	}

	/**
	 * Add a new pirate to the relationship matrix.
	 * By default, his relationships are set to {@link #LIKE}, which means that nobody hates him.
	 */
	private void addPirateInRelationship() {
		relationship.add(new ArrayList<>());
		for(int i = 0; i < getNbPirate(); i++) {
			relationship.get(i).add(LIKE);
			if(i == getNbPirate() - 1) {
				for(int j = 1; j < getNbPirate(); j++) {
					relationship.get(i).add(LIKE);
				}
			}
		}
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
			relationship.get(pirateID).set(pirateID2, HATE);
			relationship.get(pirateID2).set(pirateID, HATE);
			System.out.println("Success in adding the relationship between " + a + " and " + b + ".");
		} else {
			throw new Exception(CAN_T_FIND_THE_PIRATE + a + " or " + b);
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

	/**
	 * Check if all pirate favourite loot list is complete.
	 *
	 * @return true if all pirate favourite loot list is complete,
	 * else false if there is at least one list that is incomplete.
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
		} else {
			throw new Exception(CAN_T_FIND_THE_PIRATE + a + " or " + b);
		}
	}

	/**
	 * Same as {@link #exchangeLoot(String pirate1Name, String pirate2Name)} & display a result message if success.
	 *
	 * @param a the pirate involved.
	 * @param b the other pirate involved.
	 * @throws Exception if pirate A or B doesn't exist.
	 */
	public void exchangeLootWithResultMsg(String a, String b) throws Exception {
		exchangeLoot(a, b);
		System.out.println("Successful exchange of loot between " + a + " and " + b + ".");
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
		System.out.println("\nThe loot of each pirate (pirateName:objectName) : ");
		System.out.print(getCrewLoot());
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
					if(p.getId() != anotherP.getId() && relationship.get(p.getId()).get(anotherP.getId()) != LIKE
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
	 * Naive approximation algorithm proposed by the project.
	 * the greater the number of attempts, the closer the result cost is to the optimal cost.
	 * All pirate must already have a loot before using this method.
	 *
	 * @param nbAttempt the number of times the algo tries before stopping
	 */
	public void autoLootAttributionSmart(int nbAttempt) {
		int cost = calcultateCost();
		List<Pirate> pirateList = new ArrayList<>(equipage.values());
		int randNb1;
		int randNb2;
		for(int i = 0; i < nbAttempt; i++) {
			// get random index of the pirateList
			do {
				randNb1 = RAND_GENERATOR.nextInt(pirateList.size());
				randNb2 = RAND_GENERATOR.nextInt(pirateList.size());
			} while(randNb1 == randNb2);
			// get a random pirate p1 & p2
			Pirate p1 = pirateList.get(randNb1);
			Pirate p2 = pirateList.get(randNb2);
			try {
				exchangeLoot(p1.getName(), p2.getName());
				int newCost = calcultateCost();
				if(newCost > cost) {
					exchangeLoot(p1.getName(), p2.getName());
				} else {
					cost = newCost;
				}
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
