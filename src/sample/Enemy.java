package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 11/26/14.
 */
public class Enemy extends Object {
    private int power;
    private int hp;
    @FXML private double velocityX;
    @FXML
    private double velocityY;
    private boolean bDead;
    private final int scaleConstant=64;
    private ImageView imageView;
    private int walkAnimCounter=0;
    private boolean bAnim=false;
    public int bXOrientation=0;
    private String myString="/res/img/mario_idler.png";
    public Enemy() {
        bDead=false;
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
    public void setHP(int newHP) {
        this.hp=newHP;
    }
    public void setPower(int newPower) {
        this.power=newPower;
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
        if ((this.getLayoutY()+this.getHeight() >= object.getLayoutY())
                && (this.getLayoutY()+this.getHeight() <= object.getLayoutY()+(object.getHeight()/2))
                && (this.getLayoutY() < object.getLayoutY())

                ){
            return true;
        }
        return false;
    }



    public boolean collisionUp(Object object) {
        if ((this.getLayoutY() <= object.getLayoutY()+object.getHeight())
                && (this.getLayoutY() >= object.getLayoutY()+(object.getHeight()/2))
                && (this.getLayoutY() > object.getLayoutY())
                ) {
            return true;
        }
        return false;
    }
    public int getHP() {
        return this.hp;
    }
    public int getAttack() {
        return this.power;
    }
    public boolean getDead() {
        return this.bDead;
    }
    public void step() {

        this.setLayoutX(this.getLayoutX() + this.velocityX);
        this.setLayoutY(this.getLayoutY() + this.velocityY);
        if (this.hp <= 0) {
            System.out.println("dead");
            this.bDead=true;
        }
    }
    public void updateSprite() {
       // Class<?> enclosingClass = this.getClass().getEnclosingClass();
        String className=this.getClass().getSimpleName().toLowerCase();
        System.out.println(className);
        this.imageView = new ImageView();
        Image image;
        if (this.getVelocityX() > 0) {
            if (this.walkAnimCounter >= 4) {
                if (bAnim) myString="/res/img/"+className+"_idler.png";
                else myString="/res/img/"+className+"_walkingr.png";
                bAnim=!bAnim;
                walkAnimCounter=0;
            }
            walkAnimCounter+=1;
            image = new Image(getClass().getResourceAsStream(myString));
            this.bXOrientation=0;
        } else if (this.getVelocityX() < 0) {
            if (this.walkAnimCounter >= 4) {
                if (bAnim) myString="/res/img/"+className+"_idle.png";
                else myString="/res/img/"+className+"_walking.png";
                bAnim=!bAnim;
                walkAnimCounter=0;

            }
            walkAnimCounter+=1;
            this.bXOrientation=1;
            image = new Image(getClass().getResourceAsStream(myString));
        } else {
            if (this.bXOrientation==0) image = new Image(getClass().getResourceAsStream("/res/img/"+className+"_idle.png"));
            else image = new Image(getClass().getResourceAsStream("/res/img/"+className+"_idle.png"));
        }
        //image = new Image(getClass().getResourceAsStream("/res/img/mario_idle.png"));

        this.imageView.setImage(image);
        this.getChildren().clear();
        this.getChildren().add(imageView);
        this.walkAnimCounter+=1;
    }
}
