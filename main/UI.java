package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Font arial_80;
    Font arial_30;
    Font arial_15;
    BufferedImage keyImage;
    int messageCounter = 0;

    public boolean messageOn = false;
    public String message = "";

    public boolean gameFinished = false;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_80 = new Font("Arial", Font.BOLD, 80);
        arial_30 = new Font("Arial", Font.PLAIN, 30);
        arial_15 = new Font("Arial", Font.PLAIN, 15);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void showMessage (String text) {
        message = text;
        messageOn = true;
    }

    //Display the keys that the player currently has
    public void draw(Graphics2D g2) {

        if (gameFinished == true) {
            g2.setFont(arial_30);
            g2.setColor(Color.white);

            //We need to find the length of the text so we can adjust the message
            String text;
            int textLength;
            int x;
            int y;

            text = "You found the treasure!";
            //This returns the length of the text so we can subtract in the coordinates
            textLength = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();

            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text,x,y);

            g2.setFont(arial_80);
            g2.setColor(Color.YELLOW);
            text = "Congratulations!";
            //This returns the length of the text so we can subtract in the coordinates
            textLength = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();

            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*2);
            g2.drawString(text,x,y);

            gp.gameThread = null; //This stops the thread that runs the game

        }
        else {

        /*
         g2.setFont(new Font("Arial", Font.PLAIN, 40));
         Instantiating object in the game loop is not recommended
         because this method will be call 60 times (depending on the fps set) and
         will consume time creating the new object each time the method is called
         */
            g2.setFont(arial_30);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 69, 64);

            //MESSAGE
            if (messageOn == true) {
                g2.setFont(arial_15);
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

                messageCounter++;
                //The message disappears after 2 seconds (60fps*2=120)
                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }

    }
}
