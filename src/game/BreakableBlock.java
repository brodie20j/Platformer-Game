package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 4/14/15.
 */
public class BreakableBlock extends Block {
    public BreakableBlock() {
        Image image = new Image(getClass().getResourceAsStream("/res/img/genericblock.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.getChildren().add(imageView);

        this.setSolid(true);
    }
}
