package entity;
/**
 * Point class keeps track of the x and y coordinate of a point
 * 
 * @author Vachia Thoj
 *
 */
public class Point 
{
	//x and y position of Point
	private int x;
	private int y;
	
	public Point()
	{
		
	}
	
	/**
	 * Constructor
	 * @param x (integer) the x coordinate of Point
	 * @param y (integer) the y coordinate of Point
	 */
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	//Getter methods
	public int getX() {return x;}
	public int getY() {return y;}
	
	//Setter methods
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public void setPoint(int x, int y) {this.x = x; this.y = y;}
}