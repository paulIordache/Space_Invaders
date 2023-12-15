package application;

import javafx.scene.image.Image;

import static application.SpaceInvaders.*;
import static application.SpaceInvaders.gc;

public class DeathStar {
    static final Image explosion2_png = new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\explosion.png");
    public int posX;
    int posY, size;
    boolean exploding = false, destroyed;
    Image img;
    int explosionStepDeath = 0;

    public DeathStar(int posX, int posY, int size, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        img = image;
    }
    public void update() {
        if(exploding) explosionStepDeath++;
        destroyed = explosionStepDeath > explosionDeath_frames;
    }

    public void draw() {
        if(exploding) {
            gc.drawImage(explosion2_png, (explosionStepDeath % explosion_rowD) * explosion_widthD + 1,
                    ( explosionStepDeath / explosion_colD) * explosion_heightD + 1,
                    explosion_widthD, explosion_heightD,
                    posX, posY, size, size);
        }
        else {
            gc.drawImage(img, posX, posY, size, size);
        }
    }

    public void explode() {
        exploding = true;
        explosionStepDeath = -1;
    }
}
