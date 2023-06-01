package entity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import manager.MouseManager;

/**
 * Button class represents a Button to be clicked on
 * 
 * @author Vachia Thoj
 *
 */
public class Button 
{
	//x and y position of Button
	private int x;
	private int y;
	
	//width and height of Button
	private int width;
	private int height;
	
	//Button images
	private BufferedImage image1;
	private BufferedImage image2;
	private BufferedImage currentImage;
	
	//Flag to see if mouse is touching/clicking Button
	private boolean mouseTouchingButton;
	private boolean mouseClickingButton;
	
	//Flag to see if Button is disabled
	private boolean disabled;
	
	//Flag to see if Button is visible
	private boolean visible;
	
	//To manage mouse events
	private MouseManager mouseManager;
	
	/**
	 * Constructor
	 * @param image1 (BufferedImage) A button image
	 * @param image2 (BufferedImage) A button image
	 */
	public Button(BufferedImage image1, BufferedImage image2)
	{
		this.image1 = image1;
		this.image2 = image2;
		
		init();
	}
	
	/**
	 * Constructor
	 * @param x (integer) x coordinate of Button
	 * @param y (integer) y coordinate of Button
	 * @param image1 (BufferedImage) A button image
	 * @param image2 (BufferedImage) A button image
	 */
	public Button(int x, int y, BufferedImage image1, BufferedImage image2)
	{
		this.x = x;
		this.y = y;
		this.image1 = image1;
		this.image2 = image2;
		
		init();
	}
	
	/**
	 * Initializes various variables
	 * To be used in constructor
	 */
	private void init()
	{
		this.currentImage = image1;
		this.width = currentImage.getWidth();
		this.height = currentImage.getHeight();
		
		this.mouseTouchingButton = false;
		this.mouseClickingButton = false;
		
		this.disabled = false;
		this.visible = true;
		
		this.mouseManager = MouseManager.instance();
	}
	
	//Getter Methods
	public int getX() {return x;}
	public int getY() {return y;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public BufferedImage getCurrentImage() {return currentImage;}
	public boolean isMouseTouchingButton() {return mouseTouchingButton;}
	public boolean isMouseClickingButton() {return mouseClickingButton;}
	public boolean isDisabled() {return disabled;}
	public boolean isVisible() {return visible;}
	
	//Setter Methods
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public void setMouseTouchingButton(boolean b) {mouseTouchingButton = b;}
	public void setMouseClickingButton(boolean b) {mouseClickingButton = b;}
	public void setDisabled(boolean b) {disabled = b;}
	public void setVisible(boolean b) {visible = b;}
	
	/**
	 * Method that updates Button
	 */
	public void update()
	{
		//Do not update if Button is disabled nor visible
		if(disabled == true || visible == false)
		{
			if(mouseManager.getPressedPoint() != null && mouseManager.getReleasedPoint() != null)
    		{
    			//Pressed location
    	    	int pressedX = mouseManager.getPressedPoint().getX();
    	    	int pressedY = mouseManager.getPressedPoint().getY();
    	    	
    	    	//Released location
    	    	int releasedX = mouseManager.getReleasedPoint().getX();
    	    	int releasedY = mouseManager.getReleasedPoint().getY();
    	    	
    	    	//Check if mouse has pressed button
    	    	if(atLocation(pressedX, pressedY) == true && atLocation(releasedX, releasedY) == true)
    	    	{
    	    		mouseManager.clearPressedPoint();
    	    		mouseManager.clearReleasedPoint();
    	    	}
    		}
    		return;
		}
		
		//Check if mouse is touching Button
		if(mouseManager.getCurrentPoint() != null)
		{
			//x and y location of mouse point
			int mouseX = mouseManager.getCurrentPoint().getX();
			int mouseY = mouseManager.getCurrentPoint().getY();
			
			if(atLocation(mouseX, mouseY) == true)
			{
				mouseTouchingButton = true;
			}
			else
			{
				mouseTouchingButton = false;
			}
		}
		
		//Change currentImage depending on if mouse is touching button or not
		if(mouseTouchingButton == true && currentImage.equals(image1))
		{
			currentImage = image2;
			width = currentImage.getWidth();
			height = currentImage.getHeight();
		}
		else if(mouseTouchingButton == false && currentImage.equals(image2))
		{
			currentImage = image1;
			width = currentImage.getWidth();
			height = currentImage.getHeight();
		}
		
		//Check if mouse is clicking button
    	if(mouseManager.isMouseReleased() == true)
    	{
    		if(pressedAndReleased() == true)
    		{
    			mouseClickingButton = true;
	    		mouseManager.clearPressedPoint();
	    		mouseManager.clearReleasedPoint();
    		}
    	}
	}
	
	/**
	 * Method that draws Button
	 * 
	 * @param g The Graphics2D object that will be drawn on
	 */
	public void draw(Graphics2D g)
	{
		if(visible == true)
		{
			//Draw currentImage of Button
			g.drawImage(currentImage, x, y, null);
		}
	}
	
	/**
	 * Method that checks if mouse location is within (touching) Button
	 * 
	 * @param mouseX an integer; x position of mouse
	 * @param mouseY an integer; y position of mouse
	 * @return true if mouse location is within Button otherwise false
	 */
	private boolean atLocation(int mouseX, int mouseY)
	{
		if((mouseX >= x && mouseX <= (x + width)) && (mouseY >= y && mouseY <= (y + height)))
        {
            return true;
        }
        
        return false;
	}
	
	//Check if mouse has pressed and released Button
	private boolean pressedAndReleased()
	{
		if(mouseManager.getPressedPoint() != null && mouseManager.getReleasedPoint() != null)
		{
			//Pressed location
	    	int pressedX = mouseManager.getPressedPoint().getX();
	    	int pressedY = mouseManager.getPressedPoint().getY();
	    	
	    	//Released location
	    	int releasedX = mouseManager.getReleasedPoint().getX();
	    	int releasedY = mouseManager.getReleasedPoint().getY();
	    	
	    	//Check if mouse has pressed and released Button
	    	if(atLocation(pressedX, pressedY) == true && atLocation(releasedX, releasedY) == true)
	    	{
	    		return true;
	    	}
		}
		
		return false;
	}
}