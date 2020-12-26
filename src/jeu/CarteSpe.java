package Jeu;

public class CarteSpe extends Cartes {

	// Variables

	private String effet;
	private String item;

	// Constructeur

	public CarteSpe(int id, int type, Integer chiffre, int nbMouche, String effet) {

		super(id,type,chiffre, nbMouche);
		setEffet(effet);
		setItem(item);

	}


	// Accesseurs

	public String getEffet() {
		return effet;
	}

	public void setEffet(String effet) {
		this.effet = effet;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	// fonction pour le tri des cartes
	@Override
	public int compareTo(Object s) { // Pour dir au .sort de trier par chiffre
		CarteSpe c = (CarteSpe) s;
		if (getChiffre().equals(c.getChiffre())) {
			return getChiffre().compareTo(c.getChiffre());
		}
		return getChiffre().compareTo(c.getChiffre());

	}

}
