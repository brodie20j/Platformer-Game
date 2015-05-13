package game;

import javafx.fxml.FXML;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by jonathanbrodie on 2/26/15.
 */
public class HeroWeapon extends Object {
    private ImageView imageView;
    @FXML
    private double velocityX;
    @FXML private double velocityY;
    public HeroWeapon(double startX,double startY,boolean bDirection) {
        Image image= new Image(getClass().getResourceAsStream("/res/img/coin.png"));
        Rectangle myRectangle;
        for (int i=0; i<4; i++) {
            myRectangle=new Rectangle(0,0,32,8);
            myRectangle.setFill(Color.CYAN);
            myRectangle.setBlendMode(BlendMode.SCREEN);
            myRectangle.setEffect(new GaussianBlur((double) i+1));
            this.getChildren().add(myRectangle);

        }
        /*
        imageView = new ImageView();
        imageView.setImage(image);

        this.getChildren().add(imageView);
        */
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.setVelocityX(-15.0);
        if (!bDirection) {
            this.setVelocityX(this.getVelocityX()*-1);
        }

    }
    public boolean collision(Object object) {
        if (this.getBoundsInParent().intersects(object.getBoundsInParent())) {
            return true;
        }
        return false;
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
    public void step() {
        //accelerate
        if (this.getVelocityX() > 0)
        this.setVelocityX(this.getVelocityX()+4.0);
        if (this.getVelocityX() < 0)
            this.setVelocityX(this.getVelocityX()-4.0);
        this.setLayoutX(this.getLayoutX() + this.velocityX);
        this.setLayoutY(this.getLayoutY() + this.velocityY);
    }
}
