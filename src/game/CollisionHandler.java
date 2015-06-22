package game;

/**
 * Created by jonathanbrodie on 5/31/15.
 */
import game.AI.*;
import game.util.Transitions;

import java.util.List;

public class CollisionHandler {
    private Object oObject1;
    private Object oObject2;
    private State myState;


    //Create a different constructor for each type of collision we

    public CollisionHandler(Object Object1,Object Object2) {
        this.oObject1=Object1;
        this.oObject2=Object2;
        this.handleCollisions();
    }
    public CollisionHandler(Hero myHero,Object oObject) {
        
    }
    private void handleCollisions() {
        if (this.oObject1.getClass().isAssignableFrom(BreakableBlock.class)) {
            Class object2=this.oObject2.getClass();
            if (object2.isAssignableFrom(EnemyWeapon.class)) {
                //do stuff
            }
            else if (object2.isAssignableFrom(Hero.class)) {
                Hero myHero=(Hero) this.oObject2;
                if (myHero.isAttacking()) {
                    //do stuff
                }
            }
            else {
                
            }
        }

        else if (this.oObject2.getSolid()) {
            //COLLISION DETECTION: If the object is solid and oObject is in the air, we check if oObject is on top of
            //this.oObject2
            if (this.oObject1.collisionDown(this.oObject2)) {
                this.oObject1.inAir = false;
                this.oObject1.setLayoutY(this.oObject2.getLayoutY() - this.oObject1.getHeight());
                if ((this.oObject1.getVelocityY() != 0)) {
                    this.oObject1.setVelocityY(0);
                }

            }
            else if (this.oObject1.collisionUp(this.oObject2)) {
                this.oObject1.setVelocityY(0);

                if (this.oObject1.getLayoutY() < this.oObject2.getHeight() + this.oObject2.getLayoutY()) {
                    this.oObject1.setLayoutY(this.oObject2.getHeight() + this.oObject2.getLayoutY());
                }

            }
            //now determine if we're colliding from sides
            if (this.oObject1.collisionRight(this.oObject2) && (!this.oObject1.collisionDown(this.oObject2))) {
                this.oObject1.setVelocityX(0);
                if (this.oObject1.getLayoutX() + this.oObject1.getWidth() > this.oObject2.getLayoutX())
                    this.oObject1.setLayoutX(this.oObject2.getLayoutX() - this.oObject1.getWidth() - 1);
            }
            else if (this.oObject1.collisionLeft(this.oObject2) && (!this.oObject1.collisionDown(this.oObject2))) {

                this.oObject1.setVelocityX(0);
                if (this.oObject1.getLayoutX() < this.oObject2.getLayoutX() + this.oObject2.getWidth())
                    this.oObject1.setLayoutX(this.oObject2.getLayoutX() + this.oObject2.getWidth() + 1);
            }
        }
        else if (oObject1 instanceof Hero) {
            Hero hero=(Hero) this.oObject1;
            //this.handleCombat(hero,this.oObject1);
        }

    }
    public static void handleCollision(Hero myHero, Object oObject2) {
        Class objectclass=oObject2.getClass();

        if (objectclass.isAssignableFrom(BreakableBlock.class)) {

            //do stuff here
        }
        if (oObject2.getSolid()) {
            if (myHero.collisionDown(oObject2)) {
                myHero.inAir=false;
                myHero.setLayoutY(oObject2.getLayoutY() - myHero.getHeight());
                if ((myHero.getVelocityY() != 0)) {
                    myHero.setVelocityY(0);
                }

            }
            else if (myHero.collisionUp(oObject2)) {
                myHero.setVelocityY(0);

                if (myHero.getLayoutY() < oObject2.getHeight() + oObject2.getLayoutY()) {
                    myHero.setLayoutY(oObject2.getHeight() + oObject2.getLayoutY());
                }

            }
            //now determine if we're colliding from sides
            if (myHero.collisionRight(oObject2) && (!myHero.collisionDown(oObject2))) {
                myHero.setVelocityX(0);
                if (myHero.getLayoutX() + myHero.getWidth() > oObject2.getLayoutX())
                    myHero.setLayoutX(oObject2.getLayoutX() - myHero.getWidth() - 1);
            }
            else if (myHero.collisionLeft(oObject2) && (!myHero.collisionDown(oObject2))) {

                myHero.setVelocityX(0);
                if (myHero.getLayoutX() < oObject2.getLayoutX() + oObject2.getWidth())
                    myHero.setLayoutX(oObject2.getLayoutX() + oObject2.getWidth() + 1);
            }
        }
        else if (oObject2 instanceof Door) {
            if (myHero.getFacingUp()) {
                //transition time!
                Transitions.DoorTransition((Door) oObject2,myHero);
            }
        }

    }


}
