import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TronClient extends Thread{
	
	JAreneTron arene;
	JPanel pane, pane2;
	int gridwidth = 100;
	int gridheight = 100;
	private ArrayList<TronPlayer> players;
	static TronClient client;
	public static void main(String[] args) {
		client = new TronClient ("YO", 1337);

	}
	
	public TronClient (String hostserver, int serverport){
		//Création de la liste de joueur
		this.players = new ArrayList<TronPlayer>();
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
		
		frame.getContentPane().add(pane);
		frame.show();
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
		pane2 = new JPanel();
	}
	
	//Getter&Ssetter
	public void setPlayers(TronPlayer p, int index){
		players.add(index, p);
	}
	public ArrayList<TronPlayer> getPlayers(){
		return players;
	}
}
