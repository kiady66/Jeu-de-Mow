package Jeu;

import java.util.ArrayList;

public class GestionnairePartie {
	public static ArrayList<Partie> listeparties = new ArrayList<Partie>();
	public static ArrayList<String> listeProtocoles = new ArrayList<String>();

	public ArrayList<String> getListeProtocoles() {
		return listeProtocoles;
	}

	public ArrayList<Partie> getListeparties() {
		return listeparties;
	}
	
	public String getNomP(int i) {
		String sep = "-";
		String mots[] = listeProtocoles.get(i).split(sep);
		return mots[3];
	}

	public void setListeProtocoles(ArrayList<String> p) {
		listeProtocoles = p;		
	}

}
