package game.AI;

/**
 * Created by jonathanbrodie on 5/26/15.
 */

import game.*;
import game.Object;

public class EnemyIntelligence implements ArtificialIntelligence {
    private EnemyState currentState;
    private Enemy body;

    private int coolDownCount=0;
    private boolean coolingDown=false;
    private int coolDownWait=70;
    private int type=0;

    public EnemyIntelligence(EnemyState myState) {
        this.currentState=myState;
        this.body=myState.getEnemy();
        this.setAI(1);
    }
    public EnemyIntelligence() {
        this.setAI(1);
    }

    public void setAI(int newAI) {
        this.type=newAI;
    }
    public int getAI() {
        return this.type;
    }
    public void setCurrentState(EnemyState myState) {
        this.currentState=myState;
        this.body=myState.getEnemy();
    }
    public EnemyState getCurrentState()  {
        return this.currentState;
    }
    public void updateState(EnemyState myState) {
        this.currentState=myState;
        this.body=myState.getEnemy();
    }
    @Override
    public void step() {
        //Update AI based on current state
        System.out.println("mom?");

        if (this.currentState.CanSeePlayer()) {
            Hero myHero=this.currentState.getHero();

            if (this.getAI() < 1) {
                System.out.println("What the hell?  How did we get here?  AI < 1");
                this.setAI(AI_TYPE_NORMAL);
                return;
            }
            double dist=getDistance(myHero,this.body);
            if (dist < 200) {
                //do something i'm tired
                this.setAI(AI_TYPE_ATTACK);
            }
        }
        //Stroll around or chill out
        else {
            this.setAI(AI_TYPE_NORMAL);
        }
        this.adjustCoolDown();

        this.handleAIType();
    }

    protected void adjustCoolDown() {
        if (this.coolingDown) {
            this.coolDownCount+=1;
            if (coolDownCount >coolDownWait) {
                this.coolDownCount=0;
                this.coolingDown=false;
            }
        }
    }
    protected final void handleAIType() {
       //EnemyState successor = this.currentState.getSuccessor();
        int myAction=Action.ACTION_NO_ACTION;
        int nAI=this.getAI();

        switch (nAI) {
            case AI_TYPE_NORMAL:
                //check to see if successor is in the air, if we're normal, then we just want to turn away from the edge
                /*
                if (successor.getEnemy().inAir) {
                    this.body.turnAround();
                }
                */
                break;
            case AI_TYPE_ATTACK:
                //Rushes the player if close combat oriented
                Hero myHero = this.currentState.getHero();
                int nConstant = 1;
                if (this.getDistance(myHero, this.body) > 3) {
                    if (this.body.getLayoutX() - currentState.getHero().getLayoutX() >= 0) {
                        myAction = Action.ACTION_MOVE_LEFT;
                    } else {
                        myAction = Action.ACTION_MOVE_RIGHT;
                    }
                } else {
                    //do attack thingy
                    if (!this.coolingDown) {
                        myAction=Action.ACTION_ATTACK;
                        this.coolingDown = true;
                    }
                }
                break;
            case AI_TYPE_BOSS:
                myAction=Action.ACTION_ATTACK_SPECIAL;
                break;
        }
        this.body.addAction(new Action(myAction));
    }
    private double getDistance(Object object1, Object object2) {
        return Math.sqrt(((object1.getLayoutY()-object2.getLayoutY())*(object1.getLayoutY()-object2.getLayoutY()))+
                ((object1.getLayoutX()-object2.getLayoutX())*(object1.getLayoutX()-object2.getLayoutX())));
    }
}
