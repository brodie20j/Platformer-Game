package game;


import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 6/3/15.
 */


public class Dialog extends Group {
    Rectangle wordBox;
    private final double STARTX=20;
    private final double STARTY=20;
    private final double width=460;
    private final double height=200;

    public Dialog() {
        wordBox = new Rectangle();
        wordBox.setX(50);
        wordBox.setY(50);
        wordBox.setWidth(200);
        wordBox.setHeight(100);
        wordBox.setArcWidth(20);
        wordBox.setArcHeight(20);
    }


    public void Say(String sMessage, Object object) {

    }
}
