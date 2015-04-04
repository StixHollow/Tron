import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerConnection extends Thread {

	private TronPlayer infoPlayer;
	private char nextMove;
	private int gridwidth = 20;
	private int gridheight = 20;

	// Info connexion
	private Socket clientSocket;
	private PrintWriter os;
	private BufferedReader is;

	PlayerConnection(Socket clientSocket) throws ConnectionException {
		this.clientSocket = clientSocket;
		try {
			os = new PrintWriter(clientSocket.getOutputStream());
			is = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
		} catch (IOException e) {
			System.err.println("Exception en ouvrant les streams du client: " + e);
			throw new ConnectionException(e);
		}
	}

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

	public void run() {
		// protocole de communication avec le Client
		String ligneClient;

		System.out.println("attente du message d'ouverture du client...");
		System.out.flush();

		try { // TRY PROTOCOLE

			// verifier que le Client et le Serveur parlent le meme langage
			ligneClient = is.readLine();
			if (ligneClient == null) { // on finit le travail anormalement
				System.err.println("le client n'a pas etabli une connection normale");
				return;
			}

			// System.out.println("envoyer reponse protocolaire");
			// System.out.flush();

			System.out.println(ligneClient + "@" + clientSocket.getInetAddress().getLocalHost().getHostName());
			

			os.println(gridwidth);
			os.flush(); 
			os.println(gridheight);
			os.flush(); // NE PAS OUBLIER LE FLUSH!

			// boucle sur les commandes du client
			while ((ligneClient = is.readLine()) != null) {
				System.out.println("Direction: " + ligneClient);
				System.out.flush();

				os.println(new StringBuffer("Merci Client"));
				os.flush();
			}

			System.out.println("fin normale de la connection client");

		} catch (IOException e) { // pour TRY PROTOCOLE
			System.err
					.println("IOException durant l'interaction avec le client:"
							+ e);
			return;
		}
	}

}
