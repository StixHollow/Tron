/**
 * Classe de structure du profil du joueur
 * 
 * @author Leo Marti & Patrice Wilhelmy
 * @version 0.5.2
 * @see PlayerConnection.java
 * @see TronHeartBeat.java
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class TronHeartBeat extends Thread {

	// serveur principal 
	private TronServer server;
	
	private int nbConnexion;		// nombre du joueur connecter
	private int nbPlayerAlive;		// nombre du joueur vivant
	private int clocktick;
	
	private boolean party;			// statut de la partie
	
	private PrintWriter[] os;		// stream d'envoie

	TronHeartBeat(TronServer s, int ct) throws ConnectionException {
		
		// initialisation des variables
		this.server = s;
		clocktick = ct;
		nbConnexion = 0;
		nbPlayerAlive = 0;
		party = false;
		
		os = new PrintWriter[10];
	}
	
	/**
	 * Ajout d'un nouveau joueur au coeur du serveurs
	 * @param clientSocket - socket du clients
	 * @return true - All Right ! || false - Good luck
	 * @throws ConnectionException
	 */
	public boolean addNewPlayer(Socket clientSocket) throws ConnectionException {
		
		// valeur de retour
		boolean chkConnection = false;
		
		try {

			//creation d'un stream pour le joueur
			os[nbConnexion] = new PrintWriter(clientSocket.getOutputStream());
			// augmentation du nombre de joueurs et de vivants
			nbConnexion++;
			nbPlayerAlive++;
			// tout fonctionne
			chkConnection = true;

		} catch (IOException e) {
			System.err.println("Exception en ouvrant les streams du client: " + e);
			throw new ConnectionException(e);
		}

		return chkConnection;

	}

	/**
	 * Lancement du serveur
	 */
	public void run() {
		
		// attante que 2 joueur au minimum soit connectes
		while (nbConnexion < 2){
			
			try {
			    Thread.sleep(1000);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
			
		}
		
		// lancement de la partie
		startParty();
		// creation du tick horloge
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				
				if (party){
					whoIsAlive();
					sendMove();
					
				}
				
				
			}
		}, 0, clocktick);

	}
	
	public void whoIsAlive(){
		
		TronPlayer p;
		TronPlayer otherP;
		
		for (int i=0; i < nbConnexion; i++){
			p = server.getPlayer(i).getInfoPlayer();
			/* if (p.getTracePlayer().ramTrace()){
				server.getPlayer(i).getInfoPlayer().setAlive(false);
			} */
			
			// logiquement si on repasse sur tous les joueurs on reprend le joueur en cours en compte
			for (int k=0; k < nbConnexion; k++){
				otherP = server.getPlayer(i).getInfoPlayer();
				if (otherP.getTracePlayer().thisPointRamTrace(p.getTracePlayer().getLastPoint())){
					server.getPlayer(i).getInfoPlayer().setAlive(false);
				}
			}
			
		}
		
	}

	/**
	 * Envoi des mouvement aux joueurs
	 */
	public void sendMove() {
		// ligne d'envoi
		String d = "";
		
		// formatage de la ligne d'envoi
		for (int i = 0; i < nbConnexion; i++) {
			
			if (server.getPlayer(i).getInfoPlayer().isAlive()){
				// recuperation et transformation du char direction en string
				d = d + String.valueOf(server.getPlayer(i).getInfoPlayer().getDirection());
			} else {
				d = d + "X";
				nbPlayerAlive -= 1;
			}
		}
		System.out.println("s" + d);
		// envoi des positions a l'ensemble des joueur
		for (int i = 0; i < nbConnexion; i++) {
			os[i].println("s" + d);
			os[i].flush();
			
		}
		
		
	}
	
	/**
	 * Lancement de la partie
	 */
	public void startParty(){
		party = true;
	}

}
