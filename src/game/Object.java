package game;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 11/26/14.
 */
public class Object extends Group {
    @FXML
    private double velocityX;
    @FXML private double velocityY;
    private int layer=0;
    private ImageView imageView;
    private boolean solid=false;
    public boolean inAir=true;
    public Object(double startX, double startY) {
        this.setLayoutX(startX);
        this.setLayoutY(startY);
    }
    public Object() {
        this.setLayoutX(0);
        this.setLayoutY(0);

    }
    public void setLayer(int nLayer) {
        this.layer=nLayer;
    }
    public int getLayer() {
        return this.layer;
    }
    public double getWidth() {
        return this.getBoundsInParent().getWidth();
    }
    public double getHeight() {
        return this.getBoundsInParent().getHeight();
    }

    public void step() {

    }
    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }
    public double getVelocityX() {
        return velocityX;
    }
    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
    public double getVelocityY() {
        return velocityY;
    }
    public boolean collision(Object object) {
        if (this.getBoundsInParent().intersects(object.getBoundsInParent())) {
            return true;
        }
        return false;
    }
    public boolean collisionLeft(Object object) {
        if ((this.getLayoutX() >= object.getLayoutX()+(object.getWidth()/2))
                && (this.getLayoutX() <= (object.getLayoutX()+object.getWidth()))
                ){
            return true;
        }
        return false;
    }
    public boolean collisionRight(Object object) {
        if ((this.getLayoutX() +this.getWidth() >= object.getLayoutX())
                && (this.getLayoutX() +this.getWidth() <= (object.getLayoutX()+(object.getWidth()/2))))

        {
            return true;
        }
        return false;
    }
    public boolean collisionDown(Object object) {

        if (
        (this.getBoundsInParent().getMaxY() < object.getBoundsInParent().getMaxY())
        && (this.getBoundsInParent().getMinY() < object.getBoundsInParent().getMinY())
                ){
            return true;
        }
        return false;
    }
    public boolean collisionUp(Object object) {
        if ((this.getLayoutY() <= object.getLayoutY()+object.getHeight())
                && (this.getLayoutY() <= object.getLayoutY()+object.getHeight())
                && (this.getLayoutY() > object.getLayoutY())
                ) {
            return true;
        }
        return false;
    }
    public boolean getSolid() {
        return this.solid;
    }
    public void setSolid(boolean bool) {
        this.solid=bool;
    }

    //loads sprite based on path '/res/img/yourfilepathhere'
    public void setSprite(String sFileName) {
        this.getChildren().clear();
        Image image = new Image(getClass().getResourceAsStream("/res/img/"+sFileName));
        this.imageView = new ImageView();
        this.imageView.setImage(image);
        this.getChildren().add(imageView);
    }

    //adds sprite to node's children
    //To avoid ambiguity: this local imageView is not the Object's set "Image View"
    public void addSprite(String sFileName) {
        Image image = new Image(getClass().getResourceAsStream(sFileName));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        this.getChildren().add(imageView);
    }

}
