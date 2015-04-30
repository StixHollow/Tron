
public class Point {

	protected int x;
	protected int y;
	
	public Point (int x, int y, int[][] grille){
		this.x = x;
		this.y = y;
		grille[x][y] = 0;
	}
	
	public Point (Point pt, int[][] grille){
		this.x  = pt.getX();
		this.y = pt.getY();
		grille[x][y] = 0;
	}
	
	
	

	
	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Point p) {
		
		if (p.getX() == this.x && p.getY() == this.y){
			return true;
		} else {
			return false;
		}
	}

	//Get 
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	//set
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
}
