package appli;

import java.util.ArrayList;
import java.util.HashMap;

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
}
