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
		
		Point temp = listePt.get(indexListePt);
		switch (direction){
		
		case 'N' : temp.setY(temp.getY() - 1);
		case 'S' : temp.setY(temp.getY() + 1);	   
		case 'E' : temp.setX(temp.getX() - 1);	
		case 'O' : temp.setX(temp.getX() + 1);	
		}
		listePt.add(temp);
	}
	
	//get
	
	public ArrayList<Point> getListePt(){
		return listePt;
	}
	
	public Point getDepart(){
		return depart;
	}
	
	//set
	
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
