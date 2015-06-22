package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 5/13/15.
 *
 * Door
 * =====
 * Acts as a portal to another location a map when a player presses up
 */

public class Door extends Object {
    private double Ax;
    private double Ay;
    private ImageView imageView;


    public Door() {
        Image image = new Image(getClass().getResourceAsStream("/res/img/door_temp.png"));
        this.imageView = new ImageView();
        this.imageView.setImage(image);
        this.getChildren().add(this.imageView);
    }
    public void setTransportLocation(double x, double y) {
        this.Ax=x;
        this.Ay=y;

    }
    public double getTransportX() {
        return this.Ax;
    }
    public double getTransportY() {
        return this.Ay;
    }

    public void TransportHero(Hero myHero) {
        myHero.setLayoutX(this.Ax);
        myHero.setLayoutY(this.Ay);
    }
    public void openDoor() {
        //door open animation here
    }
    public void closeDoor() {
        //close door animation here
    }


}
