package pjr;

import java.net.InetAddress;
import java.util.Scanner;

public class Controleur {
	private static UserConsole ui = new UserConsole();	
	private static int portTCP=65432;
	private static String IP;
	private static String name;
	
	
	public static void main(String[] args) throws Exception {
		IP = InetAddress.getLocalHost().getHostAddress();
		UserConsole.bonjour();
		Scanner scan1 = new Scanner(System.in);
		System.out.println("\t Saisir pseudo");
		setName(scan1.nextLine());
		start();
	}

	public String getName() {
		return name;
	}

	public static void setName(String n) {
		name = n;
	}

	public static void start() throws Exception {
		
		Scanner scan = new Scanner(System.in);
		String commande = ui.lireCommande();
		switch (commande.charAt(0)) {
		case UserConsole.COM_CREER_PARTIE:
			
			String c = "AP-"+IP+"-"+portTCP+"-";
			System.out.println("Entrez le nom : ");
			c += scan.nextLine();
			System.out.println("Entrez le nombre de joueur maximum pour la partie : ");
			c += "-" + scan.nextInt();
			c += "-0-ATTENTE";
			Commandes.annoncerPartie(c);
			
			String sep = "-";
			String mots[] = c.split(sep);
			
			Client tcp=new Client(IP,""+portTCP, name,createID(name));
			new Thread(tcp).start();
			
			while(mots[mots.length-1].equals("ATTENTE")){
			}
			break;
		case UserConsole.COM_RECHERCHER_PARTIE:
			Scanner scan1 = new Scanner(System.in);
			
			String s = "RP";
			System.out.println("Taille maximum de joueur? \n Choisir entre 3,4 ou 5 joueurs");
			s += "-"+scan1.nextLine();
			String reponse=Commandes.rechercherPartie(s);
			System.out.println(reponse);
			
			if(reponse.equals("quitter")) {start();break;}
			
			String sep1 = "-";
			String reponseTCP[] = reponse.split(sep1);
			Client tcp1=new Client(reponseTCP[1],reponseTCP[2],name,createID(name));
			new Thread(tcp1).start();
			break;	

		default:
			ui.sop("La commande spécifiée n'existe pas\n");
			Thread.sleep(100);
			start();

		}

	}
	
	private static long createID(String name) {
		String t = "";
	    int nameLenght = name.length(); // length of the string used for the loop
	    for(int i = 0; i < nameLenght ; i++){   // while counting characters if less than the length add one   
	        char character = name.charAt(i); // start on the first character
	        int ascii = (int) character - 96; //convert the first character
	        t += ascii;
	    }
		long ID = Long.parseLong(t);
		return ID;
	}
}
