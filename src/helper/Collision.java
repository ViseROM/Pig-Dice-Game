package helper;

import entity.Entity;

/**
 * Collision class is a helper class that determines collisions
 * between objects
 * 
 * @author Vachia Thoj
 *
 */
public class Collision 
{
	/**
	 * Method that determines if two Entities have collided
	 * Uses the Axis-Aligned Bounding Box (AABB) collision detection
	 * 
	 * @param e1 (Entity) An Entity object
	 * @param e2 (Entity) An Entity object
	 * @return true if the two Entities have collided, otherwise false
	 */
	public static boolean aabbCollision(Entity e1, Entity e2)
	{
		if(e1.getX() < e2.getX() + e2.getWidth() &&
		   e1.getX() + e1.getWidth() > e2.getX() &&
		   e1.getY() < e2.getY() + e2.getHeight() &&
		   e1.getY() + e1.getHeight() > e2.getY())
		{
			return true;
		}
				
		return false;
	}
	
	/**
	 * Method that determines if two objects have collided
	 * Uses the Axis-Aligned Bounding Box (AABB) collision detection
	 *  
	 * @param x1 (int) x-coordinate of 1st object
	 * @param y1 (int) y-coordinate of 1st object
	 * @param x2 (int) x-coordinate of 2nd object
	 * @param y2 (int) y-coordinate of 2nd object
	 * @param width1 (int) width of 1st object
	 * @param height1 (int) height of 1st object
	 * @param width2 (int) width of 2nd object
	 * @param height2 (int) height of 2nd object
	 * @return true if the two objects have collided, otherwise false
	 */
	public static boolean aabbCollision(int x1, int y1, int x2, int y2, int width1, int height1, int width2, int height2)
	{
		if(x1 < x2 + width2 &&
		   x1 + width1 > x2 &&
		   y1 < y2 + height2 &&
		   y1 + height1 > y2)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Method that determines if an Entity has collided with a coordinate/point
	 * @param x (int) The x-coordinate of the point
	 * @param y (int) The y-coordinate of the point
	 * @param e (Entity) The Entity object
	 * @return true if Entity has collided with the point/coordinate, otherwise false
	 */
	public static boolean pointEntityCollision(int x, int y, Entity e)
	{		
		if(x >= e.getX() &&
		   x <= (e.getX() + e.getWidth()) &&
		   y >= e.getY() &&
		   y <= (e.getY() + e.getHeight()))
		{
			return true;
		}
		
		return false;
	}
}