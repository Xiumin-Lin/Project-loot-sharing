package appli;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main class to run the program.
 *
 * @author Xiumin Lin
 * @author Céline Li
 * @version 1.0
 */
public class Main {
	public static final int NB_PIRATE_MAX = 26;

	public static void main(String[] args) {
		// P2
		Crew crew = new Crew();
		File f = new File("path/to/texte.txt");
		try {
			String inputString = Translator.translate(f, crew);
			System.out.println("--- DEBUG ---\n" + inputString + "--- END ---"); // DEBUG
			Scanner fileScanner = new Scanner(inputString);
			// adding relationships & loot preference
			menu(crew, fileScanner, 1);  // display the menu 1
			fileScanner.close();
		} catch(Exception e) {
			System.out.println("[Error] " + e.getMessage());
			return; // stop the program
		}

		// Display & manage the main menu
		crew.showRelation();
		Scanner sc = new Scanner(System.in);
		menu(crew, sc, 3);
		sc.close();

		// P1 ---
		/*Scanner sc = new Scanner(f);
		int nbPirate = enterNbPirate(sc);
		Crew crew = new Crew(nbPirate);
		menu(crew, sc, 1);  // display the menu 1
		menu(crew, sc, 2);  // display the menu 1
		sc.close();*/
	}

	/**
	 * Return the number of pirates in the crew from the scanner.
	 *
	 * @param sc a scanner for text input and output.
	 * @return the number of pirates in the crew.
	 */
	private static int enterNbPirate(Scanner sc) {
		int nb;
		do {
			System.out.print("Enter the number of pirates (between 1 and " + NB_PIRATE_MAX + ") : ");
			try {
				nb = sc.nextInt();
			} catch(InputMismatchException e) {
				System.out.println("I want a integer !\n");
				nb = -1; // set to -1 to keep the loop going
			}
			sc.nextLine();
		} while(nb <= 0 || nb > NB_PIRATE_MAX);
		return nb;
	}

	/**
	 * Manage the different menus for this loot sharing program in a pirate crew.
	 *
	 * @param crew    the pirate crew to manage.
	 * @param sc      a scanner for text input and output.
	 * @param menuNum the number of the menu to be displayed and managed. // TODO change this
	 */
	private static void menu(Crew crew, Scanner sc, int menuNum) {
		boolean isEnd = false;
		do {
			try {
				if(menuNum == 2) menu2Text();
				else if(menuNum == 3) menu3Text();
				int choice = sc.nextInt();
				switch(menuNum) {
					case 1:
						isEnd = menu1Choice(crew, sc, choice);
						break;
					case 2:
						isEnd = menu2Choice(crew, sc, choice);
						continue;
					case 3:
						isEnd = menu3Choice(crew, sc, choice);
						break;
					default:
						System.out.println("Invalid menu number !");
						return;
				}
			} catch(InputMismatchException e) {
				System.out.println("I want a integer !");
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
			sc.nextLine();
		} while(!isEnd);
	}

	/**
	 * Display the text of the menu 1 for relationship and favourite loot management.
	 */
	public static void menu1Text() {
		System.out.println("\nMenu 1 :");
		System.out.println("\t(1) Add a relationship");
		System.out.println("\t(2) Adding preferences");
		System.out.println("\t(0) End");
		System.out.print(">>> ");
	}

	/**
	 * Display and manage the first menu choices about relationship and favourite loot management.
	 * Return true if the user has finished using menu 1, else return false.
	 *
	 * @param crew   the pirate crew to manage.
	 * @param sc     a scanner for text input and output.
	 * @param choice action chosen for menu 1.
	 * @return true if the user has finished using menu 1, else return false.
	 * @throws Exception if an error is made during the execution of the action chosen
	 *                   by the user with a descriptive message.
	 */
	public static boolean menu1Choice(Crew crew, Scanner sc, int choice) throws Exception {
		switch(choice) {
			case 1: // add relation
				System.out.print("Enter the letters of the 2 pirates who dislike each other ! (Ex: A B) : ");
				String a = sc.next().toUpperCase();
				String b = sc.next().toUpperCase();
				crew.addRelation(a, b);
				break;
			case 2: // add pref
				System.out.print("Enter the preferences of a pirate (Ex: A 1 2 3 4) : ");
				String pName = sc.next().toUpperCase();
				for(int i = 0; i < crew.getNbPirate(); i++) {
					crew.addFavLootToPirate(pName, sc.nextInt());
				}
				System.out.println("Success of adding pref : " + crew.getPirate(pName)); // display pirate's info
				break;
			case 0: // end
				crew.allPirateFavListIsComplete();
//				System.out.println("Exit !");
//				System.out.println("Automatic Loot Attribution...");
//				crew.autoLootAttribution();
				crew.showCrew(); // DEBUG
				return true; // isEnd = true
			default:
				System.out.println("Invalid Input ! Retry !");
		}
		return false;
	}

	/**
	 * Display the text of menu 2 for exchanging an object and display the cost.
	 */
	public static void menu2Text() {
		System.out.println("\nMenu 2 :");
		System.out.println("\t(1) Exchanging an object");
		System.out.println("\t(2) Show cost");
		System.out.println("\t(0) End");
		System.out.print(">>> ");
	}

	/**
	 * Display and manage the second menu choices about exchanging an object and display the cost.
	 * Return true if the user has finished using menu 2, else return false.
	 *
	 * @param crew   the pirate crew to manage.
	 * @param sc     a scanner for text input and output.
	 * @param choice action chosen for menu 2.
	 * @return true if the user has finished using menu 1, else return false.
	 * @throws Exception if an error is made during the execution of the action chosen
	 *                   by the user with a descriptive message.
	 */
	public static boolean menu2Choice(Crew crew, Scanner sc, int choice) throws Exception {
		switch(choice) {
			case 1: // Exchange
				System.out.print("Enter the letters of the 2 pirates who have to exchange their loots ! (Ex: A B) : ");
				String a = sc.next().toUpperCase();
				String b = sc.next().toUpperCase();
				crew.exchangeLoot(a, b);
				crew.showCrewLoot();
				break;
			case 2: // Cost
				System.out.println("The cost : " + crew.calcultateCost());
				crew.showCrewLoot();
				break;
			case 0: // End
//				System.out.println("End of the program."); // P1
				return true;
			default:
				System.out.println("Invalid Input ! Retry !");
		}
		return false;
	}

	/**
	 * Display the text of menu 3 for exchanging an object and display the cost.
	 */
	public static void menu3Text() {
		System.out.println("\nMenu Principal :");
		System.out.println("\t(1) Resolution automatique");
		System.out.println("\t(2) Resolution manuelle");
		System.out.println("\t(3) Sauvegarde");
		System.out.println("\t(0) End");
		System.out.print(">>> ");
	}

	public static boolean menu3Choice(Crew crew, Scanner sc, int choice) throws Exception {
		System.out.println();
		switch(choice) {
			case 1: // resolve auto
				System.out.println("Resolution automatique :");
				crew.autoLootAttribution();
				crew.showCrew();
				break;
			case 2: // resolve manuelle
				System.out.println("Resolution manuelle :");
				menu(crew, sc, 2); // invoque menu 2
				break;
			case 3: // sauvegarde
				System.out.println("Save.");
				break;
			case 0: // End
				System.out.println("End of the program.");
				return true;
			default:
				System.out.println("Invalid Input ! Retry !");
		}
		return false;
	}
}
