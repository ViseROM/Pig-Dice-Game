package context;

import backend.Die;
import backend.Game;
import backend.Player;

/**
 * Context connects the front end to back end 
 * 
 * @author Vachia Thoj
 *
 */
public class Context 
{
	//Game
	private Game game;
	
	/**
	 * Constructor
	 */
	public Context()
	{
		this.game = new Game();
	}
	
	//Getter methods
	public Player getPlayer1() {return game.getPlayer1();}
	public Player getPlayer2() {return game.getPlayer2();}
	public int getPlayer1Score() {return game.getPlayer1().getScore();}
	public int getPlayer2Score() {return game.getPlayer2().getScore();}
	public Player getCurrentPlayer() {return game.getCurrentPlayer();}
	public Die getDie1() {return game.getDie1();}
	public Die getDie2() {return game.getDie2();}
	public int getDie1Value() {return game.getDie1().getValue();}
	public int getDie2Value() {return game.getDie2().getValue();}
	public int getTurnScore() {return game.getTurnScore();}
	public Player getWinner() {return game.getWinner();}
	public boolean isGameOver() {return game.isGameOver();}
	
	public void roll() {game.roll();}
	public int evaluateRoll() {return game.evaluateRoll();}
	public void doneRolling() {game.doneRolling();}
	public void nextPlayer() {game.nextPlayer();}
	public void newGame() {game = new Game();}
}
