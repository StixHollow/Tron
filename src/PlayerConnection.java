/**
 * Classe thread des connections avec le joueur
 * 
 * @author Leo Marti & Patrice Wilhelmy
 * @version 0.5
 * @see TronServer.java
 * @see TronHeartBeat.java
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerConnection extends Thread {

	private TronPlayer infoPlayer;			// Profil du joueur
	private int idPlayer;
	private char nextMove;					// mouvement suivant du joueur
	
	// taille de la grille de jeu
	private int gridwidth = 0;				
	private int gridheight = 0;
	

	// Info connexion
	private Socket clientSocket;
	private PrintWriter os;
	private BufferedReader is;
	
	// serveur du jeu
	private TronServer server;
	

	PlayerConnection(Socket clientSocket, int w, int h, int id, TronServer s) throws ConnectionException {
		
		// initialisation des variables
		this.server = s;
		this.clientSocket = clientSocket;
		this.gridwidth = w;
		this.gridheight = h;
		this.idPlayer = id;
		
		try {
			// Creation des Streams du jeu
			
			os = new PrintWriter(clientSocket.getOutputStream());
			is = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
		} catch (IOException e) {
			System.err.println("Exception en ouvrant les streams du client: " + e);
			throw new ConnectionException(e);
		}
	}

	/**
	 * Gestion du la fin de connection
	 */
	protected void finalize() {
		try {
			// cleanup
			os.close();
			is.close();
			clientSocket.close();
		} catch (IOException e) {
			System.err
					.println("IOException en fermant les stream et le socket client"
							+ e);
		}
	}

	/**
	 * Lancement du thread
	 */
	public void run() {
		// protocole de communication avec le Client
		String reception;
		
		try { 
			
			// recuperation du texte du client
			reception = is.readLine();
			if (reception == null) { // on finit le travail anormalement
				System.err.println("le client n'a pas etabli une connection normale");
				return;
			}

			//System.out.println(reception + "@" + clientSocket.getInetAddress().getLocalHost().getHostName());
			
			// initialisation de la connexion 
			String name = reception;
			String host = clientSocket.getInetAddress().getLocalHost().getHostName();
			infoPlayer = new TronPlayer(host, name);
			
			// envoi des dimension la grille de jeu
			os.println(gridwidth);
			os.flush(); 
			os.println(gridheight);
			os.flush(); 
			
			// envoi des informations du joueur aux autres joueurs
			notifyAllPlayer(); 
			
			// envoi les infos des autres joueurs à ce client
			sendPlayers();
			
			// reception des directions
			while ((reception = is.readLine()) != null) {

				// Mise a jour des directions
				if (reception != null){
					nextMove = reception.charAt(0);
					infoPlayer.setDirection(reception.charAt(0));
				}
				
				// affichage des données du joueur
				System.out.println(infoPlayer.toString());
				System.out.flush();
			}

			//server.removePlayer(); IMPLEMENTER

		} catch (IOException e) { // pour TRY PROTOCOLE
			System.err.println("IOException durant l'interaction avec le client:" + e);
			return;
		}
	}
	
	/**
	 * envoi des joueurs deja connecte au client
	 */
	public void sendPlayers(){
		
		// envoi de chaque client de la liste de joueur au client
		for (int i = 0; i <= server.getNbPlayers()-1; i++){
			// Info : nom, host, color, position x et y
			os.println("+" + server.getPlayer(i).getInfoPlayer().getName());
			os.flush(); 
			os.println(server.getPlayer(i).getInfoPlayer().getHostname());
			os.flush(); 
			os.println(server.getPlayer(i).getInfoPlayer().getColorPlayer());
			os.flush(); 
			os.println(server.getPlayer(i).getInfoPlayer().getPosX());
			os.flush(); 
			os.println(server.getPlayer(i).getInfoPlayer().getPosY());
			os.flush(); 
		}
		
	}
	
	/**
	 * Envoi des données du dernier joueur au client
	 */
	public void sendLastPlayerInfo(){
		
		int nb = server.getNbPlayers() -1;
		
		os.println("+" + server.getPlayer(nb).getInfoPlayer().getName());
		os.flush(); 
		os.println(server.getPlayer(nb).getInfoPlayer().getHostname());
		os.flush(); 
		os.println(server.getPlayer(nb).getInfoPlayer().getColorPlayer());
		os.flush(); 
		os.println(server.getPlayer(nb).getInfoPlayer().getPosX());
		os.flush(); 
		os.println(server.getPlayer(nb).getInfoPlayer().getPosY());
		os.flush(); 
	}
	
	/**
	 * Notifie les joueur de l'arrivé du nouveau joueur
	 */
	public void notifyAllPlayer(){
		for (int i = 0; i <= server.getNbPlayers()-1; i++){
			server.getPlayer(i).sendLastPlayerInfo();
			
		}
	}

	/*
	 * GETTER SETTER
	 */
	
	public TronPlayer getInfoPlayer() {
		return infoPlayer;
	}


	public int getGridwidth() {
		return gridwidth;
	}


	public int getGridheight() {
		return gridheight;
	}
	

}
