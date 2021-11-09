package appli;

import java.io.File;

public class Translator {
	public static String translate(File f, Crew c){
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
		// case deteste
		res.append(1 + " nomdupirate1 nomdupirate2").append(System.lineSeparator());
		// default : jeter erreur

		return res.toString();
	}
}
