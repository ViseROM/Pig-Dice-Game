package manager;

import java.awt.Color;

/**
 * OptionsManager class manages options
 * @author Vachia Thoj
 *
 */
public class OptionsManager 
{
	//For singleton
	private static OptionsManager optionsManager;
	
	//Dice color
	private Color diceColor;
	
	//Default dice color
	private static final Color DEFAULT_COLOR = Color.WHITE;
	
	//Target Score
	private int targetScore;
	
	//Default target Score
	private static final int DEFAULT_SCORE = 100;
	
	/**
	 * Constructor
	 */
	private OptionsManager()
	{
		this.diceColor = DEFAULT_COLOR;
		this.targetScore = DEFAULT_SCORE;
	}
	
	/**
	 * Method to be called to obtain OptionsManager object (Singleton)
	 * @return OptionsManager object
	 */
	public static OptionsManager instance()
	{
		if(optionsManager == null)
		{
			optionsManager = new OptionsManager();
		}
		
		return optionsManager;
	}
	
	//Getter methods
	public Color getDiceColor() {return diceColor;}
	public int getTargetScore() {return targetScore;}
	
	//Setter methods
	public void setDiceColor(Color diceColor) {this.diceColor = diceColor;}
	public void setTargetScore(int targetScore) {this.targetScore = targetScore;}
}

