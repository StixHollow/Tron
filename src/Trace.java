/**
 * Classe de structure de la trace de chaque joueurs
 * 
 * @author Leo Marti & Patrice Wilhelmy
 * @version 1.0
 * @see Point.java
 */
import java.util.*;

public class Trace {
	
	protected ArrayList<Point> listePt;
	protected int  indexListePt;
	protected Point depart;
	protected TronPlayer player;
	
	
	public Trace (TronPlayer p, Point pt){
		depart = pt;
		listePt = new ArrayList<Point>();
		indexListePt = 1;
		player = p;
		
		listePt.add(pt);
	}
	
	/**
	 * deplace la trace du joueur
	 * @param direction
	 */
	public void allonge(char direction){
		Point temp = new Point (listePt.get(indexListePt-1).getX(),listePt.get(indexListePt-1).getY());
		
		switch (direction){
		
		case 'N' : case 'n' : temp.setY(temp.getY() - 1);break;
		case 'S' : case 's' : temp.setY(temp.getY() + 1);break; 
		case 'E' : case 'e' : temp.setX(temp.getX() + 1);break;
		case 'W' : case 'w' : temp.setX(temp.getX() - 1);break;	
		}
		
		player.setPosX(temp.getX());
		player.setPosY(temp.getY());
		
		listePt.add(temp);
		indexListePt++;
	}
	
	/**
	 * test si la tete heurte la trace
	 * @return boolean - true | false
	 */
	public boolean ramTrace(){
		boolean r = false;
		
		for (int j=0; j < getIndexListePt()-1; j++){
			if (getLastPoint().equals(getPointOf(j))){
				r = true;
			}
			
		}
		
		return r;
	}
	
	/**
	 * Test si un point precis touche la trace
	 * @param p - point en question
	 * @return bool : true | false
	 */
	public boolean thisPointRamTrace(Point p){
		boolean r = false;
		
		// getIndexListPt-1 pour ne pas reprendre en compte la tete que l'on compare
		// pour chaque partie
		for (int i=0; i < getIndexListePt()-1; i++){
			if (p.equals(getPointOf(i))){
				r = true;
			}
			
		}
		
		return r;
	}
	
	public boolean ramBorder() {
		
		int sizeX = player.getGrille().length;
		int sizeY = player.getGrille()[0].length;
		
		if (listePt.get(indexListePt-1).getX() >= sizeX || 
			listePt.get(indexListePt-1).getY() >= sizeY || 	
			listePt.get(indexListePt-1).getX() <= 0	    || 
			listePt.get(indexListePt-1).getY() <= 0    		){
		
			return true;
		} else {return false;}
		
	}
	
	//get
	
	public Point getLastPoint(){
		return listePt.get(indexListePt-1);
	}
	
	public Point getPointOf(int i) {
		return listePt.get(i);
	}
	
	public ArrayList<Point> getListePt(){
		return listePt;
	}
	
	public Point getDepart(){
		return depart;
	}
	
	
	/**
	 * @return the indexListePt
	 */
	public int getIndexListePt() {
		return indexListePt;
	}

	//sets

	/**
	 * @param indexListePt the indexListePt to set
	 */
	public void setIndexListePt(int indexListePt) {
		this.indexListePt = indexListePt;
	}


	public void setListePt(int index, Point pt){
		listePt.add(index, pt);
	}
	
	public void setListePt(ArrayList<Point> array){
		listePt = array;
	}
	
	public void setDepart(Point depart){
		this.depart = depart;
	}
}
