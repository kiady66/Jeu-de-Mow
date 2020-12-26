package Jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Manche {

	// Variables

	private static ArrayList<Cartes> jeuEnCours;
	private int compteur;
	private int tours;
	private static int nbMoucheSurTable;
	private boolean sens;
	private int etat;
	private int numManche;
	private Scanner scan = new Scanner(System.in);
	

	// Constructeur

	public Manche(ArrayList<Cartes> pioche, ArrayList<Joueur> l, boolean sens, int manche) {
		CommandesPCJ.initManche();
		setNumManche(manche);
		this.tours = 1;
		Manche.jeuEnCours = new ArrayList<Cartes>();
		this.compteur = (Partie.listeDeJoueur.size() - 1);
		Manche.nbMoucheSurTable = 0;
		this.etat = 0;
		System.out.println("\n===================================================");
		System.out.println("============= Debut de la manche n°" + getNumManche() + " ==============");
		System.out.println("===================================================\n");
		setSens(sens);

		System.out.println("\nPioche\n");
		for (int i = 0; i < Partie.pioche.size(); i++) // on regarde la pioche
			System.out.println(Partie.pioche.get(i).getChiffre());

		System.out.println("\nMain\n");
		donnerCarte(Partie.listeDeJoueur.size());

		for (int p = 0; p < Partie.listeDeJoueur.size(); p++) {
			System.out.println("la main de " + Partie.listeDeJoueur.get(p).getNom() + " est : \n");
			for (int i = 0; i < Partie.listeDeJoueur.get(p).getMains().size(); i++) // On regarde la main du joueur
				System.out.println(Partie.listeDeJoueur.get(p).getMains().get(i).getChiffre() + "\n");

		}

		try {
			proposerDeJouer();

		} catch (Exception e) {

		}

		if (etat == 1)
			
			clearManche();
			
			return;

	}

	// Fonctions
	
	
	public void clearManche() {
		jeuEnCours.clear();
		setNbMoucheSurTable(0);
	}
	

	public Cartes selectionnerCarte(Joueur j) throws Exception {
		System.out.println("main de " + j.getNom());
		for (int i = 0; i < j.getMains().size(); i++) {
			if (j.getMains().get(i).getType() == 0) {
				System.out.println("Carte : " + i + "        | Valeur : " + j.getMains().get(i).getChiffre()
						+ "        | Mouches : " + j.getMains().get(i).getNbMouche());
			} else {
				System.out.println("Carte : " + i + "        | Valeur : " + j.getMains().get(i).getChiffre()
						+ "        | Mouches : " + j.getMains().get(i).getNbMouche() + "        | Effet : "
						+ ((CarteSpe) j.getMains().get(i)).getEffet());
			}
		}
		System.out.println("Selectionnez une carte : \n");
		String selection;
		selection = scan.nextLine();
		if (selection.equals("troupeau")) {
			CommandesPCJ.infos(1);
			ramasserTroupeau(j);
			Cartes c = new Cartes(jeuEnCours.get(0).getId() + 10000, 2, 10, 0); // on fais ca pour le 2 de la carte car
																				// elle permet de continuer
			// de jouer qui va avoir l image de la derniere carte du jeu
			if (etat == 0) // pour simuler une pile de carte
				return c;
			else
				return null;
		}else 
		
			if (selection.equals("score")) {
				
				afficherScore();
				proposerDeRejouer(j);
				Cartes c = new Cartes(3,3,3,3);
				return c;
			}else {
				
			int p = Integer.parseInt(selection);
			System.out.println("\n");
			// checker si la carte est ok
			
			return j.getMains().get(p);
		}
		
	}

	public void compteurTour() {
		compteur++;
		if (compteur == Partie.listeDeJoueur.size()) {
			System.out.println("------------ tour " + tours + " ---------------\n");
			compteur = 0;
			tours++;
		} else {
		}
	}

	public void proposerDeJouer() throws Exception {

		while (etat == 0) {

			for (int i = 0; i < Partie.listeDeJoueur.size();) {

				compteurTour();
				CommandesPCJ.initTour(Partie.listeDeJoueur.get(i));
				AjouterCartesPlateau(selectionnerCarte(Partie.listeDeJoueur.get(i)), Partie.listeDeJoueur.get(i));
				System.out.println("cartes sur le plateau\n");

				if (this.sens == true) {

					System.out.println("+1\n");
					i++;

				}
				if (this.sens == false) {

					System.out.println("-1\n");
					if (i != 0) {

						i--;

					} else {

						i = Partie.listeDeJoueur.size() - 1;

					}
					// sens inverse

				}
				for (int j = 0; j < getJeuEnCours().size(); j++) // on regarde les cartes sur table
					System.out.println(getJeuEnCours().get(j).getChiffre() + "\n");
			}

		}

		// for (int i = Partie.listeDeJoueur.size() - 1; i >= 0; i--) ;

	}

	public void proposerDeRejouer(Joueur p) throws Exception {

		AjouterCartesPlateau(selectionnerCarte(p), p);

	}

	public void donnerCarte(int size) {

		int h = 0;
		while (h < 5) {
			for (int i = 0; i < Partie.listeDeJoueur.size(); i++) {
				if (Partie.listeDeJoueur.get(i).getMains().size() == 5) {
					// si ils ont 5 cartes on en donne pas
				} else {
					
					donnerCarteDepuisPioche(Partie.listeDeJoueur.get(i)); // On donne les carte au joueur
				}
			}
			h++;
		}

	}

	public void donnerCarteDepuisPioche(Joueur j) {
		CommandesPCJ.distibuerCarte(j);
		if (Partie.pioche.isEmpty()) {
			System.out.println("pioche vide");
		} else {

			Cartes c = Partie.pioche.get(0); // on prend la premiere carte (mélangé) de la pioche que l
			Partie.pioche.remove(0); // on enleve cette carte de la pioche
			j.getMains().add(0, c); // on donne cette carte au joueur
			System.out.println("la carte : " + c.getChiffre() + " a ete donnée au joueur : " + j.getNom() + "\n"); // pour
																													// tester
		} // la
			// carte
			// donnée

	}

	@SuppressWarnings("static-access")
	public void ramasserTroupeau(Joueur j) throws InterruptedException {
		j.setNbMouche(this.getNbMoucheSurTable() + j.getNbMouche());
		System.out.println("nombre de mouche : " + j.getNbMouche());
		afficherScore();

		if (Partie.pioche.isEmpty()) { // si la pioche est vide fin de la manche
			for (int i = 0; i < Partie.listeDeJoueur.size(); i++) // on reinitialise les mains
				Partie.listeDeJoueur.get(i).getMains().clear();
				jeuEnCours.clear();
			
				this.etat = 1;

		} else {

		}

		Manche.nbMoucheSurTable = 0;

		for (int i = 0; i < Partie.listeDeJoueur.size(); i++) {
			if (Partie.listeDeJoueur.get(i).getNbMouche() >= 100) {
				etat = 1;

			}
		}

	}

	public Joueur returnJoueur() {
		Joueur j = new Joueur("r", 1);
		j.setNbMouche(0);
		for (int i = 0; i < Partie.listeDeJoueur.size(); i++) {

			if (Partie.listeDeJoueur.get(i).getNbMouche() > j.getNbMouche())
				j = Partie.listeDeJoueur.get(i);

		}
		return j;

	}

	public void recapNbMouche() throws InterruptedException {

		for (int i = 0; i < Partie.listeDeJoueur.size(); i++) {

			System.out.println("A la fin de la manche " + getNumManche() + " le joueur "
					+ Partie.listeDeJoueur.get(i).getNom() + " a " + Partie.listeDeJoueur.get(i).getNbMouche()
					+ " mouches.\n");

		}
		Thread.sleep(400);
	}
	
	public void afficherScore() throws InterruptedException {
		
		for(int i=0; i<Partie.listeDeJoueur.size(); i++) {
			
			System.out.println("Le joueur "+Partie.listeDeJoueur.get(i).getNom()+" a "+Partie.listeDeJoueur.get(i).getNbMouche()+" mouches.\n");
			
		}
		Thread.sleep(3000);
		
	}
	
	
	
	@SuppressWarnings({ "unchecked" })
	public void AjouterCartesPlateau(Cartes c, Joueur j) throws Exception {

		switch (c.getType()) {
		
		case 3:
			//quand on ne veux auccune action
			break;
		

		case 2:

			getJeuEnCours().clear();
			proposerDeRejouer(j);

			break;

		case 1:

			if (((CarteSpe) c).getEffet().equals("Acrobate")) {

				int check = 0;

				if (getJeuEnCours().isEmpty()) {
					System.out.println(j.getNom() + " carte non valide : " + c.getChiffre() + "\n");
					proposerDeRejouer(j);

				}

				for (int i = 0; i < getJeuEnCours().size(); i++) {

					if (getJeuEnCours().get(i).getChiffre() == (c.getChiffre())) {
						System.out.println("le joueur " + j.getNom() + " a joue la carte " + c.getChiffre() + "\n");
						j.getMains().remove((CarteSpe) c);
						
						switch(getJeuEnCours().get(i).getChiffre()) {
						case 7:
							
							if(getJeuEnCours().get(i).getNbMouche() == 0)
						getJeuEnCours().get(i).setId(10001);
							if(getJeuEnCours().get(i).getNbMouche() == 1)
						getJeuEnCours().get(i).setId(10002);
							if(getJeuEnCours().get(i).getNbMouche() == 2)
						getJeuEnCours().get(i).setId(10003);
							if(getJeuEnCours().get(i).getNbMouche() == 3)
						getJeuEnCours().get(i).setId(10004);
						
						
						break;
						
						case 9:
							
							if(getJeuEnCours().get(i).getNbMouche() == 0)
						getJeuEnCours().get(i).setId(20001);
							if(getJeuEnCours().get(i).getNbMouche() == 1)
						getJeuEnCours().get(i).setId(20002);
							if(getJeuEnCours().get(i).getNbMouche() == 2)
						getJeuEnCours().get(i).setId(20003);
							if(getJeuEnCours().get(i).getNbMouche() == 3)
						getJeuEnCours().get(i).setId(20004);
			
						
						break;
						}
						check = 1;
						

						if (Partie.pioche.isEmpty()) {
							System.out.println("la pioche est vide");
						} else {

							donnerCarteDepuisPioche(j);
						}

					} else {

						System.out.println(j.getNom() + " carte non valide : " + c.getChiffre() + "\n");

					}

				}

				if (check == 0) {

					proposerDeRejouer(j);

				}

			}
			if (((CarteSpe) c).getEffet().equals("Entre")) {
				int h=1;
				if (Manche.jeuEnCours.size() < 2) {

					System.out.println(j.getNom() + " carte non valide : " + c.getChiffre() + "\n");
					proposerDeRejouer(j);

				} else {
					for(int o = 0 ; o<Manche.jeuEnCours.size()-1; o++) {
						if(h==1) {
				
					if (Manche.jeuEnCours.get(o+1).getChiffre() - Manche.jeuEnCours.get(o).getChiffre() != 2) {
						
						System.out.println(j.getNom() + " carte non valide : " + c.getChiffre() + "\n");
						
					}else {
						Cartes m = new Cartes(20, 0,Manche.jeuEnCours.get(o+1).getChiffre()-1, c.getNbMouche());
						j.getMains().remove((CarteSpe) c);
						Manche.jeuEnCours.add(m);
						h++;
						if (Partie.pioche.isEmpty()) {

							System.out.println("la pioche est vide");

						} else {

							donnerCarteDepuisPioche(j);

						}
						}
					}
					}
				}
				if(h == 1) {
					proposerDeRejouer(j);
				}
			}

			if (((CarteSpe) c).getEffet().equals("Min")) {

				System.out.println("le joueur " + j.getNom() + " a joue la carte " + c.getChiffre() + "\n");
				Cartes m = new Cartes(c.getId(), 0, c.getChiffre(), c.getNbMouche());
				getJeuEnCours().add(m);
				this.setNbMoucheSurTable(getNbMoucheSurTable() + m.getNbMouche());

				j.getMains().remove(((CarteSpe) c));

				if (Partie.pioche.isEmpty()) {

					System.out.println("la pioche est vide");

				} else {

					donnerCarteDepuisPioche(j);

				}

			}

			if (((CarteSpe) c).getEffet().equals("Max")) {

				System.out.println("le joueur " + j.getNom() + " a joue la carte " + c.getChiffre() + "\n");
				Cartes m = new Cartes(c.getId(), 0, c.getChiffre(), c.getNbMouche());
				getJeuEnCours().add(m);
				this.setNbMoucheSurTable(getNbMoucheSurTable() + m.getNbMouche());

				j.getMains().remove(((CarteSpe) c));

				if (Partie.pioche.isEmpty()) {

					System.out.println("la pioche est vide");

				} else {

					donnerCarteDepuisPioche(j);

				}

			}
			Scanner sc=new Scanner(System.in);
			System.out.println("Voulez-vous changer de sens ?");
			if(sc.nextLine().equals("oui")) {
				CommandesPCJ.infos(0);
				if (this.sens == true) {
					this.sens = false;
				} else if (this.sens == false) {
					this.sens = true;
				}
			}
			
			break;

		case 0:

			if (getJeuEnCours().isEmpty()) { // si le plateau est vide, on place la premiere carte sans contraintes

				System.out.println("le joueur " + j.getNom() + " a joue la carte " + c.getChiffre() + "\n");
				getJeuEnCours().add(c);
				this.setNbMoucheSurTable(getNbMoucheSurTable() + c.getNbMouche()); // on affecte au nombre de mouches de
																					// la manche le nombre de mouches de
																					// la carte on supprime la carte de
																					// la mains
				j.getMains().remove(c);

				if (Partie.pioche.isEmpty()) {

					System.out.println("la pioche est vide");

				} else {

					donnerCarteDepuisPioche(j);

				}

			} else if (getJeuEnCours().get(0).getChiffre() > c.getChiffre()) { // si la carte jouée est plus petit que
																				// la carte la plus a gauche du jeu(soit
																				// la plus petite) alors on la place
																				// dans le jeu

				System.out.println("le joueur " + j.getNom() + " a joue la carte " + c.getChiffre() + "\n");
				getJeuEnCours().add(c);
				this.setNbMoucheSurTable(getNbMoucheSurTable() + c.getNbMouche());
				j.getMains().remove(c);

				if (Partie.pioche.isEmpty()) {
					System.out.println("la pioche est vide"); // si la pioche est vide on ne donne pas de carte au
																// joueur

				} else {

					donnerCarteDepuisPioche(j);

				}

			} else if (getJeuEnCours().get(getJeuEnCours().size() - 1).getChiffre() < c.getChiffre()) { // si la carte
																										// est plus
																										// grande

				System.out.println("le joueur " + j.getNom() + " a joue la carte " + c.getChiffre() + "\n");
				getJeuEnCours().add(c);
				this.setNbMoucheSurTable(getNbMoucheSurTable() + c.getNbMouche());
				j.getMains().remove(c);

				if (Partie.pioche.isEmpty()) {

					System.out.println("la pioche est vide");

				} else {

					donnerCarteDepuisPioche(j);

				}

			} else {

				System.out.println(j.getNom() + " carte non valide : ok " + c.getChiffre() + "\n");
				proposerDeRejouer(j);

			}

			break;
		}

		Collections.sort(getJeuEnCours());

}

	// Accesseurs

	public boolean getSens() {
		return sens;
	}

	public void setSens(boolean sens) {
		this.sens = sens;
	}

	public static ArrayList<Cartes> getJeuEnCours() {
		return jeuEnCours;
	}

	@SuppressWarnings("static-access")
	public void setJeuEnCours(ArrayList<Cartes> jeuEnCours) {
		this.jeuEnCours = jeuEnCours;
	}

	public static int getNbMoucheSurTable() {
		return nbMoucheSurTable;
	}

	@SuppressWarnings("static-access")
	public void setNbMoucheSurTable(int nbMoucheSurTable) {
		this.nbMoucheSurTable = nbMoucheSurTable;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public int getNbTour() {
		return tours;
	}

	public int getNumManche() {
		return numManche;
	}

	public void setNumManche(int numManche) {
		this.numManche = numManche;
	}
}
