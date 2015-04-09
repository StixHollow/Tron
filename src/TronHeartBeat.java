import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TronHeartBeat extends Thread {

	// Info connexion
	private Socket clientSocket;
	private PrintWriter os;
	private BufferedReader is;

	TronHeartBeat(Socket clientSocket) throws ConnectionException {
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
	
	public void run() {
		// protocole de communication avec le Client
		String reception;
		
		try { // TRY PROTOCOLE

			// verifier que le Client et le Serveur parlent le meme langage
			reception = is.readLine();
			if (reception == null) { // on finit le travail anormalement
				System.err.println("le client n'a pas etabli une connection normale");
				return;
			}

			//System.out.println(reception + "@" + clientSocket.getInetAddress().getLocalHost().getHostName());

			String name = reception;
			String host = clientSocket.getInetAddress().getLocalHost().getHostName();
			
			
			//os.println(gridwidth);
			//os.flush(); 
			//os.println(gridheight);
			//os.flush(); // NE PAS OUBLIER LE FLUSH!

			// reception des directions
			
			while ((reception = is.readLine()) != null) {
				//System.out.println("Direction: " + reception);
				//System.out.flush();
				
				/*if (reception != null){
					nextMove = reception.charAt(0);
					infoPlayer.setDirection(reception.charAt(0));
				}*/
				//os.println(new StringBuffer("Merci Client"));
				//os.flush();

			}

			System.out.println("fin normale de la connection client");

		} catch (IOException e) { // pour TRY PROTOCOLE
			System.err
					.println("IOException durant l'interaction avec le client:" + e);
			return;
		}
	}


}
