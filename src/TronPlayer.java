<<<<<<< HEAD
import java.awt.Color;


public class TronPlayer {
	
	private String name; 
	private String hostname;
	private Color colorPlayer;
	private Trace tracePlayer;
	private char direction;
	private boolean Alive;
=======
/**
 * Classe de structure du profil du joueur
 * 
 * @author Leo Marti & Patrice Wilhelmy
 * @version 0.5.2
 * @see PlayerConnection.java
 * @see TronHeartBeat.java
 */

public class TronPlayer {
	
	private String name; 			// Nom du joueur
	private String hostname;		// nom de connection de son ordinateur du le reseau
	private String colorPlayer;		// couleur du joueur
	private Trace tracePlayer;		// trace du joueur
	private String direction;			// direction actuelle du joueur
	private int posX;				// position du joueur en X
	private int posY;				// position du joueur en Y
	private boolean Alive;			// si le joueur est en vie
>>>>>>> origin/master
	
	/**
	 * Constructeur 
	 * @param host - nom du host du joueur
	 * @param n - nom du joueur
	 */
	public TronPlayer(String host, String n) {
		
		name = n;
		hostname = host;
		// initialisation du joueur a vivant
		Alive = true;
		
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
	 * toString de la classe affichant son contenu
	 */
	public String toString(){
		
		String line = null;
		
		line = "Nom : " + name + "\n";
		line += "	Hostname : " + hostname + "\n";
		line += "	Couleur : " + colorPlayer + "\n";
		line += "	Direction : " + direction + "\n";
		line += "	En vie : " + Alive + "\n";
		
		return line;
	}
	
}
