package Jeu;

import java.util.ArrayList; 
import java.util.Collections;

public class Partie {

	
	protected static String nom;
	protected static ArrayList<Joueur> listeDeJoueur;
	protected static ArrayList<Manche> listeDeManche;
	protected static ArrayList<Cartes> pioche;
	private int etat; // etat de la partie si il est different de 0 on arrete la partie
	private int NBJM;
	private int NBJC;
	private static int count;
	private final int ID;
	private int numM;
	private String status="ATTENTE";
	
	@SuppressWarnings("static-access")
	public Partie(ArrayList<Joueur> l,String nom) {
		Partie.nom=nom;
		this.ID=count++;
		Partie.count=0;
		listeDeJoueur = l;
		setNumM(0);
		NBJC = 0;
	}
	public Partie(String nom, String nb) {
		Partie.nom=nom;
		this.ID=count++;
		Partie.count=0;
		NBJM=Integer.parseInt(nb);
		setNumM(0);
	}

	public void lancerPartie() {
		setup();
		gestionPartie();
		finDePartie();
	}
	

	public void gestionPartie() {
		
		while (etat == 0) {
			
			for (int i = 1; i <= 50; i++) {
	         
				try {
					setNumM(getNumM()+1);
					testeNbMouche();
					if(etat == 0) {
					pioche.clear();
					creationCartes();
					lancerManche();
				
					}else {
						i = 50;
					}
				} catch (Exception e) {

				}
				
			}
			
			

		}
		
	}
	
	@SuppressWarnings("unused")
	public void testeNbMouche() throws InterruptedException  {
		
		for(int i=0; i<this.getListeDeJoueur().size(); i++) {
			if(listeDeJoueur.get(i).getNbMouche() >= 100) {
				etat = 1;
				
				
		
			}
		}
		
	}

	
	public void setup() {
		etat=0;
		setNumM(0);
		pioche = new ArrayList<Cartes>();
		
	}
	
	public void AjouterCartesPioche(Cartes c) {

		pioche.add(c); // on ajoute une carte a la pioche et on melange
		Collections.shuffle(pioche);
	}

	public void creationCartes() { // insertions des cartes dans la pioche

		AjouterCartesPioche(new Cartes(0, 0, 1, 100 ));
		AjouterCartesPioche(new Cartes(1, 0, 2, 100 ));
		AjouterCartesPioche(new Cartes(2, 0, 3, 100 ));
		AjouterCartesPioche(new Cartes(3, 0, 4, 100 ));
		AjouterCartesPioche(new Cartes(4, 0, 5, 100 ));
		AjouterCartesPioche(new Cartes(5, 0, 6, 100 ));
		AjouterCartesPioche(new Cartes(6, 0, 7, 100 ));
		AjouterCartesPioche(new Cartes(7, 0, 8, 100 ));
		AjouterCartesPioche(new Cartes(8, 0, 9, 0 ));
		AjouterCartesPioche(new Cartes(9, 0, 10, 0 ));
		AjouterCartesPioche(new Cartes(10, 0, 11, 0 ));
		AjouterCartesPioche(new Cartes(11, 0, 12, 0 ));
		AjouterCartesPioche(new Cartes(12, 0, 13, 0 ));
		AjouterCartesPioche(new Cartes(13, 0, 14, 0 ));
		AjouterCartesPioche(new Cartes(14, 0, 15, 0 ));

		AjouterCartesPioche(new Cartes(15, 0, 2, 1 ));
		AjouterCartesPioche(new Cartes(16, 0, 3, 1 ));
		AjouterCartesPioche(new Cartes(17, 0, 4, 1 ));
		AjouterCartesPioche(new Cartes(18, 0, 5, 1 ));
		AjouterCartesPioche(new Cartes(19, 0, 6, 1 ));
		AjouterCartesPioche(new Cartes(20, 0, 7, 1 ));
		AjouterCartesPioche(new Cartes(21, 0, 8, 1 ));
		AjouterCartesPioche(new Cartes(22, 0, 9, 1 ));
		AjouterCartesPioche(new Cartes(23, 0, 10, 1 ));
		AjouterCartesPioche(new Cartes(24, 0, 11, 1 ));
		AjouterCartesPioche(new Cartes(25, 0, 12, 1 ));
		AjouterCartesPioche(new Cartes(26, 0, 13, 1 ));
		AjouterCartesPioche(new Cartes(27, 0, 14, 1 ));

		AjouterCartesPioche(new Cartes(28, 0, 3, 2 ));
		AjouterCartesPioche(new Cartes(29, 0, 4, 2 ));
		AjouterCartesPioche(new Cartes(30, 0, 5, 2 ));
		AjouterCartesPioche(new Cartes(31, 0, 6, 2 ));
		AjouterCartesPioche(new Cartes(32, 0, 7, 2 ));
		AjouterCartesPioche(new Cartes(33, 0, 8, 2 ));
		AjouterCartesPioche(new Cartes(34, 0, 9, 2 ));
		AjouterCartesPioche(new Cartes(35, 0, 10, 2 ));
		AjouterCartesPioche(new Cartes(36, 0, 11, 2 ));
		AjouterCartesPioche(new Cartes(37, 0, 12, 2 ));
		AjouterCartesPioche(new Cartes(38, 0, 13, 2 ));

		AjouterCartesPioche(new Cartes(39, 0, 7, 3 ));
		AjouterCartesPioche(new Cartes(40, 0, 8, 3 ));
		AjouterCartesPioche(new Cartes(41, 0, 9, 3 ));

		
		
		AjouterCartesPioche(new CarteSpe(42, 1, 16, 5, "Max" )); //cette carte est une carte speciale mais usit la logique d une carte
		AjouterCartesPioche(new CarteSpe(43, 1, 0, 5, "Min" ));  //cette carte est une carte speciale mais usit la logique d une carte
		AjouterCartesPioche(new CarteSpe(44, 1, 7, 5 , "Acrobate"));
		AjouterCartesPioche(new CarteSpe(45, 1, 9, 5 , "Acrobate"));
		AjouterCartesPioche(new CarteSpe(46, 1, 17, 5 , "Entre"));

	}

	public void lancerManche() throws Exception {

		listeDeManche.add(new Manche(pioche, listeDeJoueur,true,getNumM()));
		
	}

	public void finDePartie() {
			clearPartie();
			System.out.println("fin de la partie !!!");
	
	}
	
	public void clearPartie() {
		
		for(int i=0; i<listeDeJoueur.size(); i++) {
			
			listeDeJoueur.get(i).setNbMouche(0);
			
		}
		
		listeDeManche = new ArrayList<Manche>();
		
		
		
	}

	// Accesseur

	public ArrayList<Joueur> getListeDeJoueur() {
		return listeDeJoueur;
	}

	public void setListeDeJoueur(ArrayList<Joueur> listeDeJoueur) {
		Partie.listeDeJoueur = listeDeJoueur;
	}
	public void setListeDeJoueur(Joueur j) {
		Partie.listeDeJoueur.add(j);
	}
	public ArrayList<Manche> getListeDeManche() {
		return listeDeManche;
	}

	public void setListeDeManche(ArrayList<Manche> listeDeManche) {
		Partie.listeDeManche = listeDeManche;
	}

	public int getEtat() {
		return etat;
	}

	@SuppressWarnings("static-access")
	public void setEtat(int etat) {
		this.etat = etat;
	}

	public String getNom() {
		return nom;
	}

	public int getNBJM() {
		return NBJM;
	}

	public void setNBJM(int nBJM) {
		NBJM = nBJM;
	}

	public int getNBJC() {
		return NBJC;
	}

	public void setNBJC(int nBJC) {
		NBJC = nBJC;
	}

	public int getID() {
		return ID;
	}


	public int getNumM() {
		return numM;
	}

	public void setNumM(int numM) {
		this.numM = numM;
	}
	public Object getStatus() {
		return status;
	}
	public void setNBJC() {
		NBJC = NBJC + 1;
		
	}

}
