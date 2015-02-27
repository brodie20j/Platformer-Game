package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 12/26/14.
 */
public class Mushroom extends Object {
    private ImageView imageView;
    public Mushroom(double startX, double startY) {
        Image image= new Image(getClass().getResourceAsStream("/res/img/mushroom.png"));
        imageView = new ImageView();
        imageView.setImage(image);
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.getChildren().add(imageView);
    }
}
