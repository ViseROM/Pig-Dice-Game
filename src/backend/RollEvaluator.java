package backend;
/**
 * RollEvaluator class determines if two dice rolled a pig
 * 
 * @author Vachia Thoj
 *
 */
public class RollEvaluator 
{
	//Die objects
	private Die die1;
	private Die die2;
	
	//Pig values
	private static final int PIG_ZERO = 0;
	private static final int PIG_ONE = 1;
	private static final int PIG_TWO = 2;
	
	/**
	 * Constructor
	 */
	public RollEvaluator()
	{
		this.die1 = null;
		this.die2 = null;
	}
	
	/**
	 * 
	 * @param die1 (Die) a Die object
	 * @param die2 (Die) a Die object
	 */
	public RollEvaluator(Die die1, Die die2)
	{
		this.die1 = die1;
		this.die2 = die2;
	}
	
	//Getter methods
	public Die getDie1() {return die1;}
	public Die getDie2() {return die2;}
	
	//Setter methods
	public void setDie1(Die die1) {this.die1 = die1;}
	public void setDie2(Die die2) {this.die2 = die2;}
	public void setDice(Die die1, Die die2) {this.die1 = die1; this.die2 = die2;}
	
	/**
	 * Method that determines if a Pig was rolled
	 * 
	 * @return integer that tells how many pigs were rolled
	 */
	public int getPigValue()
	{
		if(die1.getValue() == 1 && die2.getValue() != 1) //Rolled 1 pig
		{
			return PIG_ONE;
		}
		else if(die1.getValue() != 1 && die2.getValue() == 1) // Rolled 1 pig
		{
			return PIG_ONE;
		}
		else if(die1.getValue() == 1 && die2.getValue() == 1) // Rolled 2 pigs
		{
			return PIG_TWO;
		}
		
		//Rolled 0 pigs
		return PIG_ZERO;
	}
}
