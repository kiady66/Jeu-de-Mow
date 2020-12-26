package Jeu;

import java.util.ArrayList;

public class Joueur {

	// variables + tableau

	private ArrayList<Cartes> mains = new ArrayList<Cartes>();
	private String nom;
	private long id;
	private int nbMouche;
	private int score=0;

	// Constructor

	public Joueur(String nom) {

		setNom(nom);
		setNbMouche(0);

	}
	public Joueur() {
	}

	public Joueur(String string, long i) {
		setNom(string);
		setNbMouche(0);
		id=i;
	}
	
	
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public long getId() {
		return id;
	}

	public ArrayList<Cartes> getMains() {
		return mains;
	}

	public void setMains(ArrayList<Cartes> mains) {
		this.mains = mains;
	}

	public int getNbMouche() {
		return nbMouche;
	}

	public void setNbMouche(int nbMouche) {
		this.nbMouche = nbMouche;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

}
