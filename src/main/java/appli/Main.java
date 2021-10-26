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
		System.out.print("Entrer le nombre de pirate : ");
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
		System.out.println("\t(1) Ajouter une relation");
		System.out.println("\t(2) Ajouter des préférences");
		System.out.println("\t(0) Fin");
		System.out.print(">>> ");
	}

	public static boolean choixMenu1(Crew crew, Scanner sc, int choix) throws Exception {
		switch(choix) {
			case 1:
				System.out.print("Entrer les lettres des 2 pirates qui s'aiment pas ! (Ex: A B) : ");
				String a = sc.next().toUpperCase();
				String b = sc.next().toUpperCase();
				crew.addRelation(a, b);
				break;
			case 2:
				System.out.print("Entrer les preferences d'un pirate (Ex: A 1 2 3 4) : ");
				String pName = sc.next().toUpperCase();
				Pirate p = crew.getPirate(pName);
				if(p != null) {
					for(int i = 0; i < crew.getNbPirate(); i++) {
						p.addFavLoot(sc.nextInt(), crew.getNbPirate());
					}
				} else {
					System.out.println("Le pirate " + pName + " n'existe pas !\n");
				}
				System.out.println(p);
				break;
			case 0:
				crew.verifCrew();
				System.out.println("Exit !");
				System.out.println("Distribution automatique des tresors !");
				crew.giveLootAuto();
				crew.showCrew(); // DEBUG
				return true; // isEnd = true
			default:
				System.out.println("Invalid Input ! Retry !\n");
		}
		return false;
	}

	public static void textMenu2() {
		System.out.println("Menu 2 :");
		System.out.println("\t(1) Echanger un objet");
		System.out.println("\t(2) Afficher le coût");
		System.out.println("\t(0) Fin");
		System.out.print(">>> ");
	}

	public static boolean choixMenu2(Crew crew, Scanner sc, int choix) throws Exception {
		switch(choix) {
			case 1: // Echange
				System.out.print("Entrer les lettres des 2 pirates doivent echanger leur objet ! (Ex: A B) : ");
				String a = sc.next().toUpperCase();
				String b = sc.next().toUpperCase();
				crew.exchangeLoot(a, b);
				crew.showCrewLoot();
				break;
			case 2: // Cout
				System.out.println(crew.calcultateCost());
				crew.showCrewLoot();
				break;
			case 0: // Fin
				System.out.println("Fin du logiciel.");
				return true;
			default:
				System.out.println("Invalid input !");
		}
		return false;
	}
}
