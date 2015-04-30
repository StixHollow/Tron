import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class testArenaLocal {
	static ArrayList<TronPlayer> tab = new ArrayList<TronPlayer>();
	
	static int grille[][]=new int[100][100];
	
	// Oublie pas que c'est le serveur qui t'envoi la position de dÃ©part
	private static TronPlayer player1 = new TronPlayer("bleh", "pat", grille);
	private static TronPlayer player2 = new TronPlayer("bleh", "leo", grille);
	private static JAreneTron arene = new JAreneTron(100,100,tab);
	private static Color[] color = {Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN, Color.CYAN, Color. GRAY};
	private static TronClient client;
	
	
	public static void main(String[] args) {
		
		client = new TronClient("yolo", 1337);
		player1.setColorPlayer(Color.BLUE);
		player2.setColorPlayer(Color.RED);
		client.addPlayer(player1);
		client.addPlayer(player2);
		client.getPlayers().get(0).getTracePlayer().allonge('N');
		client.getPlayers().get(0).getTracePlayer().allonge('N');
		client.getPlayers().get(0).getTracePlayer().allonge('N');
		client.getPlayers().get(0).getTracePlayer().allonge('N');
		client.resetPlayerList();
		
		//String[] arguments = new String[] {"Yolo 1337"};
		//client.main(arguments);
		//Création de la liste de joueur

		
	}

}
