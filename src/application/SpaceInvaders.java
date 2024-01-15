package application;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class SpaceInvaders extends Application {

    static final Random rand = new Random();
    private static final int width = 800;
    private static  final int height = 600;
    private static final int player_size = 70;
    static final Image[] player_png = {
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\player2.png"),
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\player3.png"),
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\player.png")
    };
    int index = 0;

    final Image win_png = new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\win2.png");
    final Image deathstar_png = new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\deathstar.png");
    final Image bridge_png = new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\bridge.png");
    final Image[] lose_png = {
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\lose.png"),
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\lose2.png"),
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\lose3.png"),
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\lose4.png"),
    };
    static final Image[] enemies_png = {
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\enemy.png"),
            new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\enemy2.png"),
    };

    Image level2_png = new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\level2.png");
    static final int explosion_width = 128;
    static final int explosion_row = 4;
    static final int explosion_col = 4;
    static final int explosion_height = 128;
    public static final int explosion_frames = 8;

    public static int getHeight() {
        return height;
    }
    final int max_enemies = 6,  max_shots = max_enemies * 2;
    boolean gameOver = false;
    public static GraphicsContext gc;
    static int ok = 0;
    Player player;

    List<Laser> shots;
    List<Universe> univ;
    List<Enemy> Enemies;

    private double init_pos = (double) width / 2;
    static int score, final_score;
    static Canvas canvas;
    int i = -1;
    boolean pause = false;
    boolean moveleft = false;
    boolean moveright = false;
    int universe = 20;
    boolean change_level = false;

    //start
    public void start(Stage stage) {
        canvas = new Canvas(width, height);
        //Scene scene = new Scene(width, height);
        gc = canvas.getGraphicsContext2D();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(70), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        //canvas.setCursor(Cursor.MOVE);
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(this::keyPressed);

        setup();
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setTitle("Star Wars: Rebels Resurrect");
        stage.show();
    }

    public void keyPressed(KeyEvent evt) {
        KeyCode key = evt.getCode();
        System.out.println("Key Pressed: " + key);

        if (ok == 0) {
            if (key == KeyCode.D)
                ok = 3;

            if (key == KeyCode.A)
                ok = 3;

            if (key == KeyCode.SPACE)
                ok = 3;

            if (key == KeyCode.ENTER)
                ok = 1;

        } else  if (ok == 3) {
            if (key == KeyCode.D)
                moveright = true;

            if (key == KeyCode.A)
                moveleft = true;

            if (key == KeyCode.SPACE) {
                if (shots.size() < max_shots) shots.add(player.shoot());

                if (gameOver) {
                    gameOver = false;
                    setup();
                }
            }

            if (key == KeyCode.ESCAPE) {
                pause = true;
                ok = 0;
            }
        }

        if (ok == 1) {
            if (key == KeyCode.DIGIT1) {
                index = 0;
                ok = 2;
            } else if (key == KeyCode.DIGIT2) {
                index = 1;
                ok = 2;
            }
            else if (key == KeyCode.DIGIT3) {
                index = 2;
                ok = 2;
            }
        }
        if (ok == 4) {
            if (key == KeyCode.ESCAPE) {
                ok = 0;
                gameOver = false;
                setup();
            }
        }
        if (ok == 5) {
            if (key == KeyCode.ENTER)
                ok = 6;
        }

        if (ok == 7) {
            if (key == KeyCode.ENTER) {
                setup();
                ok = 0;
            }

        }
    }

    public void keyReleased(KeyEvent evt) {
        KeyCode key = evt.getCode();
        System.out.println("Key Released: " + key);

        if (ok == 3) {
            if (key == KeyCode.A) {
                moveleft = false;
                init_pos -= 7;
            }

            if (key == KeyCode.D) {
                moveright = false;
                init_pos += 7;
            }
        }
    }

    private void setup() {
        univ = new ArrayList<>();
        canvas.setOnKeyPressed(this::keyPressed);
        player = new Player(width / 2, height - player_size, player_size, player_png[index]);
        shots = new ArrayList<>();
        Enemies = new ArrayList<>();
        init_pos = (double) width / 2;

        score = 0;
        IntStream.range(0, max_enemies).mapToObj(i -> this.newEnemy()).forEach(Enemies::add);
    }

    private void run(GraphicsContext gc) {
        gc.setFill(Color.grayRgb(13));
        gc.fillRect(0, 0, width, height);
        gc.setTextAlign(TextAlignment.CENTER);

        switch (ok) {
            case 0 -> {
                Image logo = new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\test.png");
                gc.drawImage(logo, 150, 100);
                gc.setFont(Font.font("Lucida Console", 25));
                gc.setFill(Color.LIGHTGOLDENRODYELLOW);
                gc.fillText("- Start -\n(A or D)", width / 2 - 250, 300);
                gc.fillText("- Choose Player -\n(Enter)", width / 2 + 250, 300);
                if (pause) {
                    gc.setFont(Font.font(30));
                    gc.setFill(Color.LIGHTGOLDENRODYELLOW);
                    gc.fillText("PAUSE", width / 2, 30);
                }
                canvas.setOnKeyPressed(this::keyPressed);
            }
            case 1 -> {
                gc.setFont(Font.font("Lucida Console", 30));
                gc.setFill(Color.LIGHTGOLDENRODYELLOW);
                gc.fillText("Choose Player", width / 2, 560);

                gc.setFont(Font.font("SansSerif", 20));
                gc.setFill(Color.LIGHTYELLOW);
                gc.fillText("Luke Skywalker", width / 2 - 300, height / 2 + 150);
                gc.fillText("R2D2 and C3P0", width / 2, height / 2 + 150);
                gc.fillText("Han Solo\nand\nChewbacca", width / 2 + 300, height / 2 + 150);

                gc.fillText("1", width / 2 - 300, 30);
                gc.fillText("2", width / 2, 30);
                gc.fillText("3", width / 2 + 300, 30);

                Image icon1_png = new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\icon1.png");
                Image icon2_png = new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\icon2.png");
                Image icon3_png = new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\icon3.png");
                Image icon4_png = new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\icon4.png");

                Image icon5_png = new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\icon5.png");
                Image icon6_png = new Image("file:C:\\Users\\Paul\\Desktop\\SpaceInvaders\\sprites\\icon6.png");

                gc.drawImage(icon1_png, width / 2 - 400, height / 2 - 70);
                gc.drawImage(icon2_png, width / 2 - 100, height / 2 - 70);
                gc.drawImage(icon3_png, width / 2 + 200, height / 2 - 70);

                gc.drawImage(icon5_png, width / 2 - 350, 40);
                gc.drawImage(icon4_png, width / 2 - 50, 40);
                gc.drawImage(icon6_png, width / 2 + 250, 40);

                canvas.setOnKeyPressed(this::keyPressed);
                setup();
            }
            case 2 -> {
                ok = 3;
                setup();
            }
            case 3 -> {
                gc.setFill(Color.grayRgb(universe));
                gc.fillRect(0, 0, width, height);
                if (moveright)
                    init_pos += 15;
                if (moveleft)
                    init_pos -= 15;
                pause = false;

                gc.setFont(Font.font("SansSerif", 20));
                gc.setFill(Color.WHITE);
                gc.fillText("Score: " + final_score, 60, 20);


                if (score < 1) {
                    if (!change_level) {
                        gc.setFill(Color.RED);
                        gc.fillRect(10, 30, score * 3, 15);
                    } else {
                        gc.setFill(Color.BLUE);
                        gc.fillRect(10, 30, score * 3, 15);
                    }
                } else {
                    if (!change_level) {
                        ok = 5;
                    } else {

                        ok = 7;
                    }
                }

                if (gameOver) {
                    universe = 20;
                    i++;
                    if (i > 3)
                        i = 0;
                    System.out.println(i);
                    ok = 4;
                    change_level = false;
                } else final_score = score;

                if (change_level) {
                    gc.drawImage(level2_png, 300, -100);
                } else gc.drawImage(deathstar_png, 500, 30);

                univ.forEach(Universe::draw);
                player.update();
                player.draw();

                canvas.setOnKeyPressed(this::keyPressed);
                canvas.setOnKeyReleased(this::keyReleased);
                //canvas.setOnKeyTyped(e -> keyPressed(e));

                player.posX = (int) init_pos;
                Enemies.stream().peek(Player::update).peek(Player::draw).forEach(e -> {
                    if (player.collision(e) && !player.exploding) {
                        player.explode();
                    }
                });
                for (int i = shots.size() - 1; i >= 0; i--) {
                    Laser shot = shots.get(i);
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
            case 4 -> {
                gc.drawImage(lose_png[i], 0, 0);
                gc.setFont(Font.font(35));
                gc.setFill(Color.LIGHTGOLDENRODYELLOW);
                gc.fillText("GAME OVER", width / 2, 100);
                gc.setFont(Font.font("Lucida Console", 25));
                gc.setFill(Color.LIGHTGOLDENRODYELLOW);
                gc.fillText("Your Score is: " + final_score + "\n Try Again? (ESCAPE)", width / 2, 500);
                canvas.setOnKeyPressed(this::keyPressed);
            }
            case 5 -> {
                change_level = true;
                init_pos = width / 2;
                gc.drawImage(bridge_png, 0, 0);
                gc.setFont(Font.font("Lucida Console", 30));
                gc.setFill(Color.LIGHTGOLDENRODYELLOW);
                gc.fillText("Attack the Death Star", width / 2, 60);
                gc.setFont(Font.font(30));
                gc.fillText("\n\nPress Enter to Begin", width / 2, 460);
                canvas.setOnKeyPressed(this::keyPressed);
            }
            case 6 -> {

                ok = 3;
                change_level = true;
                universe = 0;
                gc.drawImage(bridge_png, 0, 0);
                setup();
            }
            case 7 -> {
                universe = 20;
                gc.drawImage(win_png, 10, 0);
                gc.setFont(Font.font("Lucida Console", 30));
                gc.setFill(Color.LIGHTGOLDENRODYELLOW);
                gc.fillText("You win!\nThe Galaxy is, once again, at peace...", width / 2, 60);
                gc.setFont(Font.font("Lucida Console", 25));
                gc.fillText("Play Again? Press ENTER", width / 2, 550);

                change_level = false;
                canvas.setOnKeyPressed(this::keyPressed);
            }
            default -> {
            }
        }
    }

    Enemy newEnemy() {
        return new Enemy(rand.nextInt(width - 90) + rand.nextInt(100), rand.nextInt(50),
                player_size, enemies_png[rand.nextInt(enemies_png.length)]);
    }

    static int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }


    public static void main(String[] args) {
        launch();
    }
}