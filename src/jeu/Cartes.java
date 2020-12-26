package Jeu;

import java.awt.Color;

@SuppressWarnings("rawtypes")
public class Cartes implements Comparable {

	// Variable

	private Integer chiffre;
	private int nbMouche;
	private int type;
	private int id;
	private Color couleur;

	// Constructeurs

	public Cartes(int id, int type, Integer chiffre, int nbMouche) {
		
		setId(id);
		setChiffre(chiffre);
		setNbMouche(nbMouche);
		setType(type);

	}

	// Accesseurs

	public Cartes(Color decode, int parseInt) {
		couleur=decode;
		chiffre=parseInt;
	}

	public Integer getChiffre() {
		return chiffre;
	}

	public void setChiffre(Integer chiffre) {
		this.chiffre = chiffre;
	}

	public int getNbMouche() {
		return nbMouche;
	}

	public void setNbMouche(int nbMouche) {
		this.nbMouche = nbMouche;
	}


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	// fonction pour le tri des cartes
	@Override
	public int compareTo(Object s) { // Pour dir au .sort de trier par chiffre
		Cartes c = (Cartes) s;
		if (chiffre.equals(c.chiffre)) {
			return chiffre.compareTo(c.chiffre);
		}
		return chiffre.compareTo(c.chiffre);

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

}
