package state;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import entity.Button;
import helper.TextSize;
import main.GamePanel;
import manager.ImageManager;
import manager.StateManager;
import manager.TransitionManager;
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
	
	//To manage transitions
	private TransitionManager transitionManager;
	
	//Title of State
	private String title;
	
	//Stores rules text
	private ArrayList<String> rules;
	
	//Buttons
	private Button menuButton;
	private Button newGameButton;
	
	//The next state to go to
	private StateType nextState;
	
	/**
	 * Constructor
	 */
	public RulesState()
	{
		this.imageManager = ImageManager.instance();
		
		this.transitionManager = new TransitionManager(GamePanel.WIDTH, GamePanel.HEIGHT);
		this.transitionManager.setTransition(TransitionType.FADE_TO_BLACK);
		
		this.title = "RULES";
		
		//Create Buttons
		createButtons();
		
		//Load rules text
		loadRules();
		
		this.nextState = null;
	}
	
	/**
	 * Method that "creates" the Buttons
	 */
	private void createButtons()
	{
		//Obtain button images from imageManager
		BufferedImage[] buttons = imageManager.getButtons();
		
		//Create menuButton
		menuButton = new Button(buttons[6], buttons[7]);
		menuButton.setX(10);
		menuButton.setY(GamePanel.HEIGHT - (menuButton.getHeight() + 10));
		
		//Create newGameButton
		newGameButton = new Button(buttons[0], buttons[1]);
		newGameButton.setX(GamePanel.WIDTH - (newGameButton.getWidth() + 10));
		newGameButton.setY(GamePanel.HEIGHT - (newGameButton.getHeight() + 10));
		
		buttons = null;
		
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
	
	/**
	 * Method that updates the Buttons
	 */
	private void updateButtons()
	{
		//Update Buttons
		menuButton.update();
		newGameButton.update();
	}
	
	/**
	 * Method that performs an action if a button has been clicked
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
	 * Method that updates the RuleState
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
	 * Method that draws the rules
	 * @param g The Graphics2D object to be drawn on
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
	 * Method that draws the Buttonos
	 * @param g The graphics2D object to be drawn on
	 */
	private void drawButtons(Graphics2D g)
	{
		//Draw Buttons
		menuButton.draw(g);
		newGameButton.draw(g);
	}
	
	/**
	 * Method that draws everything within the RulesState
	 * 
	 * @param g The Graphics2D object that is will drawn to
	 */
	public void draw(Graphics2D g)
	{
		//Draw Background
		drawBackground(g);
		
		//Draw Title
		drawTitle(g);
		
		//Draw Rules text
		drawRules(g);
		
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
