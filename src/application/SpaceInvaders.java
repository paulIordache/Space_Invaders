package application;

import java.awt.Button;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SpaceInvaders extends Application {

    //variables
    static final Random rand = new Random();
    private static final int width = 1280;
    private static  final int height = 720;
    private static final int player_size = 60;
    static final Image player_png = new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\player.png");
    static final Image explosion_png = new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\explosion.png");
    static final Image[] enemies_png = {
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\retro.png"),
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\retro2.png"),
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\retro3.png"),
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\retro4.png"),
    };
    static final int explosion_width = 128;
    static final int explosion_row = 3;
    static final int explosion_col = 3;
    static final int explosion_height = 128;
    public static final int explosion_frames = 15;


    public static int getHeight() {
        return height;
    }


    final int max_enemies = 10,  max_shots = max_enemies * 2;
    boolean gameOver = false;
    static GraphicsContext gc;

    Player player;
    List<Shot> shots;
    List<Universe> univ;
    List<Enemy> Enemies;

    private double mouseX;
    static int score;

    //start
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnMouseMoved(e -> mouseX = e.getX());
//        canvas.setOnKeyPressed(e -> {
//            if(e.getCode() == KeyCode.RIGHT){
//                player.posX = player.posX + 10;
//                //circle.setTranslateX(newX);
//            }
//            else if(e.getCode() == KeyCode.LEFT){
//                player.posX = player.posX - 10;
//                //circle.setTranslateX(newX);
//            }
//            else if(e.getCode() == KeyCode.UP){
//                player.posY = player.posY - 10;
//                //circle.setTranslateY(newY);
//            }
//            else if(e.getCode() == KeyCode.DOWN){
//                player.posY = player.posY + 10;
//                //circle.setTranslateY(newY);
//            }
//        });
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);
        canvas.setOnMouseClicked(e -> {
                if(shots.size() < max_shots) shots.add(player.shoot());
                if(gameOver) {
                    gameOver = false;
                    setup();
                }
        });



        setup();
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setTitle("Space Invaders");
        stage.show();

    }

    public void keyPressed(KeyEvent evt) {
        KeyCode key = evt.getCode();
        System.out.println("Key Pressed: " + key);

        if (key == KeyCode.SPACE) {
            if(shots.size() < max_shots) shots.add(player.shoot());
            if(gameOver) {
                gameOver = false;
                setup();
            }
        }
    }

    //setup the game
    private void setup() {
        univ = new ArrayList<>();
        player = new Player(width / 2, height - player_size, player_size, player_png);
        shots = new ArrayList<>();
        Enemies = new ArrayList<>();

        score = 0;
        IntStream.range(0, max_enemies).mapToObj(i -> this.newEnemy()).forEach(Enemies::add);
    }

    //run Graphics
    private void run(GraphicsContext gc) {
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, width, height);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, 60,  20);


        if(gameOver) {
            gc.setFont(Font.font(35));
            gc.setFill(Color.YELLOW);
            gc.fillText("Game Over \n Your Score is: " + score + " \n Click to play again", (double) width / 2, height /2.5);
            //	return;
        }
        univ.forEach(Universe::draw);

        player.update();
        player.draw();
        player.posX = (int) mouseX;

        Enemies.stream().peek(Player::update).peek(Player::draw).forEach(e -> {
            if(player.collision(e) && !player.exploding) {
                player.explode();
            }
        });
        


        for (int i = shots.size() - 1; i >=0 ; i--) {
            Shot shot = shots.get(i);
            if(shot.posY < 0 || shot.toRemove)  {
                shots.remove(i);
                continue;
            }
            shot.update();
            shot.draw();

            for (Enemy enemy : Enemies) {
                if(shot.collide(enemy) && !enemy.exploding) {
                    score++;
                    enemy.explode();
                    shot.toRemove = true;
                }
            }
        }

        for (int i = Enemies.size() - 1; i >= 0; i--){
            if(Enemies.get(i).destroyed)  {
                Enemies.set(i, newEnemy());
            }
        }

        gameOver = player.destroyed;
        if(rand.nextInt(10) > 2) {
            univ.add(new Universe());
        }
        for (int i = 0; i < univ.size(); i++) {
            if(univ.get(i).posY > height)
                univ.remove(i);
        }
    }

    //player


    //computer player


    //bullets

    //environment


    Enemy newEnemy() {
        return new Enemy(150 + rand.nextInt(width - 100), 50 + rand.nextInt(100),
                player_size, enemies_png[rand.nextInt(enemies_png.length)]);
    }

    static int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }


    public static void main(String[] args) {
        launch();
    }
}
