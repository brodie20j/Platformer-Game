package game;

import javafx.fxml.FXML;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by jonathanbrodie on 4/15/15.
 */
public class EnemyWeapon extends Enemy{
    @FXML
    private double velocityX;
    @FXML private double velocityY;

    public EnemyWeapon(double startX,double startY,boolean bDirection) {
        Rectangle myRectangle;
        for (int i=0; i<4; i++) {
            myRectangle=new Rectangle(0,0,32,8);
            myRectangle.setFill(Color.RED);
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
    @Override
    public void step() {
        //accelerate
        if (this.getVelocityX() > 0)
            this.setVelocityX(this.getVelocityX()+4.0);
        if (this.getVelocityX() < 0)
            this.setVelocityX(this.getVelocityX()-4.0);
        this.setLayoutX(this.getLayoutX() + this.velocityX);
        this.setLayoutY(this.getLayoutY() + this.velocityY);
        super.step();
    }


}
