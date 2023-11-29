package manager;
import java.awt.Graphics2D;

import state.*;

/**
 * StateManager class keeps track and manages the different states of the game
 * 
 * @author Vachia Thoj
 *
 */
public class StateManager 
{
	//For singleton
	private static StateManager stateManager;
	
	//Current State
	private State currentState;
	
	/**
	 * Constructor
	 */
	private StateManager()
	{
		this.currentState = new MainState();
	}
	
	/**
	 * Method to be called to obtain StateManager object (Singleton)
	 * @return StateManager object
	 */
	public static StateManager instance()
	{
		if(stateManager == null)
		{
			stateManager = new StateManager();
		}
		
		return stateManager;
	}
	
	/**
	 * Method that changes the current state
	 * @param nextState (StateType) the next State to go to
	 */
	public void changeState(StateType nextState)
	{
		switch(nextState)
		{
			case MAIN:
				currentState = new MainState();
				break;
			case PLAY:
				currentState = new PlayState();
				break;
			case RULES:
				currentState = new RulesState();
				break;
			case OPTIONS:
				currentState = new OptionsState();
				break;
			default:
				break;
		}
	}
	
	/**
	 * Method that updates the current state
	 */
	public void update()
	{
		currentState.update();
	}
	
	
	/**
	 * Method that draws the current state
	 * 
	 * @param g (Graphics2D) The Graphics2D object to be drawn on
	 */
	public void draw(Graphics2D g)
	{
		currentState.draw(g);
	}
}
