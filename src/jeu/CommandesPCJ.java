package Jeu;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class CommandesPCJ{
	private static HashMap<Long,PrintWriter> joueurs=new HashMap<Long,PrintWriter>();
	private static Partie game;
	private static ArrayList<Joueur> l=new ArrayList<Joueur>();
	
	public CommandesPCJ(Partie p) {
		game = p;
	}
	
	
	public void accepterJoueur(PrintWriter out,String s,Joueur j) {
		if(game.getNBJM()>game.getNBJC()) {
			l.add(j);
			game.setListeDeJoueur(l);
			game.setNBJC();
			addJoueur(j.getId(), out);
			out.println("AC-"+s);
			out.flush();
		}else {
			out.println("RC-"+s);
			out.flush();
		}
		Launcher.ajouterJoueur(game.getNom());
	}
	
	public void initPartie(){
		String str="IP-"+game.getListeDeJoueur().get(0).getNom();
		for(int i=1;i<game.getListeDeJoueur().size();i++) {
			str+=","+game.getListeDeJoueur().get(i).getNom();
		}
		sendAll(str+"-"+game.getID());
		game.lancerPartie();
	}

	public static void initManche() {
		if(game.getListeDeManche().get(game.getNumM()).getSens()==true) {
			sendAll("IM-"+"CROI"+"-"+game.getID()+"-"+game.getNumM());
		} else {
			sendAll("IM-"+"DECROI"+"-"+game.getID()+"-"+game.getNumM());
		}
	}
	
	public static void distibuerCarte(Joueur j){
		String str = "DM-"+j.getMains().get(0).getCouleur()+"."+j.getMains().get(0).getChiffre();
			for(int i=1;i<j.getMains().size();i++) {
				Cartes c=j.getMains().get(i);
				str+=","+c.getCouleur()+"."+c.getChiffre();
			}
			joueurs.get(j.getId()).println(str+"-"+game.getID()+"-"+game.getNumM());
	}
	
	public static void initTour(Joueur j) {
		String str="IT-";
		if(game.getListeDeManche().get(game.getNumM()).getNbTour()<=50) {
			str+=j.getNom()+"-"+Partie.pioche.size()+"-"+game.getID()+"-"+game.getNumM()+"-"+game.getListeDeManche().get(game.getNumM()).getNbTour();
			sendAll(str);
		}else{
			finManche();
		}
	}
	public void deconnexion() {
		for(PrintWriter out:joueurs.values()) {
			out.println("DJ-"+game.getID()+"-"+game.getNumM()+"-"+game.getListeDeManche().get(game.getListeDeManche().size()-1).getNbTour());
			out.flush();
		}
	}
	public void sendCartePiochee(PrintWriter out) {
		String str="FC-";
		game.getListeDeManche().get(game.getNumM()).donnerCarteDepuisPioche(chercherKey(out));
		str+=chercherKey(out).getMains().get(0).getCouleur()+"."+chercherKey(out).getMains().get(0).getChiffre()+"-";
		out.println(str+game.getID()+"-"+game.getNumM()+"-"+game.getListeDeManche().get(game.getNumM()).getNbTour());
		out.flush();
	}
	public void erreur(PrintWriter out,String erreur) {
		out.println("IE-"+erreur+"-"+game.getID()+"-"+game.getNumM()+"-"+game.getListeDeManche().get(game.getListeDeManche().size()-1).getNbTour());
		out.flush();		
	}
	public static void infos(int i) {
		if(i==0) {
			sendAll("IAT-SJI"+game.getID()+"-"+game.getNumM()+"-"+game.getListeDeManche().get(game.getNumM()).getNbTour());
		}else if(i==1) {
			sendAll("IAT-TR"+game.getID()+"-"+game.getNumM()+"-"+game.getListeDeManche().get(game.getNumM()).getNbTour());		
		}
	}
	public static void infos(Cartes c) {
		sendAll("IAT-"+c.getCouleur()+"."+c.getChiffre()+"-"+game.getID()+"-"+game.getNumM()+"-"+game.getListeDeManche().get(game.getNumM()).getNbTour());
	}
	public static void finManche() {
		String str="FM-"+game.getListeDeJoueur().get(0);
		for(int i=1;i<game.getListeDeJoueur().size();i++) {
			str+=","+game.getListeDeJoueur().get(i).getNbMouche();
		}
		sendAll(str+"-"+game.getID()+"-"+game.getNumM());
	}
	public void finPartie() {
		int score=game.getListeDeJoueur().get(0).getNbMouche();
		Joueur gagnant=new Joueur("test");
		String str="FP-";
		for(Joueur j:game.getListeDeJoueur()) {
			if(score>j.getNbMouche()) {
				score=j.getNbMouche();
				gagnant=j;
			}
		}
		sendAll(str+gagnant.getNom()+"-"+game.getID());
	}
	public static void sendAll(String msg) {
	    for (PrintWriter p:joueurs.values()) {
	    	p.println(msg);
	    	p.flush();
	    }
	}
	public String toString() {
		return joueurs.toString();
	}
	public void setJoueurs(HashMap<Long,PrintWriter> j) {
		joueurs=j;
		for(PrintWriter p:joueurs.values()) {
			Serveur.setNbClients(Serveur.getNbClients() + 1);
		}
	}
	public void setJoueur(long m,PrintWriter out) {
		joueurs.put(m, out);
		Serveur.setNbClients(Serveur.getNbClients() + 1);
	}


	public Partie getGame() {
		return game;
	}
	
	public Joueur chercherKey(PrintWriter out) {
		long x=-1;
		for(Long i:joueurs.keySet()) {
			if(joueurs.get(i)==out) {
				x=i;
			}
		}
		Joueur j2=new Joueur();
		for(Joueur j:game.getListeDeJoueur()) {
			if(j.getId()==x) {
				j2=j;
			}
		}
		return j2;
	}

	public void setGame(Partie g) {
		game = g;
	}

	
	public void addJoueur(Long n,PrintWriter out) {
		joueurs.put(n, out);
		Serveur.setNbClients(Serveur.getNbClients() + 1);
	}
}
