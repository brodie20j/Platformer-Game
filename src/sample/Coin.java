package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.*;

/**
 * Created by jonathanbrodie on 12/26/14.
 */
public class Coin extends Object {
    private ImageView imageView;
    int nDeathCount=0;

    public Coin(double startX, double startY, boolean bFromBlock) {
        Image image= new Image(getClass().getResourceAsStream("/res/img/coin.png"));
        imageView = new ImageView();
        imageView.setImage(image);
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.getChildren().add(imageView);
    }

}
