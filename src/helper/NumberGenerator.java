package helper;

import java.util.Random;

/**
 * NumberGenerator class is a helper class that generate a random number
 * 
 * @author Vachia Thoj
 *
 */
public class NumberGenerator 
{
	/**
	 * Method that generates a random integer number
	 * @param min (integer) the smallest possible number to generate (inclusive)
	 * @param max (integer) the largest possible number to generate (inclusive)
	 * @return number (integer) a random number
	 */
	public static int getRandomNumber(int min, int max)
	{
		Random random = new Random();
		int number = random.nextInt((max - min) + 1) + min;
		
		return number;
	}
}
