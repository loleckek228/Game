package sample.Player;


import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class Character extends Pane {

    Image imageCharacter = new Image(getClass().getResourceAsStream("1.png"));
    public ImageView imageViewCharacter = new ImageView(imageCharacter);
    public static final int MAX_LEFT = 60;
    public static final int MAX_RIGHT = 1120;
    public static final int MAX_TOP = 460;
    public static final int MAX_BUTTOM = 540;
    public SpriteAnimation animation;

    public Character() {
        imageViewCharacter.setFitWidth(60);
        imageViewCharacter.setFitHeight(60);
        imageViewCharacter.setViewport(new Rectangle2D(0, 0, 32, 32));
        animation = new SpriteAnimation(imageViewCharacter, 3, 3,
                0, 0, 32, 32, Duration.millis(500));
        getChildren().addAll(this.imageViewCharacter);

    }

    public void moveX(int value) {
        boolean movingRight = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {

            if (getTranslateX() <= MAX_LEFT) setTranslateX(MAX_LEFT);
            if (getTranslateX() >= MAX_RIGHT) setTranslateX(MAX_RIGHT);
            this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));

        }

    }

    public void moveY(int value) {
        boolean movingDown = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            if (getTranslateY() <= MAX_TOP) setTranslateY(MAX_TOP);
            if (getTranslateY() >= MAX_BUTTOM) setTranslateY(MAX_BUTTOM);
            this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
        }
    }
}
