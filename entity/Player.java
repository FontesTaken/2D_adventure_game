package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0; //This indicates how many keys does the player have

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize/2);
        screenY = gp.screenHeight / 2 - (gp.tileSize/2);

        //We want to make this rectangle a bit smaller than the character, so it can be easier to pass obstacles
        //Collision area is the rectangle
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23; //Position on world map
        worldY = gp.tileSize * 21; //Position on world map
        speed = 4;
        direction = "down";
    }


    //Loads the player images
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(keyH.upPressed == true || keyH.downPressed == true ||
                keyH.rightPressed == true || keyH.leftPressed == true) { //Only when the user presses the keys that the sprite can change
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            }

            //Check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //Check object collision
            int objIndex = gp.cChecker.checkObject(this,true);
            pickupObject(objIndex);

            //If collision is false then the player can move
            if (collisionOn == false) {
                if (keyH.upPressed == true) {
                    worldY -= speed;
                } else if (keyH.downPressed == true) {
                    worldY += speed;
                } else if (keyH.rightPressed == true) {
                    worldX += speed;
                } else if (keyH.leftPressed == true) {
                    worldX -= speed;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) { //This means that every 12 frames  (cause the sprite counter is increasing every frame) the sprite will change
                if (spriteNum == 1) spriteNum = 2;
                else if (spriteNum == 2) spriteNum = 1;
                spriteCounter = 0;
            }
        }
    }

    public void pickupObject(int index) {
        if(index != 999) { //This means we touched an object cause the value changed
            String objectName = gp.obj[index].name;

            switch(objectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[index] = null;
                    gp.ui.showMessage("You picked up a key!");
                    break;
                case "Door":
                    if(hasKey > 0) {
                        gp.playSE(3);
                        gp.obj[index] = null;
                        hasKey--;
                        gp.ui.showMessage("You've opened the door!");
                    }else {
                        gp.ui.showMessage("You need a key");
                    }
                    break;
                case "Boots":
                    gp.ui.showMessage("You're faster now!");
                    gp.playSE(2);
                    speed += 2;
                    gp.obj[index] = null;
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }

        }
    }

    public void draw(Graphics g) {
        /*
        g.setColor(Color.white);

        // Draws a rectangle and fills it with the specified color
        g.fillRect(x, y, gp.tileSize, gp.tileSize);
         */

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if(spriteNum == 1) image = up1;
                if(spriteNum == 2) image = up2;
                break;
            case "down":
                if(spriteNum == 1) image = down1;
                if(spriteNum == 2) image = down2;
                break;
            case "left":
                if(spriteNum == 1) image = left1;
                if(spriteNum == 2) image = left2;
                    break;
            case "right":
                if(spriteNum == 1)  image = right1;
                if(spriteNum == 2)  image = right2;
                break;
            default:
                break;
        }

        g.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); //Draws an image on the screen
    }


}
