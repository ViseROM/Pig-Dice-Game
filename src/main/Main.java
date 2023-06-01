package main;
import javax.swing.JFrame;

/**
 * Main is the class that you run/execute in order to start the program
 *  
 * @author Vachia Thoj
 *
 */
public class Main 
{
	public static void main(String args[])
	{
		//Create window
		JFrame window = new JFrame("Pig Dice Game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		//Create GamePanel object
		GamePanel gamePanel = new GamePanel();
				
		//Place gamePanel in content pane
		window.setContentPane(gamePanel);
				
		window.pack();
		
		//Do not allow window to be resizable
		window.setResizable(false);
				
		//Center window to computer screen
		window.setLocationRelativeTo(null);
				
		//Make window visible
		window.setVisible(true);
	}
}
