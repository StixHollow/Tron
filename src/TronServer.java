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

	PlayerConnection[] Players = new PlayerConnection[10];
	ServerSocket serverSocket = null;
	int nbPlayers = 0;
	int gridwidth;
	int gridheight;

	public static void main(String[] args) {

		// TronHeartBeat Heart;

		if (args.length < 3) {
			System.err.println("Usage: java Serveur <port>");
			System.exit(0);
		}

		int port = Integer.parseInt(args[0]);
		int gridwidth = Integer.parseInt(args[1]);
		int gridheight = Integer.parseInt(args[2]);

		TronServer server = new TronServer();

		if (server.creatServer(port)) {
			System.out.println("Connecté sur le port "
					+ server.getServerSocket().getLocalPort());
		} else {
			System.exit(1);
		}
		
		server.run(server);

	}

	public void run(TronServer server) {
		// boucle infinie attendant des clients
		while (true) {
			try {
				System.out.println("En attente de connection...");
				System.out.flush();

				Socket clientSocket = serverSocket.accept();

				// Heart = new TronHeartBeat(clientSocket);
				server.addPLayer(clientSocket);

				System.out.println("Nombre de joueur : " + nbPlayers);

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

	public boolean creatServer(int port) {

		boolean success = false;

		try {
			serverSocket = new ServerSocket(port);
			success = true;

		} catch (IOException e) {
			System.err.println("Exception: impossible de creer ServerSocket: "
					+ e);
		}

		return success;
	}

	public void addPLayer(Socket clientSocket) throws ConnectionException {
		Players[nbPlayers] = new PlayerConnection(clientSocket, gridwidth,
				gridheight, this);
		Players[nbPlayers].start();

		nbPlayers++;
	}

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