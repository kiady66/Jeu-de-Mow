package Jeu;

import java.io.IOException;

import java.io.PrintWriter;
import java.net.*;
import java.util.HashMap;

public class Serveur {
	private boolean isRunning = true;
	private Partie partie;
	private static int count=65432; 
	private final int port=count++;
	private ServerSocket serverSocket; 
	private static int nbClients=0;

	
	public Serveur(Partie p) {
		partie=p;
		System.out.println("Serveur créer");
	}
	
	public void runServer(){
		try { 
			serverSocket=new ServerSocket(port,5,InetAddress.getLocalHost());
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
		Thread t=new Thread(new Runnable() {
			public void run() {
				while(isRunning==true) {
					try {
						System.out.println("serveur en attente");
						Socket client=serverSocket.accept();
						Runner r=new Runner(client,getPartie());
						new Thread(r).start();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		t.start();
	}

	public static int getNbClients() {
		return nbClients;
	}

	public static void setNbClients(int nbClients) {
		Serveur.nbClients = nbClients;
	}
	
	public Partie getPartie() {
		return partie;
	}
}
