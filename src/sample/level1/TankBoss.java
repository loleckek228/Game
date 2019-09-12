package sample.level1;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sample.Player.SpriteAnimation;


public class TankBoss extends Pane {

    Image imageTank = new Image(getClass().getResourceAsStream("7.png"));
    ImageView imageViewTank = new ImageView(imageTank);
    public static final int MAX_TOP = 440;
    public static final int MAX_BUTTOM = 510;

    SpriteAnimation animation;

    public TankBoss() {
        imageViewTank.setFitWidth(90);
        imageViewTank.setFitHeight(90);
        imageViewTank.setViewport(new Rectangle2D(0, 0, 64, 61));
        animation = new SpriteAnimation(imageViewTank, 1, 1, 0, 0, 64, 61, Duration.millis(1000));
        getChildren().addAll(this.imageViewTank);

    }

    public void moveBoss(int value) {
        boolean down = value > 0;

        if (getTranslateY() <= MAX_TOP) setTranslateY(MAX_TOP);
        if (getTranslateY() >= MAX_BUTTOM) setTranslateY(MAX_BUTTOM);
        this.setTranslateY(this.getTranslateY() + (down ? 1 : -1));
    }


}



