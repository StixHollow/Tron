import java.util.*;

public class Trace {
	
	protected ArrayList<Point> listePt;
	protected int  indexListePt;
	protected Point depart;
	
	
	public Trace (TronPlayer player, Point pt){
		depart = pt;
		listePt = new ArrayList<Point>();
		indexListePt = 1;
		
		listePt.add(pt);
	}
	
	
	public void allonge(char direction){
		Point temp = new Point (listePt.get(indexListePt-1).getX(),listePt.get(indexListePt-1).getY());
		
		switch (direction){
		
		case 'N' : temp.setY(temp.getY() - 1);break;
		case 'S' : temp.setY(temp.getY() + 1);break; 
		case 'E' : temp.setX(temp.getX() - 1);break;
		case 'O' : temp.setX(temp.getX() + 1);break;	
		}
		listePt.add(temp);
		System.out.println(listePt.get(indexListePt-1).getY() - 1  + " " + temp.getX() + " " + temp.getY());
		indexListePt++;
	}
	
	public boolean ramTrace(){
		boolean r = false;
		
		for (int j=0; j < getIndexListePt(); j++){
			if (getLastPoint().equals(getPointOf(j))){
				r = true;
			}
			
		}
		
		return r;
	}
	
	public boolean thisPointRamTrace(Point p){
		boolean r = false;
		
		// getIndexListPt-1 pour ne pas reprendre en compte la tete que l'on compare
		for (int i=0; i < getIndexListePt()-1; i++){
			if (p.equals(getPointOf(i))){
				r = true;
			}
			
		}
		
		return r;
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
