import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;


public class JAreneTron extends JComponent {

	private Graphics2D context;
	private ArrayList<TronPlayer> players;
	private double vWidth;
	private double vHeight;
	private double gWidth;
	private double gHeight;
	private KeyListener listener;
	
	public JAreneTron (int width, int height, ArrayList<TronPlayer> players){
		this.players = players;
		this.vWidth = width +2;
		this.vHeight = height +2;
		setFocusable(true);
		
	}
	protected void paintComponent(Graphics g) {
		context = (Graphics2D)g;
		Point temp;
		
		//Établissement des mesures de la grille virtuel
		double ratio;
		double temp1 =  getWidth()/vWidth;
		double temp2 =  getHeight()/vHeight;
		if (temp1 > temp2){
			ratio = temp2;
		}
		else{
			ratio = temp1;
		}
		this.gWidth = vWidth * ratio;
		this.gHeight = vHeight * ratio;
		//Dessine l'arene
		context.setColor(Color.BLACK);
		context.fill(new Rectangle2D.Double(0, 0, gWidth, gHeight));
		Stroke oldStroke = context.getStroke();
		context.setColor(Color.WHITE);
		context.setStroke(new BasicStroke(10));
		context.draw(new Rectangle2D.Double(0,0,gWidth, gHeight));
		context.setStroke(oldStroke);

		//Dessine les joueurs
		for (int i = 0; i < players.size(); i++){
			for (int j = 0; j < players.get(i).getTracePlayer().getListePt().size(); j++){
				temp = players.get(i).getTracePlayer().getListePt().get(j);
				context.setColor(players.get(i).getColorPlayer());
				double pointTempX = temp.getX() * (ratio);
				double pointTempY = temp.getY() * (ratio);
				context.draw(new Rectangle2D.Double(pointTempX-(ratio/2), pointTempY-(ratio/2),ratio, ratio));	
				context.fill(new Rectangle2D.Double(pointTempX-(ratio/2), pointTempY-(ratio/2),ratio, ratio));
			}
		}
	}
	public void setPlayers(TronPlayer p, int index){
		players.set(index, p);
	}
	public ArrayList<TronPlayer> getPlayers(){
		return players;
	}
}
 