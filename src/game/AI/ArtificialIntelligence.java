package game.AI;

/**
 * Created by jonathanbrodie on 3/22/15.
 */
public interface ArtificialIntelligence {

    public final int AI_TYPE_ERROR=-1;
    public final int AI_TYPE_NOTHING=0;
    public final int AI_TYPE_NORMAL=1;
    public final int AI_TYPE_ATTACK=2;
    public final int AI_TYPE_CLOSE=3;
    public final int AI_TYPE_BOSS=4;

    public void step();
}
