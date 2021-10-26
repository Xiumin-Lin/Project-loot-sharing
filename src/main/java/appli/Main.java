package appli;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	public static final int NB_PIRATE_MAX = 26;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int nbPirate = getNbPirate(sc);
		Crew crew = new Crew(nbPirate);

		menu(crew, sc, true);
		menu(crew, sc, false);

		sc.close();
	}

	private static int getNbPirate(Scanner sc) {
		System.out.print("Enter the number of pirates : ");
		int n;
		do {
			n = sc.nextInt();
		} while(n <= 0 || n > NB_PIRATE_MAX);
		return n;
	}

	private static void menu(Crew crew, Scanner sc, boolean isMenu1) {
		boolean isEnd = false;
		do {
			if(isMenu1) textMenu1();
			else textMenu2();
			try {
				int choix = sc.nextInt();
				isEnd = (isMenu1) ? choixMenu1(crew, sc, choix) : choixMenu2(crew, sc, choix);
			} catch(InputMismatchException e) {
				System.out.println("I want a integer !\n");
			} catch(Exception e) {
				System.err.println(e.getMessage());
			}
			sc.nextLine();
		} while(!isEnd);
	}

	public static void textMenu1() {
		System.out.println("Menu 1 :");
		System.out.println("\t(1) Add a relationship");
		System.out.println("\t(2) Adding preferences");
		System.out.println("\t(0) End");
		System.out.print(">>> ");
	}

	public static boolean choixMenu1(Crew crew, Scanner sc, int choix) throws Exception {
		switch(choix) {
			case 1:
				System.out.print("Enter the letters of the 2 pirates who dislike each other ! (Ex: A B) : ");
				String a = sc.next().toUpperCase();
				String b = sc.next().toUpperCase();
				crew.addRelation(a, b);
				break;
			case 2:
				System.out.print("Enter the preferences of a pirate (Ex: A 1 2 3 4) : ");
				String pName = sc.next().toUpperCase();
				Pirate p = crew.getPirate(pName);
				if(p != null) {
					for(int i = 0; i < crew.getNbPirate(); i++) {
						p.addFavLoot(sc.nextInt(), crew.getNbPirate());
					}
				} else {
					System.out.println("Pirate " + pName + " doesn't exist !\n");
				}
				System.out.println(p);
				break;
			case 0:
				crew.allPirateFavListIsComplete();
				System.out.println("Exit !");
				System.out.println("Automatic Loot Attribution...");
				crew.autoLootAttribution();
				crew.showCrew(); // DEBUG
				return true; // isEnd = true
			default:
				System.out.println("Invalid Input ! Retry !\n");
		}
		return false;
	}

	public static void textMenu2() {
		System.out.println("Menu 2 :");
		System.out.println("\t(1) Exchanging an object");
		System.out.println("\t(2) Show cost");
		System.out.println("\t(0) End");
		System.out.print(">>> ");
	}

	public static boolean choixMenu2(Crew crew, Scanner sc, int choix) throws Exception {
		switch(choix) {
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
				System.out.println("End of the programme.");
				return true;
			default:
				System.out.println("Invalid input !");
		}
		return false;
	}
}
