import java.awt.Color;


public class TronPlayer {
	
	private String name; 
	private String hostname;
	private Color colorPlayer;
	private Trace tracePlayer;
	private char direction;
	private boolean Alive;
	
	public TronPlayer(String host, String n) {
		
		name = n;
		hostname = host;
		
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
	public char getDirection() {
		return direction;
	}
	public void setDirection(char direction) {
		this.direction = direction;
	}
	public boolean isAlive() {
		return Alive;
	}
	public void setAlive(boolean alive) {
		Alive = alive;
	}
	
	
	
}
