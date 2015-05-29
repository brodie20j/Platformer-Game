package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 11/29/14.
 */
public class Koopa extends Enemy {
    private int walkAnimCounter=0;
    private boolean bAnim=false;
    private String myString="/res/img/mario_idler.png";

    public Koopa(double startX, double startY) {
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.setSprite("koopa_idle.png");

        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.setHP(2);
        this.setPower(1);
    }
    public Koopa() {
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.setSprite("koopa_idle.png");
        this.setHP(2);
        this.setPower(1);
    }

    public void step() {
        super.step();
        super.updateSprite();
    }



    public void spriteDead() {
        //this.getChildren().clear();
        //Rectangle tempBox=new Rectangle(0,0,32,32);
        //tempBox.setFill(Color.GREEN);
        //this.getChildren().add(tempBox);
    }


}

