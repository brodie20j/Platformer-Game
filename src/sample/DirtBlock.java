package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 12/25/14.
 */
public class DirtBlock extends Block {

    public DirtBlock(double startX, double startY) {
        Image image = new Image(getClass().getResourceAsStream("/res/img/dirt.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.getChildren().add(imageView);
    }
}