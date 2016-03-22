package Game.Entity.Enemies;

import Game.Entity.Animation;
import Game.Entity.Enemy;
import Game.TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;


        import Game.Entity.*;
        import Game.TileMap.TileMap;

        import javax.imageio.ImageIO;
        import java.awt.Graphics2D;
        import java.awt.image.BufferedImage;

public class redSpider extends Enemy {

    private BufferedImage[] sprites;

    public redSpider (TileMap tm) {

        super(tm);

        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.2;
        maxFallSpeed = 10;

        width = 66;
        height = 50;
        cwidth = 40;
        cheight = 35;

        health = maxHealth = 2;
        damage = 1;

        //load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/redSpider.png"));

            sprites = new BufferedImage[8];

            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(150);

        right = true;
        facingRight = true;

    }

    private void getNextPosition() {
        if (left) {
            dx -= moveSpeed;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        }

        if (falling) {
            dy += fallSpeed;
        }


    }

    public void update() {
        //update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        //check flinching
        if (flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed > 400) {
                flinching = false;
            }
        }

        //wall collision (flip directions)
        if (right && dx == 0) {
            right = false;
            left = true;
            facingRight = false;
        } else if (left && dx == 0) {
            right = true;
            left = false;
            facingRight = true;
        }

        animation.update();



    }

    public void draw(Graphics2D g) {

        //if (notOnScreen()) return;

        setMapPosition();

        super.draw(g);


    }


}
