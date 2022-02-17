package main;

import entity.Player;
import object.OBJ_Key;
import object.SuperObject;
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


	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	// WORLD SETTINGS


	//SYSTEM
	KeyHandler keyH = new KeyHandler();
	public TileManager tileM = new TileManager(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public UI ui = new UI(this);
	Thread gameThread; //Simulates the passage of time to update sprites to show "movement"
	//SYSTEM


	//ENTITY AND OBJECTS
	public Player player = new Player(this, keyH);
	/*
	10 slots for objects to be put in. Kinda works like the inventory in a game.
	If we pick up object "a" then he disappears from thegame and is put here
	 */
	public SuperObject obj[] = new SuperObject[10];
	//ENTITY AND OBJECTS


	//Set FPS
	int FPS = 60;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true); //With this, the GamePanel can be "focused" to receive key input
	}

	public void setupGame() {

		aSetter.setObject(); //We create this method, so we can add other objects in the future
							 //We want to call this setupGame method before the game starts

		playMusic(0); //This plays the background music in a loop

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

		//TILE
		tileM.draw(g2); //This has to be first because it's the background

		//OBJECT
		for (int i = 0; i < obj.length; i++) {
			if(obj[i] != null) { // Check if the slot is not empty to avoid NullPointer error
				obj[i].draw(g2,this);
			}
		}

		//PLAYER
		player.draw(g2);

		//UI
		ui.draw(g2);

		//Dispose of this graphics context and release any system resources that it is using. Good practice to save memory
		g2.dispose();
	}

	//Play the background music
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic() {
		music.stop();
	}

	//Play a sound effect
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
	
}
