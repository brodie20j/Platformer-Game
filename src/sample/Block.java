package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;

/**
 * Created by jonathanbrodie on 11/26/14.
 */
public class Block extends Object {


    public Block() {
        /*Image image = new Image(getClass().getResourceAsStream("ship.jpeg"));
        ImageView imageView = new ImageView();
        imageView.setImage(image);*/
        this.setLayoutX(0);
        this.setLayoutY(0);
        Rectangle tempBox=new Rectangle(0,0,32,32);
        tempBox.setFill(Color.BLUE);
        this.getChildren().add(tempBox);
    }
    public void step() {

    }
}
