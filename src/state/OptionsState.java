package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import button.*;
import helper.TextSize;
import main.GamePanel;
import manager.ImageManager;
import manager.MouseManager;
import manager.StateManager;
import manager.OptionsManager;
import transition.*;

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
	
	//The next state to go to
	private StateType nextState;
	
	//Texts
	private String titleText;
	private String dieColorText;
	private String targetScoreText;
	
	//Max number of colors for dice
	private static final int NUM_COLORS = 3;
	
	//Max number of target scores
	private static final int NUM_TARGETS = 2;
	
	//Possible color options for dice
	private ImageButton[] colorOptions;
	private int colorIndex;
	
	//Possible target score options for the game
	private ImageButton[] targetOptions;
	private int targetIndex;
	
	//Buttons
	private ImageButton menuButton;
	private ImageButton newGameButton;
	
	//Transitions
	private FadeToBlack fadeToBlack;
	
	/**
	 * Constructor
	 */
	public OptionsState()
	{
		this.imageManager = ImageManager.instance();
		this.optionsManager = OptionsManager.instance();
		
		this.nextState = null;
		
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
		
		createTexts();
		createOptions();
		createButtons();
		createTransitions();
	}
	
////////////////////////////////////////////// CREATE METHODS //////////////////////////////////////////////
	
	private void createButtons()
	{
		//Obtain button images
		BufferedImage[] buttons = imageManager.getButtons();
		
		//menuButton
		this.menuButton = new ImageButton(buttons[6], buttons[7]);
		this.menuButton.setX(10);
		this.menuButton.setY(GamePanel.HEIGHT - (menuButton.getHeight() + 10));
		
		//newGameButton
		this.newGameButton = new ImageButton(buttons[0], buttons[1]);
		this.newGameButton.setX(GamePanel.WIDTH - (newGameButton.getWidth() + 10));
		this.newGameButton.setY(GamePanel.HEIGHT - (newGameButton.getHeight() + 10));		
	}
	
	private void createOptions()
	{
		BufferedImage[] options = imageManager.getOptions();
		
		this.colorOptions = new ImageButton[NUM_COLORS];
		this.targetOptions = new ImageButton[NUM_TARGETS];
		
		//Dice color options
		this.colorOptions[0] = new ImageButton(750, 170, options[0], options[1]);
		this.colorOptions[1] = new ImageButton(750, 170, options[2], options[3]);	
		this.colorOptions[2] = new ImageButton(750, 170, options[4], options[5]);
		
		//Target score options
		this.targetOptions[0] = new ImageButton(750, 270, options[6], options[7]);
		this.targetOptions[1] = new ImageButton(750, 270, options[8], options[9]);
	}
	
	private void createTexts()
	{
		this.titleText = "OPTIONS";
		this.dieColorText = "Dice Color:";
		this.targetScoreText = "Target Score:";
	}
	
	private void createTransitions()
	{
		this.fadeToBlack = new FadeToBlack(GamePanel.WIDTH, GamePanel.HEIGHT);
	}
	
////////////////////////////////////////////// UPDATE METHODS //////////////////////////////////////////////
	
	/**
	 * Method that updates the Transitions
	 */
	private void updateTransitions()
	{
		fadeToBlack.update();
	}
	
	/**
	 * Method that checks if a change of state is necessary
	 */
	private void changeState()
	{	
		if(fadeToBlack.isDone() && nextState != null)
		{
			MouseManager.instance().clearPressedPoint();
			MouseManager.instance().clearReleasedPoint();
			
			StateManager.instance().changeState(nextState);
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
		
		//Check if an action needs to be performed
		performButtonAction();
	}
	
	/**
	 * Method that performs an action when a Button has been clicked
	 */
	private void performButtonAction()
	{
		if(menuButton.isMouseClickingButton() == true)
		{
			menuButton.setMouseClickingButton(false);
			
			//Run the fadeToBlack Transition
			fadeToBlack.setRunning(true);
			
			//Indicate that the next State to go to is the MainState
			nextState = StateType.MAIN;
		}
		else if(newGameButton.isMouseClickingButton() == true)
		{
			newGameButton.setMouseClickingButton(false);
			
			//Run to the fadeToBlack Transition
			fadeToBlack.setRunning(true);
			
			//Indicate that the next State to go to is the PlayState
			nextState = StateType.PLAY;
		}
	}
	
	/**
	 * Method that updates the color options
	 */
	private void updateColorOptions()
	{
		colorOptions[colorIndex].update();
		
		if(colorOptions[colorIndex].isMouseClickingButton())
		{
			colorOptions[colorIndex].setMouseClickingButton(false);
			
			++colorIndex;
			if(colorIndex >= NUM_COLORS)
			{
				colorIndex = 0;
			}
			
			return;
		}
	}
	
	/**
	 * Method that updates the target options
	 */
	private void updateTargetOptions()
	{
		targetOptions[targetIndex].update();
		
		if(targetOptions[targetIndex].isMouseClickingButton())
		{
			targetOptions[targetIndex].setMouseClickingButton(false);
			
			++targetIndex;
			if(targetIndex >= NUM_TARGETS)
			{
				targetIndex = 0;
			}
			
			return;
		}
	}
	
	/**
	 * Method that updates the optionsManager
	 */
	private void updateOptionsManager()
	{
		switch(targetIndex)
		{
			case 0:
				optionsManager.setTargetScore(100);
				break;
			case 1:
				optionsManager.setTargetScore(200);
				break;
			default:
				break;
		}
		
		switch(colorIndex)
		{
			case 0:
				optionsManager.setDiceColor(Color.WHITE);
				break;
			case 1:
				optionsManager.setDiceColor(Color.BLACK);
				break;
			case 2:
				optionsManager.setDiceColor(Color.RED);
				break;
			default:
				break;
		}
	}
		
	/**
	 * Method that updates the OptionsState
	 */
	public void update()
	{
		updateTransitions();
		
		if(fadeToBlack.isRunning())
		{
			return;
		}
		
		//Check if a change of State is necessary
		changeState();
		
		updateButtons();
		updateColorOptions();
		updateTargetOptions();
		updateOptionsManager();
	}
	
////////////////////////////////////////////// DRAW METHODS //////////////////////////////////////////////
	
	/**
	 * Method that draws the background
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawBackground(Graphics2D g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
	}
	
	/**
	 * Method that draws the titleText
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawTitleText(Graphics2D g)
	{
		//Draw Title
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier New", Font.BOLD, 64));
		int titleWidth = TextSize.getTextWidth(titleText, g);
		g.drawString(titleText, (GamePanel.WIDTH / 2) - (titleWidth / 2), 100);
	}
	
	/**
	 * Method that draws the dieColorText
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawDieColorText(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier New", Font.BOLD, 24));
		g.drawString(dieColorText, 400, 200);
	}
	
	/**
	 * Method that draws various Strings
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawTargetScoreText(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier New", Font.BOLD, 24));
		g.drawString(targetScoreText, 400, 300);
	}
	
	/**
	 * Method that draws the options
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawOptions(Graphics2D g)
	{
		//Draw options
		colorOptions[colorIndex].draw(g);
		targetOptions[targetIndex].draw(g);
	}
	
	/**
	 * Method that draws the Buttons
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawButtons(Graphics2D g)
	{
		//Draw Buttons
		menuButton.draw(g);
		newGameButton.draw(g);
	}
	
	/**
	 * Method that draws the Transitions
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawTransitions(Graphics2D g)
	{
		fadeToBlack.draw(g);
	}
	
	/**
	 * Method that draws the OptionsState
	 * @param g (Graphics2D) The Graphisc2D object to be drawn on
	 */
	public void draw(Graphics2D g)
	{
		drawBackground(g);
		drawTitleText(g);
		drawDieColorText(g);
		drawTargetScoreText(g);
		drawOptions(g);
		drawButtons(g);
		drawTransitions(g);
	}
}
