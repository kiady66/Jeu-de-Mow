package Jeu;

import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

import pjr.*;

@SuppressWarnings("serial")
public class Launcher implements Serializable {
	public static GestionnairePartie gp=new GestionnairePartie();

	public static void main(String[] args) throws Exception {
		int port = 7779;
		String sep = "-";
		String IP = "224.7.7.7";
			try {
				byte[] data=new byte[256];
				InetAddress ia=InetAddress.getByName(IP);
				MulticastSocket ms=new MulticastSocket(port);
				ms.joinGroup(ia);
				DatagramPacket dp=new DatagramPacket(data,data.length);
				while(true) {
					ms.receive(dp);
					String s=new String(dp.getData(),0,dp.getLength());
					String mots2[] = s.split(sep);
					switch(mots2[0]) {	
					//////////////////////////////////////////////////////////////////////////
						case "AP":					
							if(ms.getRemoteSocketAddress()==ms.getLocalSocketAddress()) {
								System.out.println("Ne rien faire stp");
							}else {
								for(int i = 0; i < gp.getListeProtocoles().size(); i++) {								
									if(mots2[3].equalsIgnoreCase(gp.getNomP(i))) {
										s = "La partie " + gp.getNomP(i) + " existe deja";
										System.out.println(s);
										break;
									}								
								}
								if(!s.substring(0, 2).equalsIgnoreCase("AP")) {break;}
								gp.getListeProtocoles().add(s);
								Partie p=new Partie(mots2[3],mots2[4]);
								gp.listeparties.add(p);
								System.out.println(gp.listeparties.toString());
								Serveur serveur=new Serveur(p);
								serveur.runServer();
								
								
							}
							break;
					//////////////////////////////////////////////////////////////////////////////////
						case "RP":
							String r = "";
							int j = 1;
							for(int i = 0; i < gp.getListeparties().size(); i++) {	
								System.out.println(gp.getListeparties().get(i));
								if(mots2[1].equalsIgnoreCase(""+gp.getListeparties().get(i).getNBJM())) {									
									r += (j++)+". " + gp.getListeProtocoles().get(i)+"\n";																		
								}
							}
							System.out.println(r);
							Commandes.annoncerPartie(r);
							break;
					///////////////////////////////////////////////////////////////////////////////////
						default:
							Thread.sleep(100);
							System.out.println("Received "+s);
							break;
					}
					
					
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	public static void ajouterJoueur(String nomP) {
		System.out.println(gp.getListeProtocoles().toString());
		ArrayList<String> debut = new ArrayList<String>();
		
		for(String s:gp.getListeProtocoles()) {
			String sep="-";
			String tab[]=s.split(sep);
			String rep = "";
			if(nomP.equals(tab[3])) {
				System.out.println(tab[5]);
				int i=Integer.parseInt(tab[5]) + 1;
				System.out.println(i);
				tab[5]=""+i;				
				for(int t = 0; t<tab.length-1; t++) {
					rep+=tab[t]+"-";
				}
				rep+=tab[tab.length-1];
			}else {
				rep += s;
			}
			debut.add(rep);
			
		}
		gp.setListeProtocoles(debut);
		System.out.println(gp.getListeProtocoles().toString());
	}

}
