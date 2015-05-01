
/**
 * Classe de structure du profil du joueur
 * 
 * @author Leo Marti & Patrice Wilhelmy
 * @version 1.0
 * @see PlayerConnection.java
 * @see TronHeartBeat.java
 */

import java.awt.Color;
import java.util.Random;

public class TronPlayer {
	
	private String name; 			// Nom du joueur
	private String hostname;		// nom de connection de son ordinateur du le reseau
	private Color colorPlayer;		// couleur du joueur
	private Trace tracePlayer;		// trace du joueur
	private String direction;			// direction actuelle du joueur
	private int [][] grille;
	private int posX;				// position du joueur en X
	private int posY;				// position du joueur en Y
	private boolean Alive;			// si le joueur est en vie

	/**
	 * Constructeur 
	 * @param host - nom du host du joueur
	 * @param n - nom du joueur
	 */
	public TronPlayer(String host, String n, int[][] g) {
		setNewPosition(g);
		name = n;
		hostname = host;
		grille = g;
		// initialisation du joueur a vivant
		Alive = true;
		
		tracePlayer = new Trace(this, new Point(posX, posY));
		
	}
	
	/*
	 * Getter et Setter
	 */
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public Color getColorPlayer() {
		return colorPlayer;
	}
	public void setColorPlayer(Color colorPlayer) {
		this.colorPlayer = colorPlayer;
	}
	public Trace getTracePlayer() {
		return tracePlayer;
	}
	public void setTracePlayer(Trace tracePlayer) {
		this.tracePlayer = tracePlayer;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public boolean isAlive() {
		return Alive;
	}
	public void setAlive(boolean alive) {
		Alive = alive;
	}
	
	/**
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
	

	/**
	 * @return the grille
	 */
	public int[][] getGrille() {
		return grille;
	}

	public void setNewPosition(int[][] grille){
		//Genere le point de depart
		System.out.println(grille.length + " " + grille[0].length);
				boolean temp = true;
				int randx = 0;
				int randy = 0;
				while (temp) {
					Random r1 = new Random();
					Random r2 = new Random();
					
					//randx = r1.nextInt(grille.length - 10) + 10;
					//randy = r2.nextInt(grille[0].length - 10) + 10;
					randx = r1.nextInt(90) + 10;
					randy = r2.nextInt(90) + 10;
					if (grille[randx][randy] == 0) {
						temp = !temp;
					}

				}
				posX = randx;
				posY = randy;
	}
	
	/**
	 * toString de la classe affichant son contenu
	 */
	public String toString(){
		
		String line = null;
		
		line = "Nom : " + name + "\n";
		line += "	Hostname : " + hostname + "\n";
		line += "	Couleur : " + colorPlayer + "\n";
		line += "	Direction : " + direction + "\n";
		line += "	En vie : " + Alive + "\n";
		line += "   pos: "+ posX + " "+ posY+"\n";
		
		return line;
	}
	
}