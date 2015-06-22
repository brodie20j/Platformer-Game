package game.AI;

/**
 * Created by jonathanbrodie on 5/26/15.
 */
import game.*;
public class Action {
    //Actions for all enemies
    public static final int ACTION_NO_ACTION=0;
    public static final int ACTION_ATTACK=1;
    public static final int ACTION_MOVE_LEFT=2;
    public static final int ACTION_MOVE_RIGHT=3;
    public static final int ACTION_JUMP=4;
    //these ones may not exist for certain enemies
    public static final int ACTION_ATTACK_RANGED=5;
    public static final int ACTION_ATTACK_SPECIAL=6;

    private int myAction=-1;
    public Action(int action) {
        this.myAction=action;
    }

    public int getAction() {
        return this.myAction;
    }

}
