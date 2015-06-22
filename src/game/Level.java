package game;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * Created by jonathanbrodie on 12/20/14.
 */
public class Level implements Serializable{

    private double xTile=64;
    private double yTile=64;

    private int groundConstant=12;
    private String backgroundString="";
    private List<Block> blockList;
    private List<Enemy> enemyList;
    private List<Object> objectList;
    public String name;
    public Level() {
        this.blockList=new ArrayList<Block>();
        this.enemyList=new ArrayList<Enemy>();
        this.objectList=new ArrayList<Object>();
    }

    public List<Object> getCompleteList() {
        List<Object> mySet= new ArrayList<Object>();

        for (int i=0; i< this.blockList.size(); i++) {
            mySet.add(this.blockList.get(i));
        }
        for (int i=0; i< this.enemyList.size(); i++) {
            mySet.add(this.blockList.get(i));

        }
        for (int i=0; i< this.objectList.size(); i++) {
            mySet.add(this.objectList.get(i));
        }
        return mySet;
    }
    public void parseLevelFile(String fileName) {
        List<Object> levelList=new ArrayList<Object>();
        try {
            //reader = new BufferedReader(new FileReader(myFile));
            String text = null;
            int yAxis = 0;
            int xAxis = 0;
            ObjectInputStream obj = new ObjectInputStream(new FileInputStream("/res/lvl/" + fileName));
            int aslevelList= obj.readInt();
            //reader=new BufferedReader(new InputStreamReader(is));

        }
        catch (IOException e) {
            System.out.println("ERROR: COULD NOT PARSE FILE");
        }

        for (int i=0; i< levelList.size(); i++) {
            this.placeLevelItem(levelList.get(i));

        }
    }
    public void placeLevelItem(Block myBlock) {
        Block cBlock=myBlock;
        this.addBlockToList(cBlock);
    }

    public void placeLevelItem(Object myObject) {
        Object cBlock=myObject;
        this.addObjectToList(cBlock);
    }

    public void placeLevelItem(Enemy myObject) {
        Enemy cBlock=myObject;
        this.addEnemyToList(cBlock);
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
    public void addBlockToList(Block myBlock) {
        this.blockList.add(myBlock);
    }
    public void addEnemyToList(Enemy myEnemy) {
        this.enemyList.add(myEnemy);
    }
    public void addObjectToList(Object myObject) {
        this.objectList.add(myObject);
    }

}

