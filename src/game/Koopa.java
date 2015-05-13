package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 11/29/14.
 */
public class Koopa extends Enemy {
    private ImageView imageView;
    private int walkAnimCounter=0;
    private boolean bAnim=false;
    private String myString="/res/img/mario_idler.png";

    public Koopa(double startX, double startY) {
        this.setLayoutX(0);
        this.setLayoutY(0);
        Image image = new Image(getClass().getResourceAsStream("/res/img/koopa_idle.png"));
        imageView = new ImageView();
        imageView.setImage(image);
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.getChildren().add(imageView);
        this.setHP(2);
        this.setPower(1);
    }
    public Koopa() {
        this.setLayoutX(0);
        this.setLayoutY(0);
        Image image = new Image(getClass().getResourceAsStream("/res/img/koopa_idle.png"));
        imageView = new ImageView();
        imageView.setImage(image);
        this.getChildren().add(imageView);
        this.setHP(2);
        this.setPower(1);
    }

    public void step() {
        super.step();
        this.updateSprite();
    }



    public void spriteDead() {
        //this.getChildren().clear();
        //Rectangle tempBox=new Rectangle(0,0,32,32);
        //tempBox.setFill(Color.GREEN);
        //this.getChildren().add(tempBox);
    }


}

