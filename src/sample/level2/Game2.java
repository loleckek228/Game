package sample.level2;

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

import java.util.ArrayList;

public class Game2 {

    Image imageBackGround = new Image(getClass().getResourceAsStream("tumblr_static_.gif"));
    Image fireImage = new Image(getClass().getResourceAsStream("fire.png"));
    Image bonusImage = new Image(getClass().getResourceAsStream("cannabis_PNG2-2.png"));
    ArrayList<Node> fires = new ArrayList<>();
    ArrayList<Node> bonuses = new ArrayList<>();
    Text text;

    Pane gameRoot = new Pane();
    public Pane appRoot = new Pane();
    int modifier = 100;
    int fireCounter = modifier - 1;


    Character player = new Character();

    public Game2() {
        text = new Text("Score: 0/228");
        text.setX(10);
        text.setY(20);
        text.getText();
        text.setFill(Color.RED);
        text.setFont(Font.font("Verdana", FontPosture.REGULAR, 25));
        gameRoot.getChildren().add(text);
    }


    public void initContent() {
        ImageView backGroundIv = new ImageView(imageBackGround);

        backGroundIv.setFitWidth(1200);
        backGroundIv.setFitHeight(750);

        player.imageViewCharacter.setFitHeight(40);
        player.imageViewCharacter.setFitHeight(40);
        player.setTranslateX(400);
        player.setTranslateY(550);

        gameRoot.getChildren().add(player);
        appRoot.getChildren().addAll(backGroundIv, gameRoot);
    }

    public void update() {
        if (isPressed(KeyCode.LEFT)) {
            player.animation.play();
            player.animation.setOffsetY(32);
            player.moveX(-8);
        } else if (isPressed(KeyCode.RIGHT)) {
            player.animation.play();
            player.animation.setOffsetY(64);
            player.moveX(8);
        } else if (isPressed(KeyCode.ESCAPE)) {
            System.exit(0);
        } else {
            player.animation.stop();
        }
        fireCounter++;
        if (fireCounter % modifier == 0) {
            if (modifier > 10) {
                modifier--;
                if (modifier <= 10) modifier = 30;
            }
            fire(60, 1120);
            bonus(60, 1120);
        }
        /*fireCounter++;
        if (fireCounter % modifier == 0) {
            if (modifier > 60) {
                modifier--;
                if (modifier <= 60) modifier = 100;
            }
            fire(30, 600);
            bonus(30, 600);
        }
        fireCounter++;
        if (fireCounter % modifier == 0) {
            if (modifier > 60) {
                modifier--;
                if (modifier <= 60) modifier = 100;
            }
            fire(200, 400);
        }
        fireCounter++;
        if (fireCounter % modifier == 0) {
            if (modifier > 60) {
                modifier--;
                if (modifier <= 60) modifier = 100;
            }
            fire(400, 600);
        }
        fireCounter++;
        if (fireCounter % modifier == 0) {
            if (modifier > 60) {
                modifier--;
                if (modifier <= 60) modifier = 100;
            }
            fire(600, 900);

        }
        fireCounter++;
        if (fireCounter % modifier == 0) {
            if (modifier > 60) {
                modifier--;
                if (modifier <= 60) modifier = 100;
            }
            fire(900, 1130);
            bonus(600, 1130);
        }*/

        checkHit();
        moveFire(10);
        moveBonus(7);
    }

    public boolean isPressed(KeyCode key) {
        return Main.keyBoard.getOrDefault(key, false);
    }


    public void fire(int left, int right) {
        Node newBox = new ImageView(fireImage);
        newBox.relocate(left + (int) (Math.random() * right + 1), -90);
        fires.add(newBox);
        gameRoot.getChildren().add(newBox);
    }

    public void moveFire(int delta) {
        for (int i = 0; i < fires.size(); i++) {
            if (fires.get(i).getLayoutX() >= fires.get(i).getBoundsInLocal().getWidth()) {
                fires.get(i).relocate(fires.get(i).getLayoutX(), fires.get(i).getLayoutY() + delta);
            } else {
                gameRoot.getChildren().remove(fires.get(i));
                fires.remove(i);
            }
        }
    }

    int score = 0;

    public void bonus(int left, int right) {
        Node newBonus = new ImageView(bonusImage);
        newBonus.relocate(left + (int) (Math.random() * right + 1), -90);
        bonuses.add(newBonus);
        gameRoot.getChildren().add(newBonus);
    }

    public void moveBonus(int delta) {
        for (int i = 0; i < bonuses.size(); i++) {
            if (bonuses.get(i).getLayoutX() >= bonuses.get(i).getBoundsInLocal().getWidth()) {
                bonuses.get(i).relocate(bonuses.get(i).getLayoutX(),
                        bonuses.get(i).getLayoutY() + delta);
                if (bonuses.get(i).getLayoutY() >= 530) {
                    bonuses.get(i).setLayoutY(530);
                }
            } else {
                gameRoot.getChildren().remove(bonuses.get(i));
                bonuses.remove(i);
            }
        }
    }


    public void checkHit() {
        for (int i = 0; i < fires.size(); i++) {
            if (fires.get(i).getBoundsInParent().intersects(player.getBoundsInParent())) {
                gameRoot.getChildren().remove(player);
                gameRoot.getChildren().add(new Main.SubMenu(550, 300, Main.stepToMenu));
                fires.clear();

                Main.animationTimer.stop();
                Main.stepToMenu.setOnMouseClicked(event -> Main.primaryStage.getScene().setRoot(Main.root));
            }
        }

        for (int i = 0; i < bonuses.size(); i++) {
            if (player.getBoundsInParent().intersects(bonuses.get(i).getBoundsInParent())) {
                if (gameRoot.getChildren().remove(bonuses.get(i))) {
                    score++;
                    if (score >= 228) {
                        text.setText("Победа");
                    } else {
                        text.setText("Score: " + score + "/228");
                    }
                }
            }
        }

    }

}

