package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Created by jonathanbrodie on 11/28/14.
 */
public class Goomba extends Enemy {
    private ImageView imageView;
    public Goomba(double startX, double startY) {
        Image image = new Image(getClass().getResourceAsStream("/res/img/goomba-test.png"));
        imageView = new ImageView();
        imageView.setImage(image);
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.getChildren().add(imageView);
        this.setHP(1);
        this.setPower(1);
    }
    public void step() {
        super.step();

    }

    public void spriteDead() {
        this.getChildren().clear();
        Rectangle tempBox=new Rectangle(0,0,32,32);
        tempBox.setFill(Color.WHITE);
        this.getChildren().add(tempBox);
    }


}
