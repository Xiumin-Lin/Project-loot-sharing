package appli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Crew {
	private ArrayList<ArrayList<Integer>> relation = new ArrayList<>();
	private int nbPirate;
	private HashMap<String, Pirate> equipage = new HashMap<>();

	public Crew(int n) {
		this.nbPirate = n;
		// creation de tous les pirates de l'equpage
		System.out.println("Votre equipage : "); // DEBUG
		for(int i = 0; i < nbPirate; i++) {
			String lettre = "" + (char) (65 + i);
			equipage.put(lettre, new Pirate(lettre));
			System.out.println("\t" + equipage.get(lettre)); // DEBUG
		}
		System.out.println(); // DEBUG
		initRelation(n);
	}

	private void initRelation(int n) {
		System.out.println("Matrice d'adjacence des relations : "); // DEBUG
		for(int i = 0; i < n; i++) {
			relation.add(new ArrayList<>());
			for(int j = 0; j < n; j++) {
				relation.get(i).add(0);
				System.out.print(relation.get(i).get(j) + " "); // DEBUG
			}
			System.out.println(); // DEBUG
		}

	}

	public int getNbPirate() {
		return nbPirate;
	}

	public Pirate getPirate(String name) {
		return equipage.getOrDefault(name, null);
	}

	public void addRelation(String a, String b) {
		if(equipage.containsKey(a) && equipage.containsKey(b)) {
			int pirateID = equipage.get(a).getId();
			int pirateID2 = equipage.get(b).getId();
			relation.get(pirateID).set(pirateID2, 1);
			relation.get(pirateID2).set(pirateID, 1);
			showRelation();
		} else {
			System.out.println("Erreur ! Pirate " + a + " ou " + b + " inexistant");
		}
	}

	public void showRelation() { // DEBUG
		System.out.println("Matrice d'adjacence des relations : ");
		for(int i = 0; i < relation.size(); i++) {
			for(int j = 0; j < relation.size(); j++) {
				System.out.print(relation.get(i).get(j) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void showCrew() {
		System.out.println("Equipage : ");
		equipage.forEach((s, pirate) -> System.out.println("\t" + pirate));
	}

	public void showCrewLoot() {
		System.out.println();
		equipage.forEach((s, pirate) -> System.out.println(s + ":o" + pirate.getLoot()));
		System.out.println();
	}

	public void verifCrew() throws Exception {
		for(Pirate p : equipage.values()) {
			if(!p.verif(nbPirate)) {
				throw new Exception("Le pirate " + p.getName() + " n'a pas ses preferences !");
			}
		}
	}

	public void giveLootAuto() throws Exception { // TODO to upgrade to a better algo
		int cpt = 1;
		for(Pirate p : equipage.values()) {
			p.setLoot(cpt++, nbPirate);
		}
	}

	public void exchangeLoot(String a, String b) throws Exception {
		Pirate p = equipage.get(a);
		Pirate p2 = equipage.get(b);
		if(p != null && p2 != null) {
			int tmp = p2.getLoot();
			p2.setLoot(p.getLoot(), nbPirate);
			p.setLoot(tmp, nbPirate);
		} else {
			throw new Exception("Erreur : " + a + " ou/et " + b + " n'existe pas dans l'equipage !");
		}
	}

	public Pirate findPirateByID(int id) {
		String name = "" + (char) (id + 65);
		return equipage.getOrDefault(name, null);
	}

	public int calcultateCost() {
		int cost = 0;
		for(Pirate p : equipage.values()) {     // pour chaque pirate
			List<Integer> morePrefList = p.getMorePrefList(); // recup list des loots qu'il aurait pref avoir
			if(!morePrefList.isEmpty()) {       // si la liste n'est pas vide
				for(int i = 0; i < nbPirate; i++) { // pour chaque pirate
					if(i != p.getId() && relation.get(p.getId()).get(i) != 0) { // s'il est diff de p && que p le desteste
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
}
