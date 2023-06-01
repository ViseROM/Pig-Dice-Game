package state;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import entity.Button;
import helper.TextSize;
import main.GamePanel;
import manager.ImageManager;
import manager.StateManager;
import manager.TransitionManager;
import transition.*;

/**
 * MenuState class represents what is going to be displayed on the Menu screen
 * 
 * @author Vachia Thoj
 *
 */
public class MenuState extends State 
{
	private String title;
	private String author;
	private String version;
	
	//To manage images
	private ImageManager imageManager;
	
	//To manage transitions
	private TransitionManager transitionManager;
	
	//Buttons
	private Button newGameButton;
	private Button rulesButton;
	private Button optionsButton;
	
	//The next state to go to
	private StateType nextState;
	
	/**
	 * Constructor
	 */
	public MenuState()
	{
		this.imageManager = ImageManager.instance();
		
		this.transitionManager = new TransitionManager(GamePanel.WIDTH, GamePanel.HEIGHT);
		this.transitionManager.setTransition(TransitionType.FADE_TO_BLACK);
		
		
		this.title = "PIG DICE GAME";
		this.author = "Vachia Thoj";
		this.version = "Ver. 1.0";
		
		this.nextState = null;
		
		//Create Buttons
		createButtons();
	}
	
	/**
	 * Method that creates buttons
	 */
	private void createButtons()
	{
		//Obtain button images
		BufferedImage buttonImages[] = imageManager.getButtons();
		
		//Create newGameButton
		newGameButton = new Button(buttonImages[0], buttonImages[1]);
		newGameButton.setX((GamePanel.WIDTH / 2) - (newGameButton.getWidth() / 2));
		newGameButton.setY((GamePanel.HEIGHT / 2) - (newGameButton.getHeight()));
		
		//Create rulesButton
		rulesButton = new Button(buttonImages[2], buttonImages[3]);
		rulesButton.setX((GamePanel.WIDTH / 2) - (rulesButton.getWidth() / 2));
		rulesButton.setY(newGameButton.getY() + newGameButton.getHeight() + 25);
		
		//Create optionsButton
		optionsButton = new Button(buttonImages[4], buttonImages[5]);
		optionsButton.setX((GamePanel.WIDTH / 2) - (optionsButton.getWidth() / 2));
		optionsButton.setY(rulesButton.getY() + rulesButton.getHeight() + 25);
	}
	
	/**
	 * Method that changes the state
	 */
	private void changeState()
	{
		if(nextState != null)
		{
			StateManager.instance().changeState(nextState);
		}
	}
	
	/**
	 * Method that updates buttons
	 */
	private void updateButtons()
	{
		//Update Button
		newGameButton.update();
		rulesButton.update();
		optionsButton.update();
	}
	
	/**
	 * Method that performs an action if a button has been clicked
	 */
	private void buttonActions()
	{
		if(newGameButton.isMouseClickingButton() == true)
		{
			nextState = StateType.PLAY;
			newGameButton.setMouseClickingButton(false);
			
			//Start transition
			transitionManager.startTransition();
			
			//Disable buttons
			newGameButton.setDisabled(true);
			rulesButton.setDisabled(true);
			optionsButton.setDisabled(true);
		}
		else if(rulesButton.isMouseClickingButton() == true)
		{
			nextState = StateType.RULES;
			rulesButton.setMouseClickingButton(false);
			
			//Start transition
			transitionManager.startTransition();
			
			//Disable buttons
			newGameButton.setDisabled(true);
			rulesButton.setDisabled(true);
			optionsButton.setDisabled(true);
			
		}
		else if(optionsButton.isMouseClickingButton() == true)
		{
			nextState = StateType.OPTIONS;
			optionsButton.setMouseClickingButton(false);
			
			//Start transition
			transitionManager.startTransition();
			
			//Disable buttons
			newGameButton.setDisabled(true);
			rulesButton.setDisabled(true);
			optionsButton.setDisabled(true);
		}
	}
	
	/**
	 * Method that updates MenuState
	 */
	public void update()
	{
		//If transition is finished
		if(transitionManager.isDone())
		{
			//Change state
			changeState();
		}
		
		if(transitionManager.isRunning())
		{
			//Update Transition
			transitionManager.update();
			return;
		}
		
		//Update Button
		updateButtons();
		
		//Perform an action if Button has been clicked
		buttonActions();
	}
	
	/**
	 * Method that draws the background
	 * @param g The Graphics2D object to be drawn on
	 */
	private void drawBackground(Graphics2D g)
	{
		//Draw Background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
	}
	
	/**
	 * Method that draws the title
	 * @param g The Graphics2D object to be drawn on
	 */
	private void drawTitle(Graphics2D g)
	{
		//Draw Title
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier New", Font.BOLD, 64));
		int titleWidth = TextSize.getTextWidth(title, g);
		g.drawString(title, (GamePanel.WIDTH / 2) - (titleWidth / 2), 100);
	}
	
	/**
	 * Method that draws the buttons
	 * @param g The Graphics2D object to be drawn on
	 */
	private void drawButtons(Graphics2D g)
	{
		//Draw Buttons
		newGameButton.draw(g);
		rulesButton.draw(g);
		optionsButton.draw(g);
	}
	
	/**
	 * Method that draws MenuState
	 * 
	 * @param g The Graphics2D object that is to be drawn on
	 */
	public void draw(Graphics2D g)
	{
		//Draw background
		drawBackground(g);
		
		//Draw title
		drawTitle(g);
		
		//Draw buttons
		drawButtons(g);
		
		//Draw author
		g.setColor(Color.BLACK);
        g.setFont(new Font("Courier New", Font.BOLD, 16));
        g.drawString(author, 5, GamePanel.HEIGHT - 5);
		
		//Draw version number
		g.setColor(Color.BLACK);
        g.setFont(new Font("Courier New", Font.BOLD, 16));
        int versionWidth = (int) g.getFontMetrics().getStringBounds(version, g).getWidth();
        g.drawString(version, GamePanel.WIDTH - versionWidth, GamePanel.HEIGHT - 5);
        
        if(transitionManager.isDone())
        {
        	g.setColor(Color.BLACK);
        	g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        }
        
        //If transition is running, draw Transition
      	if(transitionManager.isRunning() == true)
      	{
      		transitionManager.draw(g);
      	}
		
	}
}
