package sample.level1;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import sample.Player.Character;
import sample.Main;
import sample.level2.Game2;

import java.util.ArrayList;


import static javafx.scene.input.KeyCode.*;


public class Game {

    private boolean throwing = false;

    public ArrayList<Node> attacksCharacter = new ArrayList<>();
    public ArrayList<Node> attacksBoss = new ArrayList<>();
    Image imageBall = new Image("file:///Volumes/George/IdeaProjects/fableNew/src/sample/level1/ballCharacter.png");
    Image fireImage = new Image(getClass().getResourceAsStream("ball.png"));
    Image backGroundImg = new Image(getClass().getResourceAsStream("XcWa7.png"));
    int score;
    Text text;

    Character player = new Character();
    public TankBoss boss = new TankBoss();


    public Pane appRoot = new Pane();
    public Pane gameRoot = new Pane();

    int modifier = 60;
    int throwCounter = modifier - 1;


    public void initContent() {
        ImageView backGroungIv = new ImageView(backGroundImg);

        backGroungIv.setFitWidth(1200);
        backGroungIv.setFitHeight(600);

        player.setTranslateX(0);
        player.setTranslateY(500);

        boss.setTranslateX(1100);
        boss.setTranslateY(500);

        gameRoot.getChildren().add(player);
        gameRoot.getChildren().add(boss);
        appRoot.getChildren().addAll(backGroungIv, gameRoot);

    }

    public void update() {
        if (isPressed(KeyCode.UP)) {
            player.animation.play();
            boss.animation.play();
            player.animation.setOffsetY(96);
            boss.animation.setOffsetY(0);
            player.moveY(-3);
            boss.moveBoss(-2);

        } else if (isPressed((DOWN))) {
            player.animation.play();
            boss.animation.play();
            player.animation.setOffsetY(0);
            boss.animation.setOffsetY(0);
            player.moveY(3);
            boss.moveBoss(2);

        } else if (isPressed(LEFT)) {
            player.animation.play();
            player.animation.setOffsetY(32);
            player.moveX(-3);

        } else if (isPressed(RIGHT)) {
            player.animation.play();
            player.animation.setOffsetY(64);
            player.moveX(3);

        } else if (isPressed(ESCAPE)) {
            System.exit(0);

        } else if (isPressed(SPACE)) {
            if (!throwing) {
                characterWeapon();
                throwing = true;
            }
        } else {
            player.animation.stop();
            throwing = false;
        }
        throwCounter++;
        if (throwCounter % modifier == 0) {
            if (modifier > 40) modifier--;
            if (modifier <= 40) modifier = 60;
            bossWeapon();
        }
        checkHit();
        throwWeapon(attacksCharacter, 8);
        throwWeapon(attacksBoss, -10);

    }


    public boolean isPressed(KeyCode key) {
        return Main.keyBoard.getOrDefault(key, false);
    }

    public void checkHit() {
        for (int i = 0; i < attacksCharacter.size(); i++) {
            if (attacksCharacter.get(i).getBoundsInParent().intersects(boss.getBoundsInParent())) {
                if (gameRoot.getChildren().remove(attacksCharacter.get(i))) {
                    attacksCharacter.remove(attacksCharacter.get(i));
                    score++;
                }
                if (score >= 20) {
                    gameRoot.getChildren().remove(boss);
                    attacksCharacter.clear();
                    Main.animationTimer.stop();
                    gameRoot.getChildren().add(new Main.SubMenu(550, 300, Main.stepToNextLevel));
                    Main.stepToNextLevel.setOnMouseClicked(event -> {
                        Game2 game2 = new Game2();
                        game2.initContent();
                        Main.animationTimer = new AnimationTimer() {
                            @Override
                            public void handle(long now) {
                                game2.update();
                            }
                        };
                        Main.animationTimer.start();
                        Main.primaryStage.getScene().setRoot(game2.appRoot);
                        Main.nextLevel = true;
                    });
                    text = new Text();
                    text.setText("Победа");
                    text.setX(460);
                    text.setY(280);
                    text.setFill(Color.DARKRED);
                    text.setFont(Font.font("Verdana", FontPosture.REGULAR, 50));
                    gameRoot.getChildren().add(text);
                }
            }
        }

        for (int j = 0; j < attacksBoss.size(); j++) {
            if (attacksBoss.get(j).getBoundsInParent().intersects(player.getBoundsInParent())) {
                gameRoot.getChildren().remove(player);
                gameRoot.getChildren().remove(attacksBoss.get(j));
                attacksBoss.clear();
                Main.animationTimer.stop();

                gameRoot.getChildren().add(new Main.SubMenu(550, 300, Main.stepToMenu));

                Main.stepToMenu.setOnMouseClicked(event -> Main.primaryStage.getScene().setRoot(Main.root));
                text = new Text();
                text.setText("Потрачено");
                text.setX(460);
                text.setY(280);
                text.setFont(Font.font("Verdana", FontPosture.REGULAR, 50));
                text.setFill(Color.DARKRED);
                gameRoot.getChildren().add(text);

            }
        }
    }

    public void characterWeapon() {
        ImageView ball = new ImageView(imageBall);
        Node characterWeapon = ball;
        characterWeapon.relocate(player.getTranslateX() + 20, player.getTranslateY() + 28);
        attacksCharacter.add(characterWeapon);
        gameRoot.getChildren().addAll(characterWeapon);
    }

    public void bossWeapon() {
        ImageView fire = new ImageView(fireImage);
        Node bossWeapon = fire;
        bossWeapon.relocate(boss.getTranslateX() - 15, boss.getTranslateY() + 20);
        attacksBoss.add(bossWeapon);
        gameRoot.getChildren().addAll(bossWeapon);
    }

    public void throwWeapon(ArrayList<Node> attacks, int delta) {
        for (int i = 0; i < attacks.size(); i++) {
            attacks.get(i).relocate(attacks.get(i).getLayoutX() + delta,
                    attacks.get(i).getLayoutY());
        }
    }

}
