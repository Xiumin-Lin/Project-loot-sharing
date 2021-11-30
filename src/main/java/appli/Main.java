package appli;

import pirate.Crew;
import util.Menu;
import util.Translator;

import java.io.File;
import java.util.Scanner;

/**
 * Main class to run the program.
 *
 * @author Xiumin Lin
 * @author Céline Li
 * @version 1.0
 */
public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Crew crew = new Crew();
		File dataFile;
		do {
			System.out.print("Enter the path to the file : ");
			String filePath = sc.nextLine();
			dataFile = new File(filePath);
			if(dataFile.exists() && dataFile.canRead()) break;
			else System.out.println("The file does not exist or cannot be read !");
		} while(true);

		try {
			String inputString = Translator.translate(dataFile, crew);
			System.out.println("---- DEBUG ----\n" + inputString + "---- END ----"); // DEBUG
			Scanner fileScanner = new Scanner(inputString);
			// adding relationships & loot preference
			Menu.showMenu(1, crew, fileScanner);  // display the menu 1
			fileScanner.close();
		} catch(Exception e) {
			System.out.println("[Error] " + e.getMessage() + "(" + e + ")");
			return; // stop the program
		}

		// Display & manage the main menu
		crew.showRelation();
		Menu.showMenu(3, crew, sc); // display the menu 3
		sc.close();
	}

}
