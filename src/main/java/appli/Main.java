package appli;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	public static final int NB_PIRATE_MAX = 26;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int nbPirate = getNbPirate(sc);
		Crew crew = new Crew(nbPirate);

		menu1(crew, sc);

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

	private static void menu1(Crew crew, Scanner sc) {
		boolean end = false;
		do {
			System.out.println("Menu 1 :");
			System.out.println("\t(1) Ajouter une relation");
			System.out.println("\t(2) Ajouter des préférences");
			System.out.println("\t(0) Fin");
			System.out.print(">>> ");

			try {
				int choix = sc.nextInt();
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
								p.addPref(sc.nextInt(), crew.getNbPirate());
							}
						} else {
							System.out.println("Le pirate " + pName + " n'existe pas !\n");
						}
						System.out.println(p);
						break;
					case 0:
						System.out.println("Exit");
						crew.showCrew(); // DEBUG
						end = true;
						break;
					default:
						System.out.println("Invalid Input ! Retry !\n");
				}
			} catch(InputMismatchException e) {
				System.out.println("I want a integer !\n");
			} catch(Exception e) {
				System.err.println(e.getMessage());
			}
			sc.nextLine();
		} while(!end);
	}
}
