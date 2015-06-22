package LevelEdit;

import game.*;
import game.Object;
import game.Block;

import java.util.List;

/**
 * Created by jonathanbrodie on 5/13/15.
 */
public class PreviewEngine {

    Level currentLevel;
    public PreviewEngine(LevelTable level) {
        currentLevel=this.convertGraphToLevel(level);
    }
    public Level convertGraphToLevel(LevelTable graph) {
        Level myLevel= new Level();

        List<String> keyList=graph.getKeyList();
        String currentKey;
        for (int i=0; i<keyList.size(); i++) {
            currentKey=keyList.get(i);
            /*
            Uncomment the following code when you're ready to start trying to switch from four hashtables to one
            Object currentObject=graph.getObject(currentKey);

            if (currentObject instanceof Enemy) {
                Enemy newEnemy=(Enemy)currentObject;
                myLevel.addEnemyToList(newEnemy);
            }
            else if (currentObject instanceof Block) {
                Block newBlock=(Block)currentObject;
                myLevel.addBlockToList(newBlock);
            }
            else {
                myLevel.addObjectToList(currentObject);
            }

            */
            Class objectType=graph.getType(currentKey);
            Class objectID=graph.getID(currentKey);
            String objectClass=graph.getID(currentKey).getName();
            Block sampleBlock=new Block();
            Enemy sampleEnemy=new Enemy();

            //if we are instantiating an enemy


           Object mySample=new Object(graph.getX(currentKey),graph.getY(currentKey));
            //
            try {
                System.out.println(objectClass);
                System.out.println(objectID.isAssignableFrom(sampleEnemy.getClass()));
                if (sampleEnemy.getClass().isAssignableFrom(objectID)) {
                    System.out.println("Throw exception?");
                    Enemy newEnemy=(Enemy)Class.forName(objectClass).newInstance();
                    myLevel.addEnemyToList(newEnemy);
                    newEnemy.setLayoutX(graph.getX(currentKey));
                    newEnemy.setLayoutY(graph.getY(currentKey));
                }
                else if (sampleBlock.getClass().isAssignableFrom(objectID)) {
                    System.out.println("Throw exception?2");
                    System.out.println(objectClass);
                    Block newBlock=(Block) Class.forName(objectClass).newInstance();
                    myLevel.addBlockToList(newBlock);
                    newBlock.setLayoutX(graph.getX(currentKey));
                    newBlock.setLayoutY(graph.getY(currentKey));
                }
                else {
                    System.out.println("Throw exception?3");

                    Object newObject=(Object) Class.forName(objectClass).newInstance();
                    myLevel.addObjectToList(newObject);
                    newObject.setLayoutX(graph.getX(currentKey));
                    newObject.setLayoutY(graph.getY(currentKey));
                }
            }
            catch (InstantiationException x) {
                x.printStackTrace();
            } catch (IllegalAccessException x) {
                x.printStackTrace();
            }
            catch (ClassNotFoundException x) {
                x.printStackTrace();
            }
        }

        return myLevel;
    }
    public Level getCurrentLevel() {
        return this.currentLevel;
    }
    public void setCurrentLevel(LevelTable level) {
        this.currentLevel=this.convertGraphToLevel(level);
    }

}
