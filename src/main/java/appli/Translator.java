package appli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Translator class to translate an instruction file into an input accepted by the main program.
 *
 * @author Xiumin Lin
 * @author Céline Li
 * @version 1.0
 */
public class Translator {

	public static String translate(File f, Crew c) throws Exception {
		StringBuilder res = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(f));
		// lire le fichier tant que c pas vide
		String line;
		while((line = br.readLine()) != null) {
			String[] s = line.split("\\(");
			// switch(sp[0]) pirate, loot, deteste ...
			switch(s[0]) {
				case "pirate": // ajouter les pirates
					String[] nom = s[1].split("\\)");
					c.addPirate(nom[0]);
					break;
				case "objet": // ajouter les loots
					String[] obj = s[1].split("\\)");
					c.addLoot(obj[0]);
					break;
				case "deteste":
					String[] dislike = s[1].split(",");
					String[] pirate2 = dislike[1].split("\\)");
					res.append("1 ")
							.append(c.getPirate(dislike[0]).getName()).append(" ")
							.append(c.getPirate(pirate2[0]).getName()).append(System.lineSeparator());
					break;
				case "preferences": //A,1,2,3,4).
					String[] str = s[1].split("\\)"); // ["A,1,2,3,4", ")."]
					String[] liste = str[0].split(","); // ["A","1","2","3","4"]
					res.append("2 ")
							.append(c.getPirate(liste[0]).getName()).append(" ")
							.append(c.findLootIdByName(liste[1])).append(" ")
							.append(c.findLootIdByName(liste[2])).append(" ")
							.append(c.findLootIdByName(liste[3])).append(" ")
							.append(c.findLootIdByName(liste[4])).append(System.lineSeparator());
					break;
				default: // jeter erreur
					throw new Exception("Erreur ! Nom de methode incorrecte !");
			}
		}
		br.close();
		res.append("0").append(System.lineSeparator());
		return res.toString();
	}
}
