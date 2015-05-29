package game;

import game.AI.Action;
import game.AI.EnemyIntelligence;
import game.AI.EnemyState;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.PriorityQueue;

/**
 * Created by jonathanbrodie on 11/26/14.
 */
public class Enemy extends Object {
    private int power;
    private int hp;
    private int maxhp;
    private boolean bDead;
    private int walkAnimCounter=0;
    private boolean bAnim=false;
    public boolean bXOrientation=true;
    private EnemyIntelligence myAI;
    private PriorityQueue<Action> ActionQueue=new PriorityQueue<Action>();
    private boolean bAttacking=false;
    private int runSpeed=4;

    private final Action GLOBAL=new Action(-1);


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
        if (myAI != null)  {
            this.myAI.step();
        }
        if (this.ActionQueue.isEmpty()) return;
        Action currentAction=this.ActionQueue.poll();
        //currentAction.doAction(this);
        this.handleAction(currentAction);

        this.setLayoutX(this.getLayoutX() + this.getVelocityX());
        this.setLayoutY(this.getLayoutY() + this.getVelocityY());
        if (this.hp <= 0) {
            this.bDead=true;
        }
        if (this.getVelocityX()<0) {
            this.bXOrientation=false;
        }
        else if (this.getVelocityX()>0) {
            this.bXOrientation=true;
        }


    }
    //Updates the sprite based on gravity effects, ect.
    //This means that this method is only called when the object steps
    public void updateSprite() {
       // Class<?> enclosingClass = this.getClass().getEnclosingClass();
        String className=this.getClass().getSimpleName().toLowerCase();
        String myString="";

        System.out.println(this.isAttacking());
        System.out.println("...");

        if (this.isAttacking()) {
            System.out.println("Attack");
            //attacking animations go here
            myString=className+"_idle";
        }
        else if (this.getVelocityX() != 0) {
            if (this.walkAnimCounter >= 4) {
                if (bAnim) myString=className+"_idle";
                else myString=className+"_walking";
                bAnim=!bAnim;
                walkAnimCounter=0;
            }
            else {
                myString=className+"_walking";
            }
            System.out.println("dad is here");
            walkAnimCounter+=1;
        }
        else {
            System.out.println("IN ELSE CLAUSE");
            myString=className+"_idle";
        }
        System.out.println("....");

        if (this.bXOrientation) myString+="r";

        myString+=".png";
        System.out.println(myString);
        this.setSprite(myString);
    }

    public void turnAround() {
        this.bXOrientation=!this.bXOrientation;
        this.setVelocityX(this.getVelocityX()*-1);
    }
    public void updateAI(EnemyState currentState) {
        if (this.myAI==null) {
            this.myAI=new EnemyIntelligence(currentState);
        }
        else {
            this.myAI.updateState(currentState);
        }
    }
    public void Attack() {
        if (this.bAttacking) return;
        this.bAttacking=true;
    }
    public boolean isAttacking() {
        return this.bAttacking;
    }
    public int getRunSpeed() {
        return this.runSpeed;
    }

    public void addAction(Action newAction) {
        this.ActionQueue.add(newAction);
    }
    public void clearActionQueue() {
        this.ActionQueue=new PriorityQueue<Action>();
    }

    /*
    handleAction(Action myAction)
    Override in subclasses to override default enemy behavior
    It would be wise to place this in the default of your new switch statement, as
    it will prevent exceptions and enemies unable to handle an action
     */
    public void handleAction(Action myAction) {
        switch (myAction.getAction()) {
            case 0:
                //Do nothing
                break;
            case 1:

                if (!this.isAttacking())
                this.Attack();
                break;
            case 2:
               this.setVelocityX(-1 * this.getRunSpeed());
                break;
            case 3:
                this.setVelocityX(this.getRunSpeed());
                break;
            case 4:
               this.setVelocityY(-20);
                break;
        }
    }
}
