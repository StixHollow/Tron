
public class Point {

	protected int x;
	protected int y;
	
	public Point (int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Point (Point pt){
		this.x  = pt.getX();
		this.y = pt.getY();
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
		System.out.println(this.y);
	}
}
