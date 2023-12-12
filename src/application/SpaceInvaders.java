package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SpaceInvaders extends Application {

    //variables
    static final Random rand = new Random();
    private static final int width = 800;
    private static  final int height = 600;
    private static final int player_size = 70;
    static final Image player_png = new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\player3.png");

    static final Image[] enemies_png = {
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\retro.png"),
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\retro2.png"),
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\retro3.png"),
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\retro4.png"),
    };
    static final int explosion_width = 120;
    static final int explosion_row = 4;
    static final int explosion_col = 4;
    static final int explosion_height = 120;
    public static final int explosion_frames = 9;

    public static int getHeight() {
        return height;
    }
    public static int getWidth() { return width; }
    final int max_enemies = 10,  max_shots = max_enemies * 2;
    boolean gameOver = false;
    public static GraphicsContext gc;
    static int ok = 0;
    Player player;
    List<Shot> shots;
    List<Universe> univ;
    List<Enemy> Enemies;

    private double mouseX = (double) width / 2;
    static int score, final_score;
    static Canvas canvas;

    //start
    public void start(Stage stage) throws Exception {
        canvas = new Canvas(width, height);
        //Scene scene = new Scene(width, height);
        gc = canvas.getGraphicsContext2D();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        //canvas.setCursor(Cursor.MOVE);
        canvas.setFocusTraversable(true);

        setup();
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setTitle("Space Invaders");
        stage.show();

    }

    public void keyPressed(KeyEvent evt) {
        KeyCode key = evt.getCode();
        System.out.println("Key Pressed: " + key);

        if (key == KeyCode.D) {
            mouseX = mouseX + 10;
            ok = 1;
        }
        if (key == KeyCode.A) {
            mouseX = mouseX - 10;
            ok = 1;
        }
        if (key == KeyCode.J) {
            if(shots.size() < max_shots) shots.add(player.shoot());
            if(gameOver) {
                gameOver = false;
                setup();
            }
            ok = 1;
        }
    }
    private void setup() {
        univ = new ArrayList<>();
        player = new Player(width / 2, height - player_size, player_size, player_png);
        shots = new ArrayList<>();
        Enemies = new ArrayList<>();

        score = 0;
        IntStream.range(0, max_enemies).mapToObj(i -> this.newEnemy()).forEach(Enemies::add);
    }

    private void run(GraphicsContext gc) {
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, width, height);
        gc.setTextAlign(TextAlignment.CENTER);


        if (ok == 0) {
            gc.setFont(Font.font("SansSerif", 60));
            gc.setFill(Color.YELLOW);
            gc.fillText("STAR WARS", (double) width / 2, (double) height / 2);
            canvas.setOnKeyPressed(this::keyPressed);
        } else if (ok == 1) {
            gc.setFont(Font.font("SansSerif", 20));
            gc.setFill(Color.WHITE);
            gc.fillText("Score: " + final_score, 60, 20);

            if (gameOver) {
                gc.setFont(Font.font(35));
                gc.setFill(Color.YELLOW);
                gc.fillText("Game Over \n Your Score is: " + final_score + " \n Click to play again", (double) width / 2, height / 2.5);
                //	return;
            } else final_score = score;
            univ.forEach(Universe::draw);

            player.update();
            player.draw();

            canvas.setOnKeyPressed(this::keyPressed);
            //canvas.setOnKeyTyped(e -> keyPressed(e));

            player.posX = (int) mouseX;

            Enemies.stream().peek(Player::update).peek(Player::draw).forEach(e -> {
                if (player.collision(e) && !player.exploding) {
                    player.explode();
                }
            });

            for (int i = shots.size() - 1; i >= 0; i--) {
                Shot shot = shots.get(i);
                if (shot.posY < 0 || shot.toRemove) {
                    shots.remove(i);
                    continue;
                }
                shot.update();
                shot.draw();

                for (Enemy enemy : Enemies) {
                    if (shot.collide(enemy) && !enemy.exploding) {
                        score++;
                        enemy.explode();
                        shot.toRemove = true;
                    }
                }
            }

            for (int i = Enemies.size() - 1; i >= 0; i--) {
                if (Enemies.get(i).destroyed) {
                    Enemies.set(i, newEnemy());
                }
            }

            gameOver = player.destroyed;
            if (rand.nextInt(10) > 2)
                univ.add(new Universe());

            for (int i = 0; i < univ.size(); i++) {
                if (univ.get(i).posY > height)
                    univ.remove(i);
            }
        }
    }

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
