package game.AI;

import game.Enemy;

/**
 * Created by jonathanbrodie on 3/22/15.
 *
 *
 * An Object should never actually have this AI, this mostly serves to set up constants
 */
public class ArtificialIntelligence {

    public final int AI_TYPE_ERROR=-1;
    public final int AI_TYPE_NOTHING=0;
    public final int AI_TYPE_NORMAL=1;
    public final int AI_TYPE_ATTACK=2;
    public final int AI_TYPE_CLOSE=3;
    public int type=-1;

    public ArtificialIntelligence() {

    }
    public ArtificialIntelligence(int type) {
        this.setAI(type);
    }

    public void setAI(int type) {
        if (type < 0) {
            return;
        }
        this.type=type;
    }
    public int getAI() {
        return this.type;
    }
    public void step() {
        //nothing here I could think of at the moment
    }
}
