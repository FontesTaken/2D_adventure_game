package main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	
	
	// SCREEN SETTINGS
	
	/*
	 * 16x16 tile. This is the default size for many 2D games
	 * This is going be the default size of the sprites.
	 */
	final int origianlTileSize = 16; 
	final int scale = 3; //This shows the sprites larger
	
	/*
	 * 48x48 Tile (16*3)
	 * Actual tile size that is displayed on the screen
	 */
	final int tileSize = origianlTileSize * scale; 
	final int maxScreenCol = 16; 
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	Thread gameThread; //Simulates the passage of time to update sprites to show "movement"
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start(); //Automatically calls the run() method
	}
	
	//When we start the gameThread, this method is automatically called
	public void run() {
		
	}
	
}
