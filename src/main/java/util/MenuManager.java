package util;

import pirate.Crew;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A utility class to display and manage the different menus for the program
 */
public class MenuManager {
	private static final String EXIT_CHOICE = "\t(0) End";
	private static final String INVALID_INPUT_RETRY = "Invalid Input ! Retry !";

	/**
	 * a private constructor to hide the implicit public one
	 */
	private MenuManager() {}

	/**
	 * Show & Manage the different menus for this loot sharing program in a pirate crew.
	 *
	 * @param menu the type of menu to be displayed and managed.
	 * @param crew the pirate crew to manage.
	 * @param sc   a scanner for text input and output.
	 */
	public static void showMenu(Menu menu, Crew crew, Scanner sc) {
		boolean isEnd = false;
		do {
			try {
				if(menu == Menu.SECOND) menu2Text();
				else if(menu == Menu.MAIN) menu3Text();
				int choice = sc.nextInt();
				switch(menu) {
					case FIRST:
						isEnd = menu1Choice(crew, sc, choice);
						break;
					case SECOND:
						isEnd = menu2Choice(crew, sc, choice);
						continue;
					case MAIN:
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
	private static void menu1Text() {
		System.out.println("\nMenu 1 :");
		System.out.println("\t(1) Add a relationship");
		System.out.println("\t(2) Adding preferences");
		System.out.println(EXIT_CHOICE);
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
	private static boolean menu1Choice(Crew crew, Scanner sc, int choice) throws Exception {
		switch(choice) {
			case 1: // add relation
				System.out.print("Enter the letters of the 2 pirates who dislike each other ! (Ex: A B) : ");
				String a = sc.next();
				String b = sc.next();
				crew.addRelation(a, b);
				break;
			case 2: // add pref
				System.out.print("Enter the preferences of a pirate (Ex: A 1 2 3 4) : ");
				String pName = sc.next();
				for(int i = 0; i < crew.getNbPirate(); i++) {
					crew.addFavLootToPirate(pName, sc.nextInt());
				}
				System.out.println("Success of adding pref : " + crew.getPirate(pName)); // display pirate's info
				break;
			case 0: // end
				if(crew.allPirateFavListIsComplete()) {
					System.out.println("Exit Menu 1!");
					System.out.println("Automatic Loot Attribution...");
					crew.autoLootAttribution();
					crew.showCrew(); // DEBUG
					return true;
				}
				break;
			default:
				System.out.println(INVALID_INPUT_RETRY);
		}
		return false;
	}

	/**
	 * Display the text of menu 2 for exchanging an object and display the cost.
	 */
	private static void menu2Text() {
		System.out.println("\nMenu 2 :");
		System.out.println("\t(1) Exchanging an object");
		System.out.println("\t(2) Show cost");
		System.out.println(EXIT_CHOICE);
		System.out.print(">>> ");
	}

	/**
	 * Display and manage the second menu choices about exchanging an object and display the cost.
	 * Return true if the user has finished using menu 2, else return false.
	 *
	 * @param crew   the pirate crew to manage.
	 * @param sc     a scanner for text input and output.
	 * @param choice action chosen for menu 2.
	 * @return true if the user has finished using menu 2, else return false.
	 * @throws Exception if an error is made during the execution of the action chosen
	 *                   by the user with a descriptive message.
	 */
	private static boolean menu2Choice(Crew crew, Scanner sc, int choice) throws Exception {
		switch(choice) {
			case 1: // Exchange
				System.out.print("Enter the letters of the 2 pirates who have to exchange their loots ! (Ex: A B) : ");
				String a = sc.next();
				String b = sc.next();
				crew.exchangeLoot(a, b);
				crew.showCrewLoot();
				break;
			case 2: // Cost
				System.out.println("The cost : " + crew.calcultateCost());
				crew.showCrewLoot();
				break;
			case 0: // End
				System.out.println("Exit Menu 2!");
				return true;
			default:
				System.out.println(INVALID_INPUT_RETRY);
		}
		return false;
	}

	/**
	 * Display the text of menu 3 for exchanging an object and display the cost.
	 */
	private static void menu3Text() {
		System.out.println("\nMain menu :");
		System.out.println("\t(1) Automatic resolution");
		System.out.println("\t(2) Manual resolution");
		System.out.println("\t(3) Backup");
		System.out.println(EXIT_CHOICE);
		System.out.print(">>> ");
	}

	/**
	 * Display and manage the third menu choices (the main menu) about automatic or
	 * manual resolution of the sharing loot problem and backup the solution.
	 * Return true if the user has finished using the main menu, else return false.
	 *
	 * @param crew   the pirate crew to manage.
	 * @param sc     a scanner for text input and output.
	 * @param choice action chosen for menu 3.
	 * @return true if the user has finished using menu 3, else return false.
	 */
	private static boolean menu3Choice(Crew crew, Scanner sc, int choice) {
		System.out.println();
		switch(choice) {
			case 1: // Resolve auto
				System.out.println("Automatic resolution :");
				crew.autoLootAttributionSmart();
				crew.showCrew();
				System.out.println("The cost : " + crew.calcultateCost());
				break;
			case 2: // Resolve manuelle
				System.out.println("Manual resolution :");
				showMenu(Menu.SECOND, crew, sc); // call menu 2
				break;
			case 3: // Backup
				System.out.print("---- Saving ----\nEnter the backup file name : ");
				String saveFileName = sc.next();
				try(BufferedWriter bW = new BufferedWriter(new FileWriter(saveFileName));
				    PrintWriter pW = new PrintWriter(bW)) {
					pW.print(crew.getCrewLoot());
					System.out.println("---- Saving successful ----");
				} catch(Exception e) {System.out.println("[Error] Failed Backup : " + e);}
				break;
			case 0: // End
				System.out.println("End of the program.");
				return true;
			default:
				System.out.println(INVALID_INPUT_RETRY);
		}
		return false;
	}
}
