package game;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 11/26/14.
 */


public class Hero extends Object {

    private int maxHP=5;
    private int currentHP=5;
    private int power=1;
    private boolean bInvincible=false;
    private int invincibleCount=0;
    private ImageView imageView;

    private int walkAnimCounter=0;
    private boolean bAnim=false;
    public boolean bXOrientation=false;
    private String myString="/res/img/mario_idler.png";
    private int weaponType=-1;
    private boolean facingUp=false;
    public Hero(double startX, double startY, int myCLASS) {
        Image image = new Image(getClass().getResourceAsStream("/res/img/mariotest2.png"));

        this.imageView = new ImageView();
        imageView.setImage(image);
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.getChildren().add(imageView);
        this.weaponType=myCLASS;


    }
    public boolean getFacingUp() {
        return this.facingUp;
    }
    public void setFacingUp(boolean bool) {
        this.facingUp=bool;
    }
    public int getCharacterWeapon() {
        return this.weaponType;
    }
    public void setWeaponType(int newType) {
        this.weaponType=newType;
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
    @Override
    public void step() {

        this.setLayoutX(this.getLayoutX() + this.getVelocityX());
        this.setLayoutY(this.getLayoutY() + this.getVelocityY());


        if (Math.abs(this.getVelocityX()) < 2.0) this.setVelocityX(0);

        if (this.bInvincible) {
            if (invincibleCount > 24) invincibleCount = 0;
            else if (invincibleCount == 24) {
                this.setInvincibility(false);
            }
            if (this.getOpacity() == 0) this.setOpacity(1);
            else this.setOpacity(0);
            invincibleCount = invincibleCount + 1;
        }
        else {
            this.setOpacity(1);

        }

        if (this.inAir) {
            if (this.getVelocityY() < 0) {
                this.setVelocityY(this.getVelocityY()*0.972);
            }
            this.setVelocityY(this.getVelocityY()+3);

        }
        //else if (this.getVelocityY() >= 0) this.setVelocityY(0);


        this.updateSprite();

    }

    public void updateSprite() {
        this.imageView = new ImageView();
        Image image;
        if (this.inAir) {
            //falling animation
            if (!this.bXOrientation && this.getVelocityX() >= 0) {
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
            this.bXOrientation = false;
            }
            else if (this.getVelocityX() < 0) {
                if (this.walkAnimCounter >= 4) {
                    if (bAnim) myString="/res/img/mario_idle.png";
                    else myString="/res/img/mario_walking.png";
                    bAnim=!bAnim;
                    walkAnimCounter=0;
                }
                walkAnimCounter+=1;
                this.bXOrientation=true;
                image = new Image(getClass().getResourceAsStream(myString));
            } else {
                if (!this.bXOrientation) image = new Image(getClass().getResourceAsStream("/res/img/mario_idler.png"));
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
