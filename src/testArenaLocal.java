import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class testArenaLocal {
	static ArrayList<TronPlayer> tab = new ArrayList<TronPlayer>();
	
	static int grille[][]=new int[100][100];
	
	
	private static TronPlayer player1 = new TronPlayer("bleh", "pat", grille);
	private static TronPlayer player2 = new TronPlayer("bleh", "leo", grille);
	private static JAreneTron arene = new JAreneTron(100,100,tab);
	private static Color[] color = {Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN, Color.CYAN, Color. GRAY};
	
	
	public static void main(String[] args) {
		BorderLayout layout = new BorderLayout (10,10);
		
		for(int i = 0; i < tab.size(); i++){
			tab.get(i).setColorPlayer(color[i]);
		}

		tab.add(player1);
		tab.add(player2);
		JFrame frame = new JFrame();
		JPanel pane = new JPanel();
		pane.setLayout(layout);
		pane.add(arene, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("test");
		frame.getContentPane().add(arene);
		frame.show();
	}

}
