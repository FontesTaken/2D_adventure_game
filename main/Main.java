package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame(); 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //This lets the window close when user clicks the close button
		window.setResizable(false); //Doesn't let the user resize the window
		window.setTitle("2D Adventure");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack(); //Causes this window to be sized to fit the preferred size and layouts of its subcomponents
		
		window.setLocationRelativeTo(null); //null = the window will be displayed at the center of the screen
		window.setVisible(true);

		gamePanel.startGameThread();
	}

}
