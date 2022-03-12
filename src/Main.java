import javax.swing.JFrame;

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
				
		window.setResizable(false);
				
		//Center window to computer screen
		window.setLocationRelativeTo(null);
				
		//Make window visible
		window.setVisible(true);
	}
}
