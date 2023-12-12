package application;

import application.SpaceInvaders;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static application.SpaceInvaders.gc;

public class StartingScreen {
    public void draw() {
        gc.setFont(Font.font(60));
        gc.setFill(Color.RED);
        gc.fillText("STAR WARS", (double) SpaceInvaders.getWidth() / 2,
                (double) SpaceInvaders.getHeight() / 2);
    }

}
