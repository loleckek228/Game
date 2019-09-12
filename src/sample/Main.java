package sample;


import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.level1.Game;
import sample.level2.Game2;

import java.util.HashMap;


public class Main extends Application {

    public static boolean nextLevel = false;

    public static Pane root = new Pane();

    public static HashMap<KeyCode, Boolean> keyBoard = new HashMap<>();

    public static AnimationTimer animationTimer;

    public static Stage primaryStage;

    public static Menu firstLevel;
    public static Menu secondLevel;

    public static Menu stepToMenu;
    public static SubMenu toMenu;

    public static Menu stepToNextLevel;
    public static SubMenu toNextLevel;


    public static MenuBox menuBox;

    public static SubMenu mainMenu;


    @Override
    public void start(Stage primaryStage) throws Exception {

        Image imageMenu = new Image(getClass().getResourceAsStream("Menu.jpg"));
        ImageView menuIv = new ImageView(imageMenu);
        menuIv.setFitWidth(1200);
        menuIv.setFitHeight(600);
        root.getChildren().add(menuIv);


        Menu startGame = new Menu("Start");
        Menu finishGame = new Menu("Exit");
        firstLevel = new Menu("Level 1");
        secondLevel = new Menu("Level 2");

        stepToMenu = new Menu("Menu");

        stepToNextLevel = new Menu("Next Level");


        mainMenu = new SubMenu(470, 262, startGame, finishGame);

        SubMenu startMenu = new SubMenu(635, 262, firstLevel, secondLevel);

        toMenu = new SubMenu(400, 300, stepToMenu);
        toNextLevel = new SubMenu(400, 300, stepToNextLevel);



        menuBox = new MenuBox(mainMenu);


        startGame.setOnMouseClicked(event -> menuBox.setSubMenu(startMenu));

        firstLevel.setOnMouseClicked(event -> {

            Game game = new Game();
            game.initContent();
            animationTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    game.update();
                }
            };
            animationTimer.start();
            primaryStage.getScene().setRoot(game.appRoot);
            Main.primaryStage = primaryStage;

        });


        secondLevel.setOnMouseClicked(event -> {

            Game2 game2 = new Game2();
            game2.initContent();
            animationTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    game2.update();
                }
            };
            animationTimer.start();
            primaryStage.getScene().setRoot(game2.appRoot);
            Main.primaryStage = primaryStage;
        });


        finishGame.setOnMouseClicked(event -> System.exit(0));
        root.getChildren().addAll(menuBox);
        root.getChildren().addAll(mainMenu);


        Scene scene = new Scene(root, 1200, 600);
        scene.setOnKeyPressed(event -> keyBoard.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keyBoard.put(event.getCode(), false));

        primaryStage.setTitle("Fable game");
        primaryStage.setScene(scene);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }

    public static class Menu extends StackPane {
        public Menu(String name) {
            Rectangle rectangle = new Rectangle(80, 20, Color.BLACK);
            rectangle.setOpacity(0.8);   // прозрачность


            final Text text = new Text(name);
            text.setFill(Color.DARKRED);
            text.setFont(Font.font("Impact", FontWeight.BOLD, 16));  //шрифт
            setAlignment(Pos.CENTER);
            getChildren().addAll(rectangle, text);

            text.setOnMouseEntered(event -> {
                text.setEffect(new GaussianBlur());
            });
            text.setOnMouseExited(event -> {
                text.setEffect(null);
            });
        }
    }

    public static class MenuBox extends Pane {
        static SubMenu subMenu;

        public MenuBox(SubMenu subMenu) {
            MenuBox.subMenu = subMenu;

            Rectangle rectangle = new Rectangle(1200, 600, Color.WHITE);
            rectangle.setOpacity(0);
            getChildren().addAll(rectangle, subMenu);
        }

        public void setSubMenu(SubMenu subMenu) {
            getChildren().remove(MenuBox.subMenu);
            MenuBox.subMenu = subMenu;
            getChildren().add(MenuBox.subMenu);
        }

    }

    public static class SubMenu extends VBox {

        public SubMenu(int x, int y, Menu... items) {
            setSpacing(7);   //отступы
            setTranslateX(x);
            setTranslateY(y);
            for (Menu item : items) {
                getChildren().addAll(item);
            }
        }

    }
}
