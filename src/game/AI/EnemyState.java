package game.AI;
import game.*;
import game.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathanbrodie on 5/19/15.
 */
public class EnemyState extends State {
    Enemy myEnemy;


    public EnemyState(Hero mainHero, Enemy myEnemy,List<game.Object> list1, List<Enemy> list2, List<Block> list3) {

        this.setObjectList(list1);
        this.setEnemyList(list2);
        this.setBlockList(list3);
        this.setHero(mainHero);

        this.myEnemy=myEnemy;
    }
    public void setEnemy(Enemy myEnemy) {
        this.myEnemy=myEnemy;
    }
    public Enemy getEnemy() {
        return this.myEnemy;
    }
    public boolean CanSeePlayer() {
        if (this.myEnemy==null) {
            System.out.println("Enemy State error: State doesn't have targeted enemy");
            return false;
        }

        List<Block> testList=new ArrayList<Block>();
        boolean bool=testList.isEmpty();
        List<Block> list=getBlockList();
        for (int i=0; i<list.size();i++) {
            Block currentBlock=list.get(i);
            if (this.myEnemy.getLayoutX() > this.getHero().getLayoutX()) {
                if ((currentBlock.getLayoutX() > this.getHero().getLayoutX()) &&
                        (currentBlock.getLayoutX() < this.myEnemy.getLayoutX())
                        ) {
                    testList.add(currentBlock);
                }
            }
            else {
                if ((currentBlock.getLayoutX() < this.getHero().getLayoutX()) &&
                        (currentBlock.getLayoutX() > this.myEnemy.getLayoutX())
                        ) {
                    testList.add(currentBlock);
                }
            }
        }
        if (bool == testList.isEmpty()) {
            return false;
        }
        return true;
    }
    //
    public EnemyState getSuccessor() {
        Level tempLev=new Level();
        for (int i=0; i< this.getBlockList().size(); i++) {
            tempLev.addBlockToList(this.getBlockList().get(i));
        }
        for (int i=0; i< this.getEnemyList().size(); i++) {
            tempLev.addEnemyToList(this.getEnemyList().get(i));
        }
        for (int i=0; i< this.getObjectList().size(); i++) {
            tempLev.addObjectToList(this.getObjectList().get(i));
        }
        Engine temp=new Engine(tempLev);
        temp.step();
        EnemyState newState=new EnemyState(this.getHero(),this.myEnemy,temp.getObjectList(),temp.getEnemyList(),temp.getBlockList());
        return newState;
    }
    public EnemyState getSuccessor(int myAction) {
        Action newAction=new Action(myAction);
        this.myEnemy.addAction(newAction);

        Level tempLev=new Level();
        for (int i=0; i< this.getBlockList().size(); i++) {
            tempLev.addBlockToList(this.getBlockList().get(i));
        }
        for (int i=0; i< this.getEnemyList().size(); i++) {
            tempLev.addEnemyToList(this.getEnemyList().get(i));
        }
        for (int i=0; i< this.getObjectList().size(); i++) {
            tempLev.addObjectToList(this.getObjectList().get(i));
        }
        Engine temp=new Engine(tempLev);
        temp.step();
        EnemyState newState=new EnemyState(this.getHero(),this.myEnemy,temp.getObjectList(),temp.getEnemyList(),temp.getBlockList());
        return newState;
    }

}
