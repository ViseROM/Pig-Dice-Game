package manager;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * ImageManager class attempts to load images files and keeps track 
 * of them
 * 
 * @author Vachia Thoj
 *
 */
public class ImageManager 
{
	//For singleton
	private static ImageManager imageManager;
	
	//Stores image sheets
	private BufferedImage diceSheet;
	private BufferedImage buttonSheet;
	private BufferedImage optionsSheet;
	
	//Stores dice images
	private BufferedImage[] whiteDice;
	private BufferedImage[] blackDice;
	private BufferedImage[] redDice;
	
	//Stores button images
	private BufferedImage[] buttons;
	
	//Stores options images
	private BufferedImage[] options;
	
	/**
	 * Constructor
	 */
	private ImageManager()
	{
		this.whiteDice = new BufferedImage[6];
		this.redDice = new BufferedImage[6];
		this.blackDice = new BufferedImage[6];
		
		this.buttons = new BufferedImage[12];
		
		this.options = new BufferedImage[10];
		
		//Load image sheets
		this.diceSheet = loadImage("/images/DiceSheet.png");
		this.buttonSheet = loadImage("/images/ButtonSheet.png");
		this.optionsSheet = loadImage("/images/OptionsSheet.png");
		
		//Obtain subImages from image sheets
		loadDiceImages();
		loadButtonImages();
		loadOptionsImages();
	}
	
	/**
	 * Method to be called to obtain ImageManager object (Singleton)
	 * @return ImageManager object
	 */
	public static ImageManager instance()
	{
		if(imageManager == null)
		{
			imageManager = new ImageManager();
		}
		return imageManager;
	}
	
	//Getter methods
	public BufferedImage[] getWhiteDice() {return whiteDice;}
	public BufferedImage[] getBlackDice() {return blackDice;}
	public BufferedImage[] getRedDice() {return redDice;}
	public BufferedImage[] getButtons() {return buttons;}
	public BufferedImage[] getOptions() {return options;}
	
	/**
	 * Method that attempts to open an image file
	 * 
	 * @param address String of address location of image file
	 * @return The BufferedImage of image opened
	 */
	private BufferedImage loadImage(String address)
	{
		BufferedImage imageSheet = null;
		
		//Obtain images
        try{
            //Obtain image sheet from the image address
            imageSheet = ImageIO.read(getClass().getResourceAsStream(address));
            
            return imageSheet;
            
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error loading graphics");
            System.exit(0);
        }
        
        return null;
	}
	
	/**
	 * Method that obtains subimages from diceSheet image
	 */
	private void loadDiceImages()
	{
		for(int i = 0; i < 6; i++)
		{
			whiteDice[i] = diceSheet.getSubimage(i * 100, 0, 100, 100);
		}
		
		for(int i = 0; i < 6; i++)
		{
			blackDice[i] = diceSheet.getSubimage(i * 100, 100, 100, 100);
		}
		
		for(int i = 0; i < 6; i++)
		{
			redDice[i] = diceSheet.getSubimage(i * 100, 200, 100, 100);
		}
	}
	
	/**
	 * Method that obtain subimages from buttonSheet image
	 */
	private void loadButtonImages()
	{
		int index = 0;
		
		for(int i = 0; i < 2; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				buttons[index] = buttonSheet.getSubimage(j * 200, i * 50, 200, 50);
				++index;
			}
		}
		
		for(int i = 0; i < 4; i++)
		{
			buttons[index] = buttonSheet.getSubimage(i * 150, 100, 150, 50);
			++index;
		}
	}
	
	/**
	 * Method that obtains subimages from optionSheet images
	 */
	private void loadOptionsImages()
	{
		int index = 0;
		
		for(int i = 0; i < 6; i++)
		{
			options[index] = optionsSheet.getSubimage(i * 100, 0, 100, 32);
			++index;
		}
		
		for(int i = 0; i < 4; i++)
		{ 
			options[index] = optionsSheet.getSubimage(i * 100, 32, 100, 32);
			++index;
		}
	}
}
