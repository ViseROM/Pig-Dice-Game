package state;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Button;
import helper.TextSize;
import main.GamePanel;
import manager.ImageManager;
import manager.StateManager;
import manager.OptionsManager;
import manager.TransitionManager;
import transition.*;

import java.awt.Color;
import java.awt.Font;

/**
 * OptionsState class represents the Options menu to be drawn on screen
 * @author Vachia Thoj
 *
 */
public class OptionsState extends State
{
	//To manage images
	private ImageManager imageManager;
	
	//To manage options
	private OptionsManager optionsManager;
	
	//To manage transitions
	private TransitionManager transitionManager;
	
	//Title of state
	private String title;
	
	private String dieColor;
	private String targetScore;
	
	//Max number of colors for dice
	private static final int NUM_COLORS = 3;
	
	//Max number of target scores
	private static final int NUM_TARGETS = 2;
	
	//Possible color options for dice
	private Button[] colorOptions;
	private int colorIndex;
	
	//Possible target score options for the game
	private Button[] targetOptions;
	private int targetIndex;
	
	//Buttons
	private Button menuButton;
	private Button newGameButton;
	
	//The next state to go to
	private StateType nextState;
	
	/**
	 * Constructor
	 */
	public OptionsState()
	{
		this.imageManager = ImageManager.instance();
		this.optionsManager = OptionsManager.instance();
		
		this.transitionManager = new TransitionManager(GamePanel.WIDTH, GamePanel.HEIGHT);
		this.transitionManager.setTransition(TransitionType.FADE_TO_BLACK);
		
		this.title = "OPTIONS";
		
		this.dieColor = "Dice Color:";
		this.targetScore = "Target Score:";
		
		if(optionsManager.getDiceColor() == Color.BLACK)
		{
			colorIndex = 1;
		}
		else if(optionsManager.getDiceColor() == Color.RED)
		{
			colorIndex = 2;
		}
		else
		{
			colorIndex = 0;
		}
		
		if(optionsManager.getTargetScore() == 200)
		{
			targetIndex = 1;
		}
		else
		{
			targetIndex = 0;
		}
		
		createOptions();
		
		//Create Buttons
		createButtons();
		
		this.nextState = null;
	}
	
	/**
	 * Method that creates Buttons
	 */
	private void createButtons()
	{
		//Obtain button images
		BufferedImage[] buttons = imageManager.getButtons();
		
		//menuButton
		menuButton = new Button(buttons[6], buttons[7]);
		menuButton.setX(10);
		menuButton.setY(GamePanel.HEIGHT - (menuButton.getHeight() + 10));
		
		//newGameButton
		newGameButton = new Button(buttons[0], buttons[1]);
		newGameButton.setX(GamePanel.WIDTH - (newGameButton.getWidth() + 10));
		newGameButton.setY(GamePanel.HEIGHT - (newGameButton.getHeight() + 10));
		
		buttons = null;
		
	}
	
	/**
	 * Method that creates options
	 */
	private void createOptions()
	{
		BufferedImage[] options = imageManager.getOptions();
		
		colorOptions = new Button[NUM_COLORS];
		targetOptions = new Button[NUM_TARGETS];
		
		//Dice color options
		colorOptions[0] = new Button(750, 170, options[0], options[1]);
		colorOptions[1] = new Button(750, 170, options[2], options[3]);	
		colorOptions[2] = new Button(750, 170, options[4], options[5]);
		
		//Target score options
		targetOptions[0] = new Button(750, 270, options[6], options[7]);
		targetOptions[1] = new Button(750, 270, options[8], options[9]);
		
		options = null;
	}
	
	/**
	 * Method that updates the optionsManager
	 */
	private void updateOptionsManager()
	{
		if(targetIndex == 0)
		{
			optionsManager.setTargetScore(100);
		}
		else if(targetIndex == 1)
		{
			optionsManager.setTargetScore(200);
		}
		
		if(colorIndex == 0)
		{
			optionsManager.setDiceColor(Color.WHITE);
		}
		else if(colorIndex == 1)
		{
			optionsManager.setDiceColor(Color.BLACK);
		}
		else if(colorIndex == 2)
		{
			optionsManager.setDiceColor(Color.RED);
		}
	}
	
	/**
	 * Method that updates Buttons
	 */
	private void updateButtons()
	{
		//Update Buttons
		menuButton.update();
		newGameButton.update();
	}
	
	/**
	 * Method that performs an action when a Button has been clicked
	 */
	private void buttonActions()
	{
		if(menuButton.isMouseClickingButton() == true)
		{
			//Set next state to MenuState
			nextState = StateType.MENU;
			
			menuButton.setMouseClickingButton(false);
			
			//Start transition
			transitionManager.startTransition();
			
			//Disable buttons
			menuButton.setDisabled(true);
			newGameButton.setDisabled(true);
			colorOptions[colorIndex].setDisabled(true);
			targetOptions[targetIndex].setDisabled(true);
		}
		else if(newGameButton.isMouseClickingButton() == true)
		{
			//Set next state to PlayState
			nextState = StateType.PLAY;
			
			newGameButton.setMouseClickingButton(false);
			
			//Start transition
			transitionManager.startTransition();
			
			//Disable buttons
			menuButton.setDisabled(true);
			newGameButton.setDisabled(true);
			colorOptions[colorIndex].setDisabled(true);
			targetOptions[targetIndex].setDisabled(true);
		}
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
	 * Method that updates the OptionsState
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
		
		//Update Buttons
		updateButtons();
		
		//Perform an action if a button has been clicked
		buttonActions();
		
		//Update options
		colorOptions[colorIndex].update();
		targetOptions[targetIndex].update();
		
		if(colorOptions[colorIndex].isMouseClickingButton())
		{
			colorOptions[colorIndex].setMouseClickingButton(false);
			
			++colorIndex;
			if(colorIndex >= NUM_COLORS)
			{
				colorIndex = 0;
			}
		}
		else if(targetOptions[targetIndex].isMouseClickingButton())
		{
			targetOptions[targetIndex].setMouseClickingButton(false);
			
			++targetIndex;
			if(targetIndex >= NUM_TARGETS)
			{
				targetIndex = 0;
			}
		}
		
		//Update OptionsManager
		updateOptionsManager();
	}
	
	/**
	 * Method that draws the background
	 * @param g The Graphics2D object to be drawn on
	 */
	private void drawBackground(Graphics2D g)
	{
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
	 * Method that draws various Strings
	 * @param g The Graphics2D object to be drawn on
	 */
	private void drawString(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier New", Font.BOLD, 24));
		g.drawString(dieColor, 400, 200);
		
		g.drawString(targetScore, 400, 300);
	}
	
	/**
	 * Method that draws the options
	 * @param g The Graphics2D object to be drawn on
	 */
	private void drawOptions(Graphics2D g)
	{
		//Draw options
		colorOptions[colorIndex].draw(g);
		targetOptions[targetIndex].draw(g);
	}
	
	/**
	 * Method that draws the Buttons
	 * @param g The Graphics2D object to be drawn on
	 */
	private void drawButtons(Graphics2D g)
	{
		//Draw Buttons
		menuButton.draw(g);
		newGameButton.draw(g);
	}
	
	/**
	 * Method that draws the OptionsState
	 * 
	 * @param g The Graphisc2D object to be drawn on
	 */
	public void draw(Graphics2D g)
	{
		//Draw Background
		drawBackground(g);
		
		//Draw Title
		drawTitle(g);
		
		//Draw Strings
		drawString(g);
		
		//Draw options
		drawOptions(g);
		
		//Draw Buttons
		drawButtons(g);
		
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
