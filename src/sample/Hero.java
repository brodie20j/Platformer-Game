package sample;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/**
 * Created by jonathanbrodie on 11/26/14.
 */


public class Hero extends Object {

    @FXML
    private double velocityX;
    @FXML private double velocityY;

    private int maxHP=5;
    private int currentHP=5;
    private int power=1;
    private boolean bInvincible=false;
    private int invincibleCount=0;
    private ImageView imageView;
    public double preLayoutX;
    public double preLayoutY;
    private int walkAnimCounter=0;
    private boolean bAnim=false;
    public int bXOrientation=0;
    private String myString="/res/img/mario_idler.png";
    public boolean inAir=true;

    public Hero(double startX, double startY) {
        Image image = new Image(getClass().getResourceAsStream("/res/img/mariotest2.png"));
        this.imageView = new ImageView();
        imageView.setImage(image);
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.getChildren().add(imageView);

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
    public int getMaxHP() {
        return this.maxHP;
    }
    public int getCurrentHP() {
        return this.currentHP;
    }
    public int getPower() {
        return this.power;
    }
    public void setMaxHP(int newMaxHP) {
        this.maxHP=newMaxHP;
    }
    public void setCurrentHP(int newcurrentHP) {
        if (newcurrentHP <= this.maxHP)
        this.currentHP=newcurrentHP;
        else this.currentHP=this.maxHP;
    }
    public void setPower(int newPower) {
        this.power=newPower;
    }
    public void setInvincibility(boolean bool) {
        this.bInvincible=bool;
    }
    public boolean getInvincibility() {
        return this.bInvincible;
    }
    public void step() {
        this.updateSprite();
        this.preLayoutX=this.getLayoutX();
        this.preLayoutY=this.getLayoutY();
        this.setLayoutX(this.getLayoutX() + this.velocityX);
        this.setLayoutY(this.getLayoutY() + this.velocityY);


        if (Math.abs(this.getVelocityX()) < 2.0) this.setVelocityX(0);

        if (this.bInvincible) {
            if (invincibleCount > 24) invincibleCount = 0;
            else if (invincibleCount == 24) {
                this.setInvincibility(false);
            }
            invincibleCount = invincibleCount + 1;
        }
        if (this.inAir) {
            this.setVelocityY(this.getVelocityY()+4);

        }
        else if (this.getVelocityY() >= 0) this.setVelocityY(0);
    }
    public void updateSprite() {
        this.imageView = new ImageView();
        Image image;
        if (this.inAir) {
            //falling animation
            if (this.getVelocityX() > 0) {
                image = new Image(getClass().getResourceAsStream("/res/img/mario_jumpingr.png"));
            }
            else {
                image = new Image(getClass().getResourceAsStream("/res/img/mario_jumping.png"));
            }

        }
        else {
            if (this.getVelocityX() > 0) {
                if (this.walkAnimCounter >= 4) {
                    if (bAnim) myString = "/res/img/mario_idler.png";
                    else myString = "/res/img/mario_walkingr.png";
                    bAnim = !bAnim;
                    walkAnimCounter = 0;
                }
            walkAnimCounter += 1;
            image = new Image(getClass().getResourceAsStream(myString));
            this.bXOrientation = 0;
            }
            else if (this.getVelocityX() < 0) {
                if (this.walkAnimCounter >= 4) {
                    if (bAnim) myString="/res/img/mario_idle.png";
                    else myString="/res/img/mario_walking.png";
                    bAnim=!bAnim;
                    walkAnimCounter=0;
                }
                walkAnimCounter+=1;
                this.bXOrientation=1;
                image = new Image(getClass().getResourceAsStream(myString));
            } else {
                if (this.bXOrientation==0) image = new Image(getClass().getResourceAsStream("/res/img/mario_idler.png"));
                else image = new Image(getClass().getResourceAsStream("/res/img/mario_idle.png"));
            }
            //image = new Image(getClass().getResourceAsStream("/res/img/mario_idle.png"));
        }

        this.imageView.setImage(image);
        this.getChildren().clear();
        this.getChildren().add(imageView);
        this.walkAnimCounter+=1;
    }
}
