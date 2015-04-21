import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;


public class JAreneTron extends JComponent {

	private Graphics2D context;
	private ArrayList<TronPlayer> players;
	private int vWidth;
	private int vHeight;
	private double gWidth;
	private double gHeight;
	
	public JAreneTron (int width, int height, ArrayList<TronPlayer> players){
		this.players = players;
		this.vWidth = width +2;
		this.vHeight = height +2;
		
	}
	protected void paintComponent(Graphics g) {
		context = (Graphics2D)g;
		Point temp;
		
		//Établissement des mesures de la grille virtuel
		double temp1 =  getWidth()/vWidth;
		double temp2 =  getHeight()/vHeight;
		if (temp1 > temp2){
			this.gWidth = vWidth * temp2;
			this.gHeight = vHeight * temp2;
		}
		else{
			this.gWidth = vWidth * temp1;
			this.gHeight = vHeight * temp1;
		}
		
		//Dessine l'arene
		context.setColor(Color.BLACK);
		context.fill(new Rectangle2D.Double(0, 0, gWidth, gHeight));
		Stroke oldStroke = context.getStroke();
		context.setColor(Color.WHITE);
		context.setStroke(new BasicStroke(10));
		context.draw(new Rectangle2D.Double(0,0,gWidth, gHeight));
		context.setStroke(oldStroke);

		//Dessine les joueurs
		
		System.out.println();
		for (int i = 0; i < players.size(); i++){
			for (int j = 0; j < players.get(i).getTracePlayer().getListePt().size(); j++){
				temp = players.get(i).getTracePlayer().getListePt().get(j);
				
				context.setColor(players.get(i).getColorPlayer());
				context.drawLine(temp.getX(), temp.getY(), temp.getX(), temp.getY());				
			}
		}
	}
}
 