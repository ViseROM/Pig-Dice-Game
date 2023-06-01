package backend;

import manager.OptionsManager;

/**
 * Game class manages the game state of the Pig Dice Game
 * @author Vachia Thoj
 *
 */
public class Game 
{
	//To manage options
	private OptionsManager optionsManager;
	
	//Players
	private Player player1;
	private Player player2;
	
	//Dices for game
	private Die die1;
	private Die die2;
	
	private RollEvaluator rollEvaluator;
	
	//Current Player rolling
	private Player currentPlayer;
	
	//A Player's score for the current turn
	private int turnScore;
	
	//Stores winner of game
	private Player winner;
	
	//Flag to indicate game over
	private boolean gameOver;
	
	//Score to reach in order to win
	private int targetScore;
	
	/**
	 * Constructor
	 */
	public Game()
	{
		this.optionsManager = OptionsManager.instance();
		this.targetScore = optionsManager.getTargetScore();
		
		this.player1 = new Player("Player 1");
		this.player2 = new Player("Player 2");
		
		this.rollEvaluator = new RollEvaluator();
		
		this.die1 = new Die();
		this.die2 = new Die();
		
		this.currentPlayer = player1;
		
		this.turnScore = 0;
		
		this.winner = null;
		this.gameOver = false;
	}
	
	//Getter methods
	public Player getPlayer1() {return player1;}
	public Player getPlayer2() {return player2;}
	public Die getDie1() {return die1;}
	public Die getDie2() {return die2;}
	public Player getCurrentPlayer() {return currentPlayer;}
	public int getTurnScore() {return turnScore;}
	public Player getWinner() {return winner;}
	public boolean isGameOver() {return gameOver;}
	
	/**
	 * Method that rolls both dices
	 */
	public void roll()
	{
		//Roll both dices
		die1.roll();
		die2.roll();
		
		//Add dice value to turnScore
		turnScore += die1.getValue() + die2.getValue();
	}
	
	/**
	 * Method that evaluates a dices roll
	 * Determines how many pigs were rolled
	 * @return (integer) the number of pigs that were rolled
	 */
	public int evaluateRoll()
	{
		rollEvaluator.setDice(die1, die2);
		
		int pigValue = rollEvaluator.getPigValue();
		
		switch(pigValue)
		{
			case 1:	//Rolled 1 pig
				turnScore = 0;
				return 1;
			case 2: //Rolled 2 pigs
				turnScore = 0;
				currentPlayer.setScore(0);
				return 2;
		}	
		
		//No pig was rolled
		return 0;
	}
	
	/**
	 * Method that executes certain actions when a roll has completed
	 */
	public void doneRolling()
	{
		//Add turnScore to players overall score
		currentPlayer.addToScore(turnScore);
		turnScore = 0;
		
		//Check if player has reached the target score
		if(currentPlayer.getScore() >= targetScore)
		{
			winner = currentPlayer;
			gameOver = true;
		}
	}
	
	/**
	 * Method that changes which player is rolling the dice
	 */
	public void nextPlayer()
	{
		if(currentPlayer.equals(player1) == true)
		{
			currentPlayer = player2;
		}
		else if(currentPlayer.equals(player2) == true)
		{
			currentPlayer = player1;
		}
	}
	
}
