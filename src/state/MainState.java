package state;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import button.*;
import helper.TextSize;
import main.GamePanel;
import manager.ImageManager;
import manager.MouseManager;
import manager.StateManager;
import transition.*;

/**
 * MainState class represents what is going to be displayed on the Main screen
 * 
 * @author Vachia Thoj
 *
 */
public class MainState extends State 
{
	//Texts
	private String titleText;
	private String authorText;
	private String versionText;
	
	//To manage images
	private ImageManager imageManager;
	
	//The next state to go to
	private StateType nextState;
	
	//Buttons
	private ImageButton newGameButton;
	private ImageButton rulesButton;
	private ImageButton optionsButton;
	
	//Transitions
	private FadeToBlack fadeToBlack;
	
	/**
	 * Constructor
	 */
	public MainState()
	{
		this.imageManager = ImageManager.instance();
		
		this.nextState = null;
		
		createTexts();
		createButtons();
		createTransitions();
	}
	
////////////////////////////////////////////// CREATE METHODS //////////////////////////////////////////////
	private void createTexts()
	{
		this.titleText = "PIG DICE GAME";
		this.authorText = "VISE";
		this.versionText = "Ver. 1.0";
	}
	
	/**
	 * Method that creates buttons
	 */
	private void createButtons()
	{
		//Obtain button images
		BufferedImage buttonImages[] = imageManager.getButtons();
		
		//Create newGameButton
		newGameButton = new ImageButton(buttonImages[0], buttonImages[1]);
		newGameButton.setX((GamePanel.WIDTH / 2) - (newGameButton.getWidth() / 2));
		newGameButton.setY((GamePanel.HEIGHT / 2) - (newGameButton.getHeight()));
		
		//Create rulesButton
		rulesButton = new ImageButton(buttonImages[2], buttonImages[3]);
		rulesButton.setX((GamePanel.WIDTH / 2) - (rulesButton.getWidth() / 2));
		rulesButton.setY(newGameButton.getY() + newGameButton.getHeight() + 25);
		
		//Create optionsButton
		optionsButton = new ImageButton(buttonImages[4], buttonImages[5]);
		optionsButton.setX((GamePanel.WIDTH / 2) - (optionsButton.getWidth() / 2));
		optionsButton.setY(rulesButton.getY() + rulesButton.getHeight() + 25);
	}
	
	private void createTransitions()
	{
		this.fadeToBlack = new FadeToBlack(GamePanel.WIDTH, GamePanel.HEIGHT);
	}
	
////////////////////////////////////////////// UPDATE METHODS //////////////////////////////////////////////
	
	/**
	 * Method that updates Transitions
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
			
			//Go to the next state
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
		
		//Check if an action needs to be performed
		performButtonAction();
	}
	
	/**
	 * Method that performs an action if a button has been clicked
	 */
	private void performButtonAction()
	{
		if(newGameButton.isMouseClickingButton())
		{
			newGameButton.setMouseClickingButton(false);
			
			//Run the fadeToBlack transition
			fadeToBlack.setRunning(true);
			
			//Indicate that the next State to go to is the PlayState
			nextState = StateType.PLAY;
		}
		else if(rulesButton.isMouseClickingButton())
		{
			rulesButton.setMouseClickingButton(false);
			
			//Run the fadeToBlack transition
			fadeToBlack.setRunning(true);
			
			//Indicate that the next state to go to is the RulesState
			nextState = StateType.RULES;	
		}
		else if(optionsButton.isMouseClickingButton() == true)
		{
			optionsButton.setMouseClickingButton(false);
			
			//Run the fadeToBlack transition
			fadeToBlack.setRunning(true);
			
			//Indicate that the next State to go to is the OptionsState
			nextState = StateType.OPTIONS;
		}
	}
	
	/**
	 * Method that updates MenuState
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
	}
	
////////////////////////////////////////////// DRAW METHODS //////////////////////////////////////////////
	
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
	 * Method that draws the titleText
	 * @param g The Graphics2D object to be drawn on
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
	 * Method that draws the authorText
	 * @param g The Graphics2D object to be drawn on
	 */
	private void drawAuthorText(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier New", Font.BOLD, 16));
        g.drawString(authorText, 5, GamePanel.HEIGHT - 5);
	}
	
	/**
	 * Method that draws the versionText
	 * @param g The Graphics2D object to be drawn on
	 */
	private void drawVersionText(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier New", Font.BOLD, 16));
		int versionWidth = (int) g.getFontMetrics().getStringBounds(versionText, g).getWidth();
		g.drawString(versionText, GamePanel.WIDTH - versionWidth, GamePanel.HEIGHT - 5);
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
	 * Method that draws the Transitions
	 * @param g (Graphics2D g) The Graphics2D object to be drawn on
	 */
	private void drawTransitions(Graphics2D g)
	{
		fadeToBlack.draw(g);
	}
	
	/**
	 * Method that draws MainState
	 * 
	 * @param g The Graphics2D object that is to be drawn on
	 */
	public void draw(Graphics2D g)
	{
		drawBackground(g);
		drawTitleText(g);
		drawAuthorText(g);
		drawVersionText(g);
		drawButtons(g);
		drawTransitions(g);
	}
}
