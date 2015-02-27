package sample;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/**
 * Created by jonathanbrodie on 11/26/14.
 */
public class Object extends Group {


    public double getWidth() {
        return this.getBoundsInParent().getWidth();
    }
    public double getHeight() {
        return this.getBoundsInParent().getHeight();
    }
    public void step() {

    }

    public void destroy() {

    }

}
