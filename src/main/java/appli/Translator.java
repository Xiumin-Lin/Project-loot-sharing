package appli;

import java.io.File;

public class Translator {
	public static String translate(File f, Crew c) throws Exception {
		StringBuilder res = new StringBuilder();
		// lire le fichier tant que c pas vide
//		String s = "pirate(nom_pirate_1).";
//		String[] sp = s.split("\\(");
//		System.out.println(Arrays.toString(sp));
//		System.out.println(sp[1].substring(0,sp[1].length() - 2));
//		String[] spp = sp[1].split("\\)");
//		System.out.println(spp[0]);
//		System.out.println(sp[0] + " : " + spp[0]);
		// switch (sp[0]) pirate, loot, deteste ...
		// case pirate : ajouter les pirates
		c.addPirate("nom du pirate");
		// case loot : ajouter les loots
		c.addLoot("nom du loot");
		// case deteste :
		// trouver l'indice associer à nomdupirate1 dans equipage avec la methode c.getPirate(nomPirate)
		// si on trouve le pirate demandé, on recup son id avec .getId()
		res.append(1 + " idPirate1 idPirate2").append(System.lineSeparator());
		// case preference :
		// trouver l'indice associer à nomobject1 dans lootList avec une methode semblable à c.getPirate(nomPirate)
		// mais avec les objects (ex : c.findLootIdByName(nomObj) ou un autre nom)
		res.append(2 + " idPirate1 idObj1 idObj2 idObj3 idObj4").append(System.lineSeparator());
		// default : jeter erreur

		return res.toString();
	}
}
