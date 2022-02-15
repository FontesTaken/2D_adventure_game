package entity;

/*
    This class will store variables that will be used in player, monster and NPC classes
 */

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public  String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea; // we create this rectangle to detect collision

    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

}
