package pjr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Jeu.*;

public class CommandesTCP {
	private PrintWriter out;
	private BufferedReader in;
	private String nom;
	private int nbMouche;
	private int idp;
	private int idm;	
	private int idt;
	private int nbMoucheSurTable = 0;
	private ArrayList<Cartes> mains = new ArrayList<Cartes>();
	
	public CommandesTCP(PrintWriter out,BufferedReader in) {
		this.out=out;
		this.in=in;
	}
	
	public boolean rejoindrePartie(long iD) throws IOException {
		out.println("CP-"+nom+"-JR-"+iD);
		out.flush();
		String n=in.readLine().substring(0, 2);
		return n.equals("AC");
	}
	

	public void rentrerTroupeau() {
		out.println("RM-OUI-"+idp+"-"+idm+"-"+idt);
		out.flush();
	}

	public void jouerCarte(Joueur j, int i) {
		Cartes c=j.getMains().get(i);
		out.println("JC-"+c.getCouleur()+"."+c.getChiffre()+"-"+idp+"-"+idm+"-"+idt);
		out.flush();
	}
	
	public void invSensJeu() {
		out.println("ISJ-OUI-"+idp+"-"+idm+"-"+idt);
		out.flush();
	}

	
	//getters & setters
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

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getNbMoucheSurTable() {
		return nbMoucheSurTable;
	}

	public void setNbMoucheSurTable(int nbMoucheSurTable) {
		this.nbMoucheSurTable = nbMoucheSurTable;
	}
	public PrintWriter getOut() {
		return out;
	}
	public void setOut(PrintWriter o) {
		out=o;
	}
	public int getIdp() {
		return idp;
	}

	public void setIdp(int idp) {
		this.idp = idp;
	}

	public int getIdm() {
		return idm;
	}

	public void setIdm(int idm) {
		this.idm = idm;
	}

	public int getIdt() {
		return idt;
	}

	public void setIdt(int idt) {
		this.idt = idt;
	}

}
