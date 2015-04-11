/**
 * Classe de gestion du serveur du jeu Tron
 * 
 * @author Leo Marti & Patrice Wilhelmy
 * @version 0.5.2
 * @see PlayerConnection.java
 * @see TronHeartBeat.java
 */

import java.io.*;
import java.net.*;

public class TronServer {

	// liste des joueur connecté au jeu
	PlayerConnection[] Players = new PlayerConnection[10];
	int nbPlayers = 0; // nombre de joueur connecte

	// socket contenant le serveur
	ServerSocket serverSocket = null;

	// Taille de la grille de jeu
	int gridwidth;
	int gridheight;

	public static void main(String[] args) throws ConnectionException {

		// test si l'ensemble des parametres sont present
		// A CORRIGER
		if (args.length < 3) {
			System.err.println("Usage: java Serveur <port>");
			System.exit(0);
		}

		// parse de args
		int port = Integer.parseInt(args[0]);
		int gridwidth = Integer.parseInt(args[1]);
		int gridheight = Integer.parseInt(args[2]);

		// initialisation du serveur
		TronServer server = new TronServer();

		// Creation du serveur
		if (server.creatServer(port)) {
			System.out.println("Connecté sur le port "
					+ server.getServerSocket().getLocalPort());
		} else {
			System.exit(1);
		}

		// Lancement du serveur
		server.run(server);

	}

	/**
	 * Fonction de lancement du serveur 
	 * @param server - Serveur lance
	 * @throws ConnectionException 
	 */
	public void run(TronServer server) throws ConnectionException {
		
		// Lancement du coeur du serveur
		TronHeartBeat heart = new TronHeartBeat(server);
		
		// boucle de connection des clients
		while (true) {
			
			try {
				// Message d'attente de connection
				System.out.println("En attente de connection...");
				System.out.flush();

				// acceptation des nouveaux clients
				Socket clientSocket = serverSocket.accept();
				
				// ajout du player au jeu
				server.addPLayer(clientSocket);
				// ajout du player au coeur du serveur
				heart.addNewPlayer(clientSocket);
				
				// affichage du nombre de joueur
				System.out.println("Nombre de joueur : " + nbPlayers);
				System.out.flush();

			} catch (IOException e) {
				System.err.println("Exception lancee par ServerSocket.accept: "
						+ e);
				System.exit(1);

			} catch (ConnectionException e) {
				System.err
						.println("Exception lancee pendant la construction de la connection: "
								+ e);
				System.exit(1);
			}
		}
	}
	
	/**
	 * Creation du serveur sur le port donne
	 * @param port - port de connection
	 * @return true - All right || false - probleme
	 */
	public boolean creatServer(int port) {
		
		// initialisation de la variable de retour 
		boolean success = false;

		try {
			// tentative de creation du serveur
			serverSocket = new ServerSocket(port);
			success = true;

		} catch (IOException e) {
			System.err.println("Exception: impossible de creer ServerSocket: "
					+ e);
		}
		
		// renvoi de la reussite ou non de la creation du serveur
		return success;
	}

	/**
	 * ajoute un joueur au jeu
	 * @param clientSocket - socket du client
	 * @throws ConnectionException
	 */
	public void addPLayer(Socket clientSocket) throws ConnectionException {
		// creation d'une nouvelle connection
		Players[nbPlayers] = new PlayerConnection(clientSocket, gridwidth, gridheight, this);
		Players[nbPlayers].start();
		
		// incrementation du nombre de joueur
		nbPlayers++;
	}

	/*
	 * GETTER ET SETTER
	 */

	public PlayerConnection getPlayer(int i) {
		return Players[i];
	}

	public int getNbPlayers() {
		return nbPlayers;
	}

	public void setNbPlayers(int nbPlayers) {
		this.nbPlayers = nbPlayers;
	}

	public int getGridwidth() {
		return gridwidth;
	}

	public void setGridwidth(int gridwidth) {
		this.gridwidth = gridwidth;
	}

	public int getGridheight() {
		return gridheight;
	}

	public void setGridheight(int gridheight) {
		this.gridheight = gridheight;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

}

class ConnectionException extends Exception {
	Exception e;

	ConnectionException(Exception e) {
		this.e = e;
	}

	ConnectionException(String msg) {
		super(msg);
		e = this;
	}
}