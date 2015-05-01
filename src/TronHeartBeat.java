/**
 * Classe de structure du profil du joueur
 * 
 * @author Leo Marti & Patrice Wilhelmy
 * @version 1.1
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
	
	final static private int tmpWait = 10;

	private int nbConnexion; // nombre du joueur connecter
	private int nbPlayerAlive; // nombre du joueur vivant
	private int clocktick;

	private boolean party; // statut de la partie

	private PrintWriter[] os; // stream d'envoie

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
	 * 
	 * @param clientSocket
	 *            - socket du clients
	 * @return true - All Right ! || false - Good luck
	 * @throws ConnectionException
	 */
	public boolean addNewPlayer(Socket clientSocket) throws ConnectionException {

		// valeur de retour
		boolean chkConnection = false;

		try {

			// creation d'un stream pour le joueur
			os[nbConnexion] = new PrintWriter(clientSocket.getOutputStream());
			// augmentation du nombre de joueurs et de vivants
			nbConnexion++;
			nbPlayerAlive++;
			// tout fonctionne
			chkConnection = true;

		} catch (IOException e) {
			System.err.println("Exception en ouvrant les streams du client: "
					+ e);
			throw new ConnectionException(e);
		}

		return chkConnection;

	}

	/**
	 * Lancement du serveur
	 */
	public void run() {

		// attante que 2 joueur au minimum soit connectes
		while (nbConnexion < 2) {

			try {
				Thread.sleep(tmpWait * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		// lancement de la partie
		startParty();

	}
	
	/**
	 * test quel(le) joueur ou joueuse sont encore en vie
	 */
	public void whoIsAlive() {

		TronPlayer p; // joueur actuel
		TronPlayer otherP; // joueur a comparer
		
		// on test tout les joueurs
		for (int i = 0; i < nbConnexion; i++) {
			// recuperation du joueur en cours
			p = server.getPlayer(i).getInfoPlayer();
			/*
			 * if (p.getTracePlayer().ramTrace()){
			 * server.getPlayer(i).getInfoPlayer().setAlive(false); }
			 */
			if (p.getTracePlayer().ramBorder()) {
				server.getPlayer(i).getInfoPlayer().setAlive(false); // il est mort
				nbPlayerAlive -= 1; 

			}
			
			// logiquement si on repasse sur tous les joueurs on reprend le
			// joueur en cours en compte
			for (int k = 0; k < nbConnexion; k++) {
				// joueur à comparer
				otherP = server.getPlayer(i).getInfoPlayer();
				// si la tete touche une partie du cours d'un autre joueur
				if (otherP.getTracePlayer().thisPointRamTrace (p.getTracePlayer().getLastPoint())) { 					
					server.getPlayer(i).getInfoPlayer().setAlive(false); // il est mort
					nbPlayerAlive -= 1;
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
			// deplace le joueur
			server.getPlayer(i).movePlayer();
			// test s'il est toujour en vie
			if (server.getPlayer(i).getInfoPlayer().isAlive()) {
				// recuperation et transformation du char direction en string
				d = d + String.valueOf(server.getPlayer(i).getInfoPlayer().getDirection());
			} else {
				d = d + "X";
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
	public void startParty() {
		party = true;
		// creation du tick horloge
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				if (party) {
					// tant qu'il y a des joueurs vivant
					if (nbPlayerAlive > 1) {
						// envoi les directions
						sendMove();
						// test s'ils sont en vie
						whoIsAlive();
						
					} else {
						// fini la partie
						party = false;
						// arrete le timer
						timer.cancel();
						// relance
						restart();
					}

				}

			}
		}, 0, clocktick); //clocktick
	}
	
	/**
	 * Restart une partie
	 */
	public void restart(){
		System.out.println("Une nouvelle partie recommance dans " + tmpWait + "sec");
		// attendre 10 sec
		try {
			Thread.sleep(tmpWait * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		nbPlayerAlive = nbConnexion;
		
		// pour chaque joueur
		for (int i=0; i < nbConnexion; i++){
			server.getPlayer(i).getInfoPlayer().setNewPosition(server.getGrille()); // nouvelle position
			server.getPlayer(i).getInfoPlayer().setAlive(true); // il est mort
			os[i].println("R"); // message de restart
			server.getPlayer(i).sendPlayers(); // envoi les coordonées de chaques joueurs
		}
		// relance le timer
		
		startParty();
	}

}
