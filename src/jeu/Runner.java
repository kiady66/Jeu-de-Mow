package Jeu;

import java.net.Socket;
import java.io.*;

public class Runner implements Runnable{
	protected Socket clientSocket=null;
	private String nomClient;
	private BufferedReader in;
	private PrintWriter out;
	private Partie p;
	private CommandesPCJ com;
	
	public Runner(Socket s,Partie p) {
		clientSocket=s;
		this.p=p;
		com=new CommandesPCJ(p);
	}
	
	public void run() {
		try {
			in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out=new PrintWriter(clientSocket.getOutputStream(),true);
			String s=in.readLine();
			String sep = "-";
			String mots[] = s.split(sep);
			switch(mots[0]) {
				case "CP":
					System.out.println("\n**********Case cp**********");
					System.out.println(s);
					setNomClient(mots[1]);
					Joueur j=new Joueur(nomClient, Long.parseLong(mots[3]));
					com.accepterJoueur(out, mots[3], j);
					System.out.println("********************\n");
			}
			
			while(p.getStatus().equals("ATTENTE")) {
				
			}
			com.initPartie();
			}catch(IOException e) {
				com.deconnexion();
				e.printStackTrace();
		}
	}

	public CommandesPCJ getCom() {
		return com;
	}

	public void setCom(CommandesPCJ com) {
		this.com = com;
	}

	public String getNomClient() {
		return nomClient;
	}

	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}
	
	public void distribuerCarte(){
		int i=0;
		while(i<p.getListeDeManche().size()) {
			i++;
		}
	}

	
	
	

}
