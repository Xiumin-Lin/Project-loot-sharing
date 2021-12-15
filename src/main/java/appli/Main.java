package appli;

import pirate.Crew;
import util.Menu;
import util.MenuManager;
import util.Parser;

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
			String inputString = Parser.translate(dataFile, crew);
			// DEBUG display translate result
//			System.out.println("---- Translate Result ----\n" + inputString + "---- Result END ----");
			Scanner fileScanner = new Scanner(inputString);
			// adding relationships & loot preference with the menu 1
			MenuManager.showMenu(Menu.FIRST, crew, fileScanner);  // display the menu 1
			fileScanner.close();
		} catch(Exception e) {
			System.out.println(e.getMessage() + "(" + e + ")" + "\nEnd of the program !"); // error msg
			return; // stop the program
		}

		// Display & manage the main menu
		System.out.println();
		crew.showCrew();
		System.out.println();
		crew.showRelation();
		MenuManager.showMenu(Menu.MAIN, crew, sc); // display the menu 3
		sc.close();
	}
}
