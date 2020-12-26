package pjr;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Commandes{
	
	private static int port = 7779;
	private static String IP = "224.7.7.7";
	private static Scanner scan = new Scanner(System.in);
	private static String reponseRp = "";
		
	
	public static String rechercherPartie(String s) {
		try {
			InetAddress ia=InetAddress.getByName(IP);
			MulticastSocket ms=new MulticastSocket(port);
			ms.joinGroup(ia);
			DatagramPacket dp=new DatagramPacket(s.getBytes(),s.getBytes().length,ia,port);			
			ms.send(dp);
			
			//attente des reponses		
			timerReponse(ms);
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			String sep3 ="\n";
			String tab3[] = getReponseRp().split(sep3);
			
			System.out.println("Selectionner une partie (0 pour quiter)");
			int x=scan.nextInt();
			
			
			if(x==0) {
				return "quitter";
			}else {
				return tab3[x-1];
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Erreur";
	}
	
	public static void timerReponse(MulticastSocket ms) {
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				public void run() {	
					for(int i = 0; i < 5; i++) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						byte[] rep = new byte[256];			
						DatagramPacket dp1 = new DatagramPacket(rep, rep.length);			
						try {
							ms.receive(dp1);
						} catch (IOException e) {
							e.printStackTrace();
						}
						String rep1 = new String(dp1.getData(), 0, dp1.getLength());
						System.out.println(rep1);
						try {
							if(!(rep1.substring(0, 2)).equals("RP")) {
								setReponseRp(rep1);
							}	
						}catch(StringIndexOutOfBoundsException e2){
							rep1="";
						}
												
						
						if(i==4) {
							timer.cancel();
							timer.purge();	
						}									
					}
				}
			};
			timer.schedule(task, 1000);	
		}
	
	
	
	public static void annoncerPartie(String p) {
		InetAddress ia;
		try {
			ia = InetAddress.getByName(IP);
			MulticastSocket ms=new MulticastSocket(port);
			ms.joinGroup(ia);
			DatagramPacket p1=new DatagramPacket(p.getBytes(),p.getBytes().length,ia,port);
			String rep1 = new String(p1.getData(), 0, p1.getLength());
			ms.send(p1);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}	
	
	
	public static String getReponseRp() {
		return reponseRp;
	}

	public static void setReponseRp(String reponseRp) {
		if(!reponseRp.substring(0, 2).equalsIgnoreCase("RP"))
			Commandes.reponseRp += (reponseRp+"\n");
	}
}
