package application;

import application.Player;
import javafx.scene.image.Image;
import static application.SpaceInvaders.score;

public class Enemy extends Player {

    int SPEED = (score/5)+2;

    public Enemy(int posX, int posY, int size, Image image) {
        super(posX, posY, size, image);
    }

    public void update() {
        super.update();
        if(!exploding && !destroyed) posY += SPEED;
        if(posY > SpaceInvaders.getHeight()) destroyed = true;
    }
}