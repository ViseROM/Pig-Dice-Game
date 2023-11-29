package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import button.*;
import helper.TextSize;
import main.GamePanel;
import manager.ImageManager;
import manager.MouseManager;
import manager.StateManager;
import transition.*;

/**
 * RuleState class displays to the screen the rules of the game 
 * 
 * @author Vachia Thoj
 *
 */
public class RulesState extends State
{
	//To manage images
	private ImageManager imageManager;
	
	//The next state to go to
	private StateType nextState;
	
	//Title of State
	private String titleText;
	
	//Stores rules text
	private ArrayList<String> rules;
	
	//Buttons
	private ImageButton menuButton;
	private ImageButton newGameButton;
	
	//Transitions
	private FadeToBlack fadeToBlack;
	
	/**
	 * Constructor
	 */
	public RulesState()
	{
		this.imageManager = ImageManager.instance();
		
		this.nextState = null;
		
		createTexts();
		createButtons();
		createTransitions();
		loadRules();
	}
	
////////////////////////////////////////////// CREATE METHODS //////////////////////////////////////////////
	
	private void createTexts()
	{
		this.titleText = "RULES";
	}
	
	private void createButtons()
	{
		//Obtain button images from imageManager
		BufferedImage[] buttons = imageManager.getButtons();
		
		//Create menuButton
		this.menuButton = new ImageButton(buttons[6], buttons[7]);
		this.menuButton.setX(10);
		this.menuButton.setY(GamePanel.HEIGHT - (menuButton.getHeight() + 10));
		
		//Create newGameButton
		this.newGameButton = new ImageButton(buttons[0], buttons[1]);
		this.newGameButton.setX(GamePanel.WIDTH - (newGameButton.getWidth() + 10));
		this.newGameButton.setY(GamePanel.HEIGHT - (newGameButton.getHeight() + 10));
	}
	
	private void createTransitions()
	{
		this.fadeToBlack = new FadeToBlack(GamePanel.WIDTH, GamePanel.HEIGHT);
	}
	
	/**
	 * Method that loads the rules into "memory"
	 */
	private void loadRules()
	{
		rules = new ArrayList<String>();
		
		//Obtain rules file
		try {
			
			InputStream inputStream = getClass().getResourceAsStream("/files/rules.txt");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			
			String line = bufferedReader.readLine();
			
			while(line != null)
			{
				rules.add(line);
				line = bufferedReader.readLine();
			}
			
			inputStream.close();
			bufferedReader.close();
			
		}catch(FileNotFoundException e) {
			System.out.println("Error obtaining rules file");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
			//Go to the next State
			StateManager.instance().changeState(nextState);
		}
	}
	
	/**
	 * Method that updates the Buttons
	 */
	private void updateButtons()
	{	
		menuButton.update();
		newGameButton.update();
		
		performButtonAction();
	}
	
	/**
	 * Method that performs an action if a button has been clicked
	 */
	private void performButtonAction()
	{
		if(menuButton.isMouseClickingButton() == true)
		{
			menuButton.setMouseClickingButton(false);
			
			//Run the fadeToBlack transition
			fadeToBlack.setRunning(true);
			
			//Indicate that the next State to go to is MainState
			nextState = StateType.MAIN;
		}
		else if(newGameButton.isMouseClickingButton() == true)
		{
			newGameButton.setMouseClickingButton(false);
			
			//Run the fadeToBlack transition
			fadeToBlack.setRunning(true);
			
			//Indicate that the next State to go to is PlayState
			nextState = StateType.PLAY;
		}
	}
	
	/**
	 * Method that updates the RuleState
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
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawBackground(Graphics2D g)
	{
		//Draw Background
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
	 * Method that draws the rules
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawRules(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier New", Font.PLAIN, 24));
		
		for(int i = 0; i < rules.size(); i++)
		{
			g.drawString(rules.get(i), 20, (150 + (i * 30)));
		}
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
	 * Method that draws everything within the RulesState
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	public void draw(Graphics2D g)
	{
		drawBackground(g);
		drawTitleText(g);
		drawRules(g);
		drawButtons(g);
		drawTransitions(g);
	}
}
