package backend;

import helper.NumberGenerator;

/**
 * Die class represents a die 
 * 
 * @author Vachia THoj
 *
 */
public class Die 
{
	//Value of die
	private int value;
	
	//Minimum and maximum value of die
	private static final int MIN_VALUE = 1;
	private static final int MAX_VALUE = 6;
	
	//Percentage chance to modify value
	private static final int MODIFIER_PERCENTAGE = 7;
	
	/**
	 * Constructor
	 */
	public Die()
	{
		value = roll();
	}
	
	public int getValue() {return value;}
	
	/**
	 * Method that randomly generates a number between 1 and 6 (inclusive)
	 * 
	 * @return an integer between 1 and 6 (inclusive)
	 */
	public int roll()
	{	
		//Generate a number between MIN_VALUE and MAX_VALUE (inclusive)
		value = NumberGenerator.getRandomNumber(MIN_VALUE, MAX_VALUE);
		
		//Check if a 1 is rolled
		if(value == 1)
		{
			//Generate random number to see if die should change it's value
			int chanceToModify = NumberGenerator.getRandomNumber(0, 100);

			if(chanceToModify < MODIFIER_PERCENTAGE)
			{
				//Change value
				value += NumberGenerator.getRandomNumber(MIN_VALUE, MAX_VALUE - 1);
			}
		}
		
		return value;
	}
}
