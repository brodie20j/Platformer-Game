package game.AI;

/**
 * Created by jonathanbrodie on 5/26/15.
 */
import game.*;
public class Action {
    //Actions for all enemies
    public final int ACTION_NO_ACTION=0;
    public final int ACTION_ATTACK=1;
    public final int ACTION_MOVE_LEFT=2;
    public final int ACTION_MOVE_RIGHT=3;
    public final int ACTION_JUMP=4;
    //these ones may not exist for certain enemies
    public final int ACTION_ATTACK_RANGED=5;
    public final int ACTION_ATTACK_SPECIAL=6;

    private int myAction=-1;
    public Action(int action) {
        this.myAction=action;
    }

    public void doAction(Enemy myEnemy) {
        switch (this.myAction) {
            case ACTION_NO_ACTION:
                //Do nothing
                break;
            case ACTION_ATTACK:
                //
                //if (!myEnemy.isAttacking())
                //myEnemy.Attack();
                break;
            case ACTION_MOVE_LEFT:
                myEnemy.setVelocityX(-1*myEnemy.getRunSpeed());
                break;
            case ACTION_MOVE_RIGHT:
                myEnemy.setVelocityX(myEnemy.getRunSpeed());
                break;
            case ACTION_JUMP:
                myEnemy.setVelocityY(-20);
                break;
        }
    }
    public int getAction() {
        return this.myAction;
    }

}
