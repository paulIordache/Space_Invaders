package application;

import javafx.scene.paint.Color;

import static application.SpaceInvaders.*;

public class Universe {
    int posX, posY;
    private final int h, w, r, g, b;
    private double opacity;

    public Universe() {
        posX = rand.nextInt(SpaceInvaders.getHeight());
        posY = 0;
        w = rand.nextInt(5) + 1;
        h =  rand.nextInt(5) + 1;
        r = rand.nextInt(100) + 150;
        g = rand.nextInt(100) + 150;
        b = rand.nextInt(100) + 150;
        opacity = rand.nextFloat();
        if(opacity < 0) opacity *=-1;
        if(opacity > 0.5) opacity = 0.5;
    }

    public void draw() {
        if(opacity > 0.8) opacity-=0.1;
        if(opacity < 0.1) opacity+=0.1;
        gc.setFill(Color.rgb(r, g, b, opacity));
        gc.fillOval(posX, posY, w, h);
        posY+=30;
    }
}