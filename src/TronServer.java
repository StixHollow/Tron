//
// Serveur.java
//
// Exemple de serveur IP  en Java
//
// Il attend des connections de serveurs sur le port donne a l'appel,
// et lance alors une thread Connection. La thread Connection verifie
// que le client est du bon type avec un protocole de communication,
// ensuite elle lit des lignes du client et les renvoie a l'envers.
//

import java.io.*;
import java.net.*;

public class TronServer {

	
	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		
		PlayerConnection[] Players = new PlayerConnection[10]; 
		int nbPlayers = 0;
		

		if (args.length < 3) {
			System.err.println("Usage: java Serveur <port>");
			System.exit(0);
		}
		
		int port = Integer.parseInt(args[0]);
		int gridwidth = Integer.parseInt(args[1]);
		int gridheight = Integer.parseInt(args[2]);
		
		
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Connecté sur le port " + serverSocket.getLocalPort());
		} catch (IOException e) {
			System.err.println("Exception: impossible de creer ServerSocket: " + e);
			System.exit(1);
		}

		// boucle infinie attendant des clients
		while (true) {
			try {
				System.out.println("En attente de connection...");
				System.out.flush();
				
				Socket clientSocket = serverSocket.accept();
				
				Players[nbPlayers] = new PlayerConnection(clientSocket, gridwidth, gridheight);
				Players[nbPlayers].start();
				
				nbPlayers++;
				
				
				System.out.println("Nombre de joueur : " + nbPlayers);
				
				
			} catch (IOException e) {
				System.err.println("Exception lancee par ServerSocket.accept: " + e);
				System.exit(1);
				
			} catch (ConnectionException e) {
				System.err.println("Exception lancee pendant la construction de la connection: "
								+ e);
				System.exit(1);
			}
		}
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