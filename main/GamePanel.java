package main;

import entity.Player;
import tile.TileManager;

import java.awt.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	// SCREEN SETTINGS
	/*
	 * 16x16 tile. This is the default size for many 2D games
	 * This is going be the default size of the sprites.
	 */
	final int originalTileSize = 16; 
	final int scale = 3; //This shows the sprites larger
	
	/*
	 * 48x48 Tile (16*3)
	 * Actual tile size that is displayed on the screen
	 */
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	// SCREEN SETTINGS

	KeyHandler keyH = new KeyHandler();
	Thread gameThread; //Simulates the passage of time to update sprites to show "movement"
	Player player = new Player(this, keyH);
	TileManager tileM = new TileManager(this);

	//Set FPS
	int FPS = 60;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true); //With this, the GamePanel can be "focused" to receive key input
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start(); //Automatically calls the run() method
	}
	
	/*
	Used for the game loop
	When we start the gameThread, this method is automatically called
	If fps=30 then we update&draw 30 times per second
	If fps=60 then we update&draw 60 times per second
	*/
	public void run() {

		double drawInterval = 1000000000/FPS; // 1000000000 nanoseconds = 1 second, and we divide by 60 to get the draw interval. We can draw once every 0.01666 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		long drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();
			delta += (currentTime-lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if(delta >= 1) {
				// 1 UPDATE: update information such as character position
				update();
				// 2 DRAW: draw the screen with the updated information
				repaint(); //You use the paintComponent calling this method
				delta--;
				drawCount++;
			}

			//Displays FPS
			if (timer > 1000000000 ) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}


		}
	}

	public void update() {
		player.update();
	}

	/*
		Graphics: A class that has many functions to draw objects on the screen. This represents the pencil to draw
		Graphics2D: A class that extends the Graphics class to provide more control over geometry, coordinate transf., color manag. etc...
	 */
	public void paintComponent(Graphics g) {

		super.paintComponent(g); //This has to be always present when you use paintComponent(g)

		Graphics2D g2 = (Graphics2D) g;

		tileM.draw((Graphics2D) g); //This has to be first because it's the background
		player.draw(g2);

		//Dispose of this graphics context and release any system resources that it is using. Good practice to save memory
		g2.dispose();
	}
	
}
