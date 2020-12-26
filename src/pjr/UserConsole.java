package pjr;

import java.util.Scanner;

public class UserConsole {
	private Scanner scan = new Scanner(System.in);
	
	public static final char COM_CREER_PARTIE = 'c';
	public static final char COM_RECHERCHER_PARTIE = 'r';
	
	public static void bonjour() {
		stop("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		stop("$                                        $");
		stop("$                  MOW                   $");
		stop("$                                        $");
		stop("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
	}

	public String demanderPseudo() {

		stop("Quel est votre pseudo ?");
		String nom = scan.nextLine();
		
		return nom;

	}
	
	public String demanderNomPartie() {

		stop("Quel nom pour la partie ?");
		String nom = scan.nextLine();
		return nom;

	}

	private final static void stop(String s) {
		System.out.println(s);
	}
	
	public String demanderChoix() {
		stop("Choisir : \n \t a. Creer une partie \n \t b. Rechercher une partie");
		String nom = scan.nextLine();
		return nom;
	}
	
	public String lireCommande() {
		sop("\n------- COMMANDES ----------");
		sop("Creer une partie : " + COM_CREER_PARTIE);
		sop("----------------------------");
		sop("Rechercher une partie : " + COM_RECHERCHER_PARTIE);
		sop("----------------------------");
		sop("\nQue voulez-vous faire ?\n");
		String str = scan.nextLine().toLowerCase();
		return str;
	}
	
	public final void sop(String s) {
		System.out.println(s);
	}
}
