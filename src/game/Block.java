package game;

import game.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by jonathanbrodie on 11/26/14.
 */
public class Block extends game.Object {


    public Block() {
        Image image = new Image(getClass().getResourceAsStream("/res/img/genericblock.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.getChildren().add(imageView);

        this.setSolid(true);
    }
    public Block(double startX, double startY) {
        Image image = new Image(getClass().getResourceAsStream("/res/img/genericblock.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.getChildren().add(imageView);
        this.setSolid(true);

    }
    public void step() {
        super.step();
    }
}
