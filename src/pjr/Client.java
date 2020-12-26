package pjr;

import java.awt.Color;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import Jeu.*;

public class Client extends Joueur implements Runnable{
	private String IP;
	private int port;
	private int jouer=0;
	private boolean sens=true;
	private Scanner sc=new Scanner(System.in);
	ArrayList<Joueur> ennemies=new ArrayList<Joueur>();
	
	public Client(String IP, String port,String nom,long n) {
		super(nom,n);
		this.port=Integer.parseInt(port);
		this.IP=IP;
	}
	
	public void run() {		
		try {
			Socket clientSocket=new Socket(InetAddress.getByName(IP),port);
			PrintWriter serveurOut=new PrintWriter(clientSocket.getOutputStream(),true);
			InputStreamReader ir=new InputStreamReader(clientSocket.getInputStream());
			BufferedReader serveurIn=new BufferedReader(ir);
			CommandesTCP c=new CommandesTCP(serveurOut,serveurIn);
			//Join game
			c.setNom(this.getNom()); 
			checkAccept(c.rejoindrePartie(this.getId()));
			//game
			System.out.println("---------------");
			System.out.println(serveurIn.readLine());
			String str=serveurIn.readLine();
			String sep="-";
			String tab[]=str.split(sep);
			switch(tab[0]) { 
			case "DM": 
					String s="."; 
					String tab2[]=tab[1].split(s); 
					for(int i=0;i<tab.length;i+=2) { 
						this.getMains().add(new Cartes(Color.decode(tab2[i]),Integer.parseInt(tab2[i+1]))); 
						} 
					break; 
					case "IP": 
						String s1=","; 
						String j[]=tab[1].split(s1); 
						for(int i=0;i<tab.length;i++) { 
							ennemies.add(new Joueur(tab[i])); 
							} 
						break; 
					case "IM": 
						c.setIdp(Integer.parseInt(tab[2])); 
						c.setIdm(Integer.parseInt(tab[3])); 
						break; 
					case "IT": 
						c.setIdt(Integer.parseInt(tab[5])); 
						break; 
					case "DJ": 
						System.out.println(str); 
						break; 
					case "FC": 
						this.getMains().add(new Cartes(Color.decode(tab[1]),Integer.parseInt(tab[2]))); 
						break; 
					case "IE": 
						System.out.println(str); 
						break; 
					case "IAT": 
						if(tab[1].equals("SJI")) { 
							System.out.println("Changement de sens du jeu"); 
							if(sens==true) { 
								sens=false; 
								}else { 
									sens=true; 
									} 
							}else if(tab[1].equals("TR")) { 
								System.out.println("Troupeau récupéré"); 
								} else { 
									System.out.println("Carte "+tab[1]+" jouée"); 
									} 
						if(sens==true) {
							jouer++;
							} else {
								jouer--;
								} if(this.getId()==(ennemies.get(jouer).getId()))jouerSonTour(c); 
								break; 
						case "FP": 
							System.out.println("Fin du jeu"); 
							System.out.println("Le gagnant est : "+tab[1]); 
							break; 
						case "FM": 
							System.out.println("Manche terminée"); 
							System.out.println(str);
						default: System.out.println("wrong"); 
							break; 
					 
			
			}
			
			
			serveurIn.close();
			ir.close();
			serveurOut.close();
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		sc.close();
	}
	
	public void checkAccept(boolean res) {
		if(res==false) {
			try {
				System.out.println(res);
				Controleur.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void jouerSonTour(CommandesTCP c) { 
		System.out.println(this.getNom()+" que voulez-vous faire ?"); 
		System.out.println("1-jouer carte"); 
		System.out.println("2-ramasser troupeau"); 
		switch(sc.nextInt()) { 
			case 1: 
				System.out.println("Choisir une carte"); 
				for(int i=0;i<this.getMains().size();i++) { System.out.println(this.getMains().get(i)); } 
				c.jouerCarte(this,sc.nextInt()); 
				break; 
			case 2: 
				c.rentrerTroupeau(); 
				break; 
			default: 
				System.out.println("Mauvaise réponse"); 
				jouerSonTour(c); 
				break; }
	}
}
