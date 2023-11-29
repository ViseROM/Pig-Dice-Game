package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import button.*;
import context.Context;
import entity.DieObject;
import helper.TextSize;
import main.GamePanel;
import manager.ImageManager;
import manager.MouseManager;
import manager.StateManager;
import manager.OptionsManager;
import transition.*;

/**
 * PlayState class represents the "game board" for the game 
 * This is where the user sees the game being played
 * 
 * @author Vachia Thoj
 *
 */
public class PlayState extends State
{
	//To manage images
	private ImageManager imageManager;
	
	//To manage options
	private OptionsManager optionsManager;
	
	//The next state to go to
	private StateType nextState;
	
	//"Middleman" to communicate between frontend and backend
	private Context context;
	
	//Die images
	private DieObject die1;
	private DieObject die2;
	
	//Buttons
	private ImageButton menuButton;
	private ImageButton newGameButton;
	private ImageButton rollButton;
	private ImageButton stopButton;
	
	//Strings to be displayed
	private String player1;
	private String player2;
	private String player1Score;
	private String player2Score;
	
	//Flag to indicate if game is over
	private boolean gameOver;
	
	//Transitions
	private VerticalSplit verticalSplit;
	
	/**
	 * Constructor
	 */
	public PlayState()
	{
		this.imageManager = ImageManager.instance();
		this.optionsManager = OptionsManager.instance();
		
		this.nextState = null;
		
		createContext();
		createTexts();
		createDieObjects();
		createButtons();
		createTransitions();
		
        this.gameOver = false;
	}
	
////////////////////////////////////////////// CREATE METHODS //////////////////////////////////////////////
	
	private void createContext()
	{
		this.context = new Context();
		this.context.newGame();
	}
	
	private void createDieObjects()
	{
		BufferedImage[] dices;
		Color diceColor = optionsManager.getDiceColor();
		
		if(diceColor == Color.WHITE)
		{
			dices = imageManager.getWhiteDice();
		}
		else if(diceColor == Color.BLACK)
		{
			dices = imageManager.getBlackDice();
		}
		else if(diceColor == Color.RED)
		{
			dices = imageManager.getRedDice();
		}
		else
		{
			dices = imageManager.getWhiteDice();
		}
		
		//Create die1
		die1 = new DieObject(dices);
		die1.setX(((GamePanel.WIDTH / 2) - (die1.getWidth() / 2)) - 75);
        die1.setY((GamePanel.HEIGHT / 2) - die1.getHeight());
		
		//Create die2
		die2 = new DieObject(dices);
		die2.setX(((GamePanel.WIDTH / 2) - (die1.getWidth() / 2)) + 75);
        die2.setY((GamePanel.HEIGHT / 2) - die2.getHeight());
	}
	
	private void createTexts()
	{
		this.player1 = context.getPlayer1().getName();
		this.player2 = context.getPlayer2().getName();
		this.player1Score = String.valueOf(context.getPlayer1().getScore());
		this.player2Score = String.valueOf(context.getPlayer1().getScore());
	}

	private void createButtons()
	{	
		BufferedImage images[] = imageManager.getButtons();
		
		//Create menuButton
		this.menuButton = new ImageButton(images[6], images[7]);
		this.menuButton.setX(0);
		this.menuButton.setY(0);
		
		//Create newGameButton
		this.newGameButton = new ImageButton(images[0], images[1]);
		this.newGameButton.setX(GamePanel.WIDTH - newGameButton.getWidth());
		this.newGameButton.setY(0);
		
		//Create rollButton
		this.rollButton = new ImageButton(images[8], images[9]);
		this.rollButton.setX((GamePanel.WIDTH / 2) - (rollButton.getWidth() / 2));
		this.rollButton.setY(GamePanel.HEIGHT - (rollButton.getHeight() + 100));
		
		//Create stopButton
		this.stopButton = new ImageButton(images[10], images[11]);
		this.stopButton.setX((GamePanel.WIDTH / 2) - (stopButton.getWidth() / 2));
		this.stopButton.setY(GamePanel.HEIGHT - (stopButton.getHeight() + 25));
	}
	
	private void createTransitions()
	{
		this.verticalSplit = new VerticalSplit(GamePanel.WIDTH, GamePanel.HEIGHT);
	}
	
////////////////////////////////////////////// UPDATE METHODS //////////////////////////////////////////////
	
	private void updateTransitions()
	{
		verticalSplit.update();
	}
	
	/**
	 * Method that changes the state
	 */
	private void changeState()
	{
		if(verticalSplit.isDone() && nextState != null)
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
		rollButton.update();
		stopButton.update();
		
		//Checks to see if an action needs to be performed if a Button has been clicked
		performButtonAction();
	}
	
	/**
	 * Method that performs a button action it is clicked
	 */
	private void performButtonAction()
	{
		if(menuButton.isMouseClickingButton() == true)
		{
			menuButton.setMouseClickingButton(false);
			
			//Run the verticalSplit transition
			verticalSplit.setRunning(true);
			
			//Indicate that the next State to go to is the MainState
			nextState = StateType.MAIN;
		}
		else if(newGameButton.isMouseClickingButton() == true)
		{
			newGameButton.setMouseClickingButton(false);
			
			//Run the verticalSplit transition
			verticalSplit.setRunning(true);
			
			//Indicate that the next State to go to is the PlayState
			nextState = StateType.PLAY;
		}
		else if(rollButton.isMouseClickingButton() == true)
		{
			rollButton.setMouseClickingButton(false);
					
			//Disable buttons
			rollButton.setDisabled(true);
			stopButton.setDisabled(true);
					
			//Tell Dices to start rolling
			die1.setRolling(true);
		    die2.setRolling(true);
		        	
		    die1.setY(0 - die1.getHeight());
		    die2.setY(0 - die2.getHeight());
					
		}
		else if(stopButton.isMouseClickingButton() == true) //stopButton pressed
		{
			stopButton.setMouseClickingButton(false);
					
			//Tell context that player is done rolling
			context.doneRolling();
					
			//Check if game over
			if(context.isGameOver() == true)
			{
				gameOver = true;
			}
			else
			{
				//Tell context to go to next player's turn
				context.nextPlayer();
			}
		}
	}
	
	/**
	 * Method that updates the PlayState
	 */
	public void update()
	{	
		updateTransitions();
		
		if(verticalSplit.isRunning())
		{
			return;
		}
		
		changeState();
		
		updateButtons();
		
		//Rolling animation
        if(die1.isRolling() == true && die2.isRolling() == true)
        {
        	//Update dices
        	die1.update();
        	die2.update();
        	
        	if(die1.isRolling() == false && die2.isRolling() == false)
            {
        		//Roll dices
                context.roll();
                
                int die1Image = (context.getDie1Value() - 1);
                int die2Image = (context.getDie2Value() - 1);
                die1.changeCurrentImage(die1Image);
                die2.changeCurrentImage(die2Image);
                
                //Check if a pig was rolled
                int value = context.evaluateRoll();
                
                //If a pig was rolled
                if(value == 1 || value == 2)
                {
                	//Go to next player
                	context.nextPlayer();
                }
                
                //Enable buttons
                rollButton.setDisabled(false);
                stopButton.setDisabled(false);
            }
        }
        
        //Check if game is over
        if(gameOver == true)
        {
        	//Disable buttons
        	if(rollButton.isDisabled() == false && stopButton.isDisabled() == false)
        	{
        		rollButton.setDisabled(true);
            	stopButton.setDisabled(true);
        	}
        }
	}
	
////////////////////////////////////////////// DRAW METHODS //////////////////////////////////////////////
	
	/**
	 * Methods that draws the background
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawBackground(Graphics2D g)
	{
		//Draw Background
		if(context.getCurrentPlayer().getName().equals(context.getPlayer1().getName()))
		{
			g.setColor(new Color(240, 240, 240));
	        g.fillRect(0, 0, (GamePanel.WIDTH / 2), GamePanel.HEIGHT);
	        g.setColor(new Color(250, 250, 250));
	        g.fillRect((GamePanel.WIDTH / 2), 0, (GamePanel.WIDTH / 2), GamePanel.HEIGHT);
	        
	        g.setColor(Color.RED);
	        g.fillOval(200, 102, 16, 16);
		}
		else
		{
			g.setColor(new Color(250, 250, 250));
            g.fillRect(0, 0, (GamePanel.WIDTH / 2), GamePanel.HEIGHT);
            g.setColor(new Color(240, 240, 240));
            g.fillRect((GamePanel.WIDTH / 2), 0, (GamePanel.WIDTH / 2), GamePanel.HEIGHT);
            
            g.setColor(Color.RED);
	        g.fillOval(840, 102, 16, 16);
		}
	}
	
	/**
	 * Method that draws the Buttons
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawButtons(Graphics2D g)
	{
		 menuButton.draw(g);
		 newGameButton.draw(g);
	     rollButton.draw(g);
	     stopButton.draw(g);
	}
	
	/**
	 * Method that draws the dices
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawDices(Graphics2D g)
	{
		die1.draw(g);
        die2.draw(g);
	}
	
	/**
	 * Method that draws the Strings/texts
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawStrings(Graphics2D g)
	{
		int textWidth;
        g.setColor(Color.BLACK);
        g.setFont(new Font ("Courier New", Font.BOLD, 36));
        
        //Draw Player 1 Name and score
        textWidth = TextSize.getTextWidth(player1, g);
        g.drawString(player1, (GamePanel.WIDTH / 4) - (textWidth / 2), GamePanel.HEIGHT / 6);
        player1Score = String.valueOf(context.getPlayer1Score());
        textWidth = TextSize.getTextWidth(player1Score, g);
        g.drawString(player1Score, (GamePanel.WIDTH / 4) - (textWidth / 2), ((GamePanel.HEIGHT / 6) + 50));
        
        //Draw Player 2 Name and score
        textWidth = TextSize.getTextWidth(player2, g);
        g.drawString(player2, (GamePanel.WIDTH - (GamePanel.WIDTH / 4)) - (textWidth / 2), GamePanel.HEIGHT / 6);
        player2Score = String.valueOf(context.getPlayer2Score());
        textWidth = TextSize.getTextWidth(player2Score, g);
        g.drawString(player2Score, (GamePanel.WIDTH - (GamePanel.WIDTH / 4)) - (textWidth / 2), ((GamePanel.HEIGHT / 6) + 50));
        
        g.setFont(new Font ("Courier New", Font.BOLD, 24));
        //Draw Turn Score
        textWidth = TextSize.getTextWidth("Current", g);
        g.drawString("Current", ((GamePanel.WIDTH / 2) - (textWidth/ 2)), ((GamePanel.HEIGHT / 2) + 75));
        textWidth = TextSize.getTextWidth(String.valueOf(context.getTurnScore()), g);
        g.drawString(String.valueOf(context.getTurnScore()), ((GamePanel.WIDTH / 2) - (textWidth / 2)), ((GamePanel.HEIGHT / 2) + 125));
        
	}
	
	/**
	 * Method that draws information about who won
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawWinner(Graphics2D g)
	{
		if(context.getWinner() != null)
	     {
			 //Draw winner text
			 g.setColor(Color.RED);
			 g.setFont(new Font("Courier New", Font.BOLD, 36));
			 int textLength = TextSize.getTextWidth("WINNER!", g);
	        	
			 if(context.getWinner().getName().equals("Player 1") == true)
			 {
				 g.drawString("WINNER!", (GamePanel.WIDTH / 4) - (textLength / 2), GamePanel.HEIGHT / 3);
			 }
			 else if(context.getWinner().getName().equals("Player 2") == true)
			 {
				 g.drawString("WINNER!", (GamePanel.WIDTH - (GamePanel.WIDTH / 4)) - (textLength / 2), GamePanel.HEIGHT / 3);
			 }
	     }
	}
	
	/**
	 * Method that draws the Transitions
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	private void drawTransitions(Graphics2D g)
	{
		verticalSplit.draw(g);
	}
	
	/**
	 * Method that draws everything within the PlayState
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	public void draw(Graphics2D g)
	{
		drawBackground(g);
        drawButtons(g);
        drawDices(g);
        drawStrings(g);
        
        //Draw winner String if game is over
        if(gameOver == true)
        {
        	drawWinner(g);
        }
        
        drawTransitions(g);
	}
}
