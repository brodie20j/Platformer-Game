package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 11/26/14.
 */
public class Enemy extends Object {
    private int power;
    private int hp;
    private int maxhp;
    private boolean bDead;
    private ImageView imageView;
    private int walkAnimCounter=0;
    private boolean bAnim=false;
    public boolean bXOrientation=false;
    private String myString="";

    public Enemy(double startX, double startY) {
        this.setLayoutX(startX);
        this.setLayoutY(startY);
    }

    public Enemy(String sImageFile,int nHP) {
        this.bDead=false;
        this.hp=nHP;
        this.maxhp=nHP;
        this.setSprite(sImageFile);
    }
    public Enemy() {
        this.bDead=false;
        this.hp=1;
        this.maxhp=1;
    }

    public void setHP(int newHP) {
        this.hp=newHP;
        if (this.hp <= 0) {
            bDead=true;
        }
    }
    private void setMaxHP(int newHP) {
        this.maxhp=newHP;
    }
    public void setPower(int newPower) {
        this.power=newPower;
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
    @Override
    public void step() {

        this.setLayoutX(this.getLayoutX() + this.getVelocityX());
        this.setLayoutY(this.getLayoutY() + this.getVelocityY());
        if (this.hp <= 0) {
            this.bDead=true;
        }
        //updateSprite();
    }
    //Updates the sprite based on gravity effects, ect.
    //This means that this method is only called when the object steps
    public void updateSprite() {
       // Class<?> enclosingClass = this.getClass().getEnclosingClass();
        String className=this.getClass().getSimpleName().toLowerCase();
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
            this.bXOrientation=false;
        } else if (this.getVelocityX() < 0) {
            if (this.walkAnimCounter >= 4) {
                if (bAnim) myString="/res/img/"+className+"_idle.png";
                else myString="/res/img/"+className+"_walking.png";
                bAnim=!bAnim;
                walkAnimCounter=0;

            }
            walkAnimCounter+=1;
            this.bXOrientation=true;
            image = new Image(getClass().getResourceAsStream(myString));
        } else {
            if (this.bXOrientation) image = new Image(getClass().getResourceAsStream("/res/img/"+className+"_idle.png"));
            else image = new Image(getClass().getResourceAsStream("/res/img/"+className+"_idle.png"));
        }
        //image = new Image(getClass().getResourceAsStream("/res/img/mario_idle.png"));

        this.imageView.setImage(image);
        this.getChildren().clear();
        this.getChildren().add(imageView);
        this.walkAnimCounter+=1;
    }

    //loads sprite based on path '/res/img/yourfilepathhere'
    public void setSprite(String sFileName) {
        this.getChildren().clear();
        Image image = new Image(getClass().getResourceAsStream(sFileName));
        imageView = new ImageView();
        imageView.setImage(image);
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
