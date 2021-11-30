package util;

import pirate.Crew;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * A utility class to translate an instruction file to init a crew into
 * a string input accepted by the main program.
 *
 * @author Xiumin Lin
 * @author Céline Li
 * @version 1.0
 */
public class Translator {
	private static final String PIRATE = "pirate";
	private static final String OBJET = "objet";
	private static final String DETESTE = "deteste";
	private static final String PREFERENCES = "preferences";

	/**
	 * a private constructor to hide the implicit public one
	 */
	private Translator() {}

	public static String translate(File f, Crew c) throws Exception {
		StringBuilder res = new StringBuilder();
		try(BufferedReader br = new BufferedReader(new FileReader(f))) {
			String line;
			while((line = br.readLine()) != null) {
				String[] command = line.split("\\(");
				switch(command[0]) {
					case PIRATE: // add a pirate
						String[] nom = command[1].split("\\)");
						c.addPirate(nom[0]);
						break;
					case OBJET:
						String[] obj = command[1].split("\\)");
						c.addLoot(obj[0]);
						break;
					case DETESTE:
						String[] dislike = command[1].split(",");
						String[] pirate2 = dislike[1].split("\\)");
						res.append("1 ")
								.append(c.getPirate(dislike[0]).getName()).append(" ")
								.append(c.getPirate(pirate2[0]).getName()).append(System.lineSeparator());
						break;
					case PREFERENCES:
						String[] str = command[1].split("\\)");
						String[] liste = str[0].split(",");
						res.append("2 ")
								.append(c.getPirate(liste[0]).getName()).append(" ")
								.append(c.findLootIdByName(liste[1])).append(" ")
								.append(c.findLootIdByName(liste[2])).append(" ")
								.append(c.findLootIdByName(liste[3])).append(" ")
								.append(c.findLootIdByName(liste[4])).append(System.lineSeparator());
						break;
					default:
						throw new Exception("[Error] ! Unknown command name : " + command[0]);
				}
			}
		}
		res.append("0").append(System.lineSeparator());
		return res.toString();
	}
}
