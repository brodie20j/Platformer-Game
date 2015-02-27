package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 12/25/14.
 */
public class GrassBlock extends Block {

    public GrassBlock(double startX, double startY) {
        Image image = new Image(getClass().getResourceAsStream("/res/img/grass.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.getChildren().add(imageView);
    }
}
