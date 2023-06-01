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
		currentState = new MenuState();
	}
	
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
	 * 
	 * @param nextState the StateType to go to
	 */
	public void changeState(StateType nextState)
	{
		switch(nextState)
		{
			case MENU:
				currentState = new MenuState();
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
	 * @param g The Graphics2D object to be drawn on
	 */
	public void draw(Graphics2D g)
	{
		currentState.draw(g);
	}
}
