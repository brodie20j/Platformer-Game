package game.AI;
import game.*;
import game.Object;
import game.Block;

import java.util.List;

/**
 * Created by jonathanbrodie on 5/18/15.
 */
public class State {
    private List<game.Object> objectList;
    private List<Block> blockList;
    private List<Enemy> enemyList;
    private Hero mainHero;
    public State() {

    }
    public State(Hero mainHero, List<Object> list1, List<Enemy> list2, List<Block> list3) {
        this.setHero(mainHero);
        this.setObjectList(list1);
        this.setEnemyList(list2);
        this.setBlockList(list3);
    }
    public void setObjectList(List<Object> list) {
        this.objectList=list;
    }
    public void setBlockList(List<Block> list) {
        this.blockList=list;
    }
    public void setEnemyList(List<Enemy> list) {
        this.enemyList=list;
    }
    public void setHero(Hero myHero) {
        this.mainHero=myHero;
    }
    public Hero getHero() {
        return this.mainHero;
    }
    public List<Block> getBlockList() {
        return this.blockList;
    }
    public List<Enemy> getEnemyList() {
        return this.enemyList;
    }
    public List<Object> getObjectList() {
        return this.objectList;
    }
}
