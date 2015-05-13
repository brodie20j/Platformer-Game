package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 3/1/15.
 */
public class HealthPotion extends Object {
    private ImageView imageView;
    public HealthPotion(double startX, double startY) {
        Image image= new Image(getClass().getResourceAsStream("/res/img/hp_potion.png"));
        imageView = new ImageView();
        imageView.setImage(image);
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.getChildren().add(imageView);
    }


}
