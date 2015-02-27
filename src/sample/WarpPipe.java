package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 1/3/15.
 */
public class WarpPipe extends Object {

    private ImageView imageView;
    public WarpPipe(double startX, double startY) {
        Image image= new Image(getClass().getResourceAsStream("/res/img/mushroom.png"));
        imageView = new ImageView();
        imageView.setImage(image);
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.getChildren().add(imageView);
    }
}
