package entity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

/**
 * DieImage class represents a die image
 * @author Vachia Thoj
 *
 */
public class DieImage 
{
	//x and y position of DieImage
	private int x;
	private int y;
	
	//width and height
	private int width;
	private int height;
	
	//Images of DieImage
	private BufferedImage[] images;
	private BufferedImage currentImage;
	
	//Speed of DieImage
	private double speed;
	
	//Change in x and y position
	private double dx;
	private double dy;
	
	//To count number of bounces
    private int numBounces;
    
    //Flag to see if rolling animation is occurring
    private boolean rolling;
	
    //Number of images
	private int size;
	
	/**
	 * Constructor
	 * @param images (BufferedImage) array of images for a die
	 */
	public DieImage(BufferedImage images[])
	{
		this.images = images;
		this.size = images.length;
		
		this.currentImage = images[0];
		this.width = currentImage.getWidth();
		this.height = currentImage.getHeight();
		
		this.speed = 15;
        this.dx = 0;
        this.dy = speed;
        
        this.numBounces = 0;
        this.rolling = false;
	}
	
	//Getter methods
	public int getX() {return x;}
	public int getY() {return y;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public BufferedImage getCurrentImage() {return currentImage;}
	public boolean isRolling() {return rolling;}
	
	//Setter methods
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public void setRolling(boolean b) {rolling = b;}
	
	public void changeCurrentImage(int index) {currentImage = images[index];}
	
	
	/**
	 * Method that updates the DieImage
	 */
	public void update()
	{
		//A bouncing animation
        //Check if ball's next position will go beyond the floor
        if(y + dy > ((GamePanel.HEIGHT / 2) - height))
        {
            if(numBounces < 6)
            {
                //Changes ball direction when hitting the floor
                dy = -dy * 0.75;
                ++numBounces;
                
                //Change image
                int value = (int)(Math.random() * size);
                changeCurrentImage(value);
            }
            else
            {
                dy = speed;
                y = ((GamePanel.HEIGHT / 2) - height);
                numBounces = 0;
                rolling = false;
            }
        }
        else
        {
            dy += 5;
        }
        
        //Set the ball's x and y position
        y += dy;
	}
	
	/**
	 * Method that draws the DieImage
	 * 
	 * @param g The Graphics2D object to be drawn on
	 */
	public void draw(Graphics2D g)
	{
		g.drawImage(currentImage, x, y, null);
	}
}
