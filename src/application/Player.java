package application;

import application.SpaceInvaders;
import javafx.scene.image.Image;

import static application.SpaceInvaders.*;

public class Player {

    static final Image explosion_png = new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\expl.png");
    public int posX;
    int posY, size;
    boolean exploding, destroyed;
    Image img;
    int explosionStep = 0;

    public Player(int posX, int posY, int size,  Image image) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        img = image;
    }

    int getPosX() {
        return posX;
    }

    public Shot shoot() {
        return new Shot(posX + size / 2 - Shot.size / 2, posY - Shot.size);
    }

    public void update() {
        if(exploding) explosionStep++;
        destroyed = explosionStep > explosion_frames;
    }

    public void draw() {
        if(exploding) {
            gc.drawImage(explosion_png, explosionStep % explosion_col * explosion_width, ((double) explosionStep / explosion_row) * explosion_height + 1,
                    explosion_width, explosion_height,
                    posX, posY, size, size);
        }
        else {
            gc.drawImage(img, posX, posY, size, size);
        }
    }

    public boolean collision(Player other) {
        int d = distance(this.posX + size / 2, this.posY + size /2,
                other.posX + other.size / 2, other.posY + other.size / 2);
        return d < other.size / 2 + this.size / 2 ;
    }

    public void explode() {
        exploding = true;
        explosionStep = -1;
    }

}