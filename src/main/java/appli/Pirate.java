package appli;

import java.util.ArrayList;

public class Pirate {
	private static int cpt_id = 0;
	private int id;
	private String name;
	private int loot;
	private ArrayList<Integer> prefList; // TODO create loot class

	Pirate(String name) {
		this.id = cpt_id++;
		this.name = name;
		this.loot = -1; // -1 = no loot
		this.prefList = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void addPref(int pref, int borne) throws Exception {
		if(pref <= 0 || pref > borne)
			throw new Exception("Pref : " + pref + " is out of range !");
		else if(prefList.contains(pref)){
			this.prefList.clear();
			throw new Exception("Pref : " + pref + " is already added !");
		}
		else
			this.prefList.add(pref);
	}

	@Override
	public String toString() {
		return "Pirate{" + "id=" + id + ", name=" + name + ", loot=" + loot + ", prefList=" + prefList + '}';
	}
}
