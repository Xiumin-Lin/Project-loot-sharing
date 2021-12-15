package util;

import pirate.Crew;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * A utility class to parse an instruction file to init a crew into
 * a string input accepted by the main program and its menus.
 *
 * @author Xiumin Lin
 * @author Céline Li
 * @version 1.0
 */
public class Parser {
	private static final String PIRATE = "pirate";
	private static final String OBJET = "objet";
	private static final String DETESTE = "deteste";
	private static final String PREFERENCES = "preferences";

	/**
	 * a private constructor to hide the implicit public one
	 */
	private Parser() {}

	/**
	 * Parse an instruction file to a string input which is accepted by the main program and its menus.
	 * The add pirate and loot instructions are executed immediately.
	 * Relationship and preference instruction are translated & are returned in string.
	 * Check {@link MenuManager} and its menuChoices methods for more detail about the format of an instruction.
	 *
	 * @param f the file containing the instructions
	 * @param c the crew that is affected by the instructions
	 * @return a string input containing the instructions in a format accepted by the main program and its menus
	 * @throws Exception if there are an unknown command or are an error when adding a pirate or loot.
	 */
	public static String translate(File f, Crew c) throws Exception {
		System.out.println("Translation of the instructions in the file : " + f);
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
						res.append("2 ").append(c.getPirate(liste[0]).getName()).append(" ");
						for(int i = 1; i < liste.length; i++) {
							res.append(c.findLootIdByName(liste[i])).append(" ");
						}
						res.append(System.lineSeparator());
						break;
					default:
						throw new Exception("[Error] ! Unknown command name : " + command[0]);
				}
			}
		}
		res.append("0").append(System.lineSeparator());
		System.out.println("The translation was successfully completed !\n");
		return res.toString();
	}
}
