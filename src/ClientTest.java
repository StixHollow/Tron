//
// Client.java
//
// Exemple de client IP en Java
//

import java.io.*;
import java.net.*;

public class ClientTest {

  public static void main(String[] args) {
    Socket socket=null;
    BufferedReader in=null;
    PrintWriter out=null;
    Socket s=null;
    
    if (args.length!=2) {
      System.out.println("Usage: java Client <hostname> <port>");
      System.exit(0);
    }
      
    int port = Integer.parseInt(args[1]);
    String address = args[0];

    try {
    	
      socket = new Socket(address,port);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream());
    }
    catch (IOException e) {
      System.err.println("IOException pour ouvrir le Socket ou ses streams: "
                         + e);
      System.exit(1);
    }

    try { // TRY PROTOCOLE
      if (socket==null || in==null || out == null) {
        System.out.println("Pas de connection au serveur: socket=" + socket + ", socket input stream = " + in + ", socket output stream = " + out);
        System.exit(1);
      }

      System.out.println("Socket au serveur etablie");

      System.out.println("envoyer message d'identification au serveur");
      System.out.flush();

      // protocole pour verifier que Client et Serveur sont compatibles
      out.println(System.getProperty("user.name"));
      out.flush(); // NE PAS OUBLIER LE FLUSH

      // see if the server is happy
      System.out.println("attendre la reponse du serveur");
      System.out.flush();

      // reception de la grille
      String answer = in.readLine();
      String answer2 = in.readLine();
      System.out.println("Grille :  " + answer + " x " + answer2);
      System.out.flush();
      
      
      

      if (answer==null) {
        System.out.println("Reponse etrange du serveur: " + answer);
        System.exit(1);
      } else {
        System.out.println("Protocole de communication OK");
        System.out.flush();
      }

      // obtenir un stream du standard input
      BufferedReader userIn = 
        new BufferedReader(new InputStreamReader(System.in));
      
      String userLine;

      do {
        System.out.print("TYPE A QUERY TO THE SERVER: ");
        userLine = userIn.readLine();
        if (userLine==null) break;
        //System.out.println("->wait for answer from server");
        //System.out.flush();
        out.println(userLine);
        out.flush();
        //answer = in.readLine();
        //System.out.println("ANSWER FROM SERVER: "+answer);
        //System.out.flush();
      } while (true);

    }
    catch (IOException e) { // pour TRY PROTOCOLE
      System.err.println("Exception: I/O error trying to talk to server: "
                         + e);
    }

  }
}
