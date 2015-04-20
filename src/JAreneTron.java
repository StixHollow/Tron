import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;


public class JAreneTron extends JComponent {

	private Graphics context;
	private ArrayList<TronPlayer> players;
	private int width;
	private int height;
	
	public JAreneTron (int width, int height, ArrayList<TronPlayer> players){
		this.players = players;
		this.width = width +2;
		this.height = height +2;
		
	}
	protected void paintComponent(Graphics g) {
		context = g;
		Point temp;
		
		//Dessine le contour
		
		context.setColor(Color.WHITE);
		context.drawRect(0, 0, width, height);
		
		//Dessine les joueurs
		
		for (int i = 0; i < players.size(); i++){
			for (int j = 0; j < players.get(i).getTracePlayer().getListePt().size(); j++){
				temp = players.get(i).getTracePlayer().getListePt().get(j);
				
				context.setColor(players.get(i).getColorPlayer());
				context.drawLine(temp.getX(), temp.getY(), temp.getX(), temp.getY());				
			}
		}
	}
}
 