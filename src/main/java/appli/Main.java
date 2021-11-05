package appli;

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
		Scanner sc = new Scanner(System.in);
		int nbPirate = enterNbPirate(sc);
		Crew crew = new Crew(nbPirate);

		menu(crew, sc, true);   // true display the menu 1
		menu(crew, sc, false);  // false display the menu 2

		sc.close();
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
	 * @param isMenu1 true for display and manage the menu 1, else false for the menu 2.
	 */
	private static void menu(Crew crew, Scanner sc, boolean isMenu1) {
		boolean isEnd = false;
		do {
			if(isMenu1) menu1Text();
			else menu2Text();
			try {
				int choice = sc.nextInt();
				isEnd = (isMenu1) ? menu1Choice(crew, sc, choice) : menu2Choice(crew, sc, choice);
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
				Pirate p = crew.getPirate(pName);
				if(p != null) {
					for(int i = 0; i < crew.getNbPirate(); i++) {
						p.addFavLoot(sc.nextInt(), crew.getNbPirate());
					}
					System.out.println("Success of adding pref : " + p); // display pirate's info
				} else {
					System.out.println("Pirate " + pName + " doesn't exist !");
				}
				break;
			case 0: // end
				crew.allPirateFavListIsComplete();
				System.out.println("Exit !");
				System.out.println("Automatic Loot Attribution...");
				crew.autoLootAttribution();
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
				System.out.println("End of the program.");
				return true;
			default:
				System.out.println("Invalid Input ! Retry !");
		}
		return false;
	}
}
