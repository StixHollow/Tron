import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TronClient implements KeyListener{
	
	JAreneTron arene;
	JPanel pane, pane2;
	int gridwidth = 100;
	int gridheight = 100;
	int numJouer = 0;
	private ArrayList<TronPlayer> players;
	static TronClient client;
	int port;
	String address;
	
	public static void main(String[] args) {
		if (args.length!=2) {
		      System.out.println("Usage: java Client <hostname> <port>");
		      System.exit(0);
		    }
		client = new TronClient (args[0], Integer.parseInt(args[1]));

	}
	
	public TronClient (String hostserver, int serverport){
	    //communication avec le serveur
		Socket socket=null;
	    BufferedReader in=null;
	    PrintWriter out=null;
	    Socket s=null;
	    
	    
	      
	    port = (serverport);
	    address = hostserver;

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


	      // protocole pour verifier que Client et Serveur sont compatibles
	      out.println(System.getProperty("user.name"));
	      out.flush(); 

	      // see if the server is happy
	      System.out.println("attendre la reponse du serveur");
	      System.out.flush();

	      // reception de la grille
	      
	      
	      String answer = in.readLine();
	      String answer2 = in.readLine();
	      System.out.println("Grille :  " + answer + " x " + answer2);
	      System.out.flush();
	      
	      
	      int PlayerArrive = 0;
	      
	      do {
	    	  answer = in.readLine();
	    	  if (answer.substring(0, 1).equals("+")) {
	    		  PlayerArrive = 5;
	    	  }
	    	  System.out.println(answer);
	    	  PlayerArrive -= 1;
	      } while (PlayerArrive != 0);
	      
	      

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

	    //Création de la liste de joueur
			this.players = new ArrayList<TronPlayer>();
			while(socket.){
				
			}
			//Création de l'interface graphique
			//Frame
			BorderLayout layout = new BorderLayout (10,10);
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("Tron");
			frame.setSize(500,500);
			
			//JArene
			arene = new JAreneTron(gridwidth,gridheight,players);
			JPanel pane = new JPanel();
			pane.setLayout(layout);
			pane.add(arene, BorderLayout.CENTER);

			//Liste joueur
			pane2 = new JPanel();
			BoxLayout layout2 = new BoxLayout (pane2, BoxLayout.Y_AXIS);
			pane2.setLayout(layout2);;
			pane.add(pane2, BorderLayout.EAST);
			
			//Focus clavier sur arene
			frame.addWindowFocusListener(new WindowAdapter() {
			    public void windowGainedFocus(WindowEvent e) {
			        arene.requestFocusInWindow();
			    }
			});
			arene.addKeyListener(this);
			//Affichage
			frame.getContentPane().add(pane);
			frame.show();
			
	      do {
	    	answer = in.readLine();
	        System.out.println(answer);
	        
	        /*userLine = userIn.readLine();
	        if (userLine==null) break;
	        System.out.println("->wait for answer from server");
	        System.out.flush();
	        out.println(userLine);
	        out.flush();
	        answer = in.readLine();
	        System.out.println("ANSWER FROM SERVER: "+answer);
	        System.out.flush();*/
	      } while (true);

	    }
	    catch (IOException e) { // pour TRY PROTOCOLE
	      System.err.println("Exception: I/O error trying to talk to server: "
	                         + e);
	    }
	    
		
	}
	
	public void addPlayer(TronPlayer e){
		players.add(e);
		arene.getPlayers().add(e);
		JLabel label = new JLabel(e.getName());
		label.setForeground(e.getColorPlayer());
		pane2.add(label);
	}
	
	public void resetPlayerList(){
		for (int i = players.size()-1; i >= 0; i--){
			System.out.println("tattaL");
			players.remove(i);
		}
		System.out.println(players.isEmpty());
		arene.repaint();
		pane2.removeAll();
	}
	
	//Getter&Ssetter
	public void setPlayers(TronPlayer p, int index){
		players.add(index, p);
	}
	public ArrayList<TronPlayer> getPlayers(){
		return players;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		this.players.get(numJouer).getTracePlayer().allonge(c);
		System.out.println("Caractère appuyé: "+c); 
		arene.repaint();
		
	}
}
