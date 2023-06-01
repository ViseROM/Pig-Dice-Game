package backend;
/**
 * Player class represents a Player playing the game
 * @author Vachia Thoj
 *
 */
public class Player 
{
	//Name of player
	private String name;
	
	//The Player's score
	private int score;
	
	/**
	 * Constructor
	 * @param name (String) the name of the Player
	 */
	public Player(String name)
	{
		this.name = name;
		
		this.score = 0;
	}
	
	//Getter Methods
	public String getName() {return name;}
	public int getScore() {return score;}
	
	//Setter Methods
	public void setName(String name) {this.name = name;}
	public void setScore(int score) {this.score = score;}
	
	public void addToScore(int amount) {score += amount;}
	public void removeFromScore(int amount) {score -= amount;}
}
