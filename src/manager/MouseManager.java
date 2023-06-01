package manager;
import entity.Point;

/**
 * MouseManager class keeps track and manages the mouses actions
 * 
 * @author Vachia Thoj
 *
 */
public class MouseManager 
{
	//For singleton
	private static MouseManager mouseManager;
	
	//Location that the mouse pressed
	private Point pressedPoint;
	
	//Location that the mouse released
	private Point releasedPoint;
	
	//Current location of mouse
	private Point currentPoint;
	
	//Flags to see of mouse has been pressed or released
	private boolean mousePressed;
	private boolean mouseReleased;
	
	private MouseManager()
	{
		this.pressedPoint = null;
		this.releasedPoint = null;
		this.currentPoint = null;
		
		this.mousePressed = false;
		this.mouseReleased = false;
	}
	
	public static MouseManager instance()
	{
		if(mouseManager == null)
		{
			mouseManager = new MouseManager();
		}
		
		return mouseManager;
	}
	
	//Getter methods
	public Point getPressedPoint() {return pressedPoint;}
	public Point getReleasedPoint() {return releasedPoint;}
	public Point getCurrentPoint() {return currentPoint;}
	public boolean isMousePressed() {return mousePressed;}
	public boolean isMouseReleased() {return mouseReleased;}
	
	//Setter methods
	public void setPressedPoint(int mouseX, int mouseY) {pressedPoint = new Point(mouseX, mouseY);}
	public void setReleasedPoint(int mouseX, int mouseY) {releasedPoint = new Point(mouseX, mouseY);}
	public void setCurrentPoint(int mouseX, int mouseY) {currentPoint = new Point(mouseX, mouseY);}
	public void setMousePressed(boolean b) {mousePressed = b;}
	public void setMouseReleased(boolean b) {mouseReleased = b;}
	
	public void clearPressedPoint() {pressedPoint = null;}
	public void clearReleasedPoint() {releasedPoint = null;}
	public void clearCurrentPoint() {currentPoint = null;}
}