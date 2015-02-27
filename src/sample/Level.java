package sample;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.URL;

/**
 * Created by jonathanbrodie on 12/20/14.
 */
public class Level {

    private double xTile=64;
    private double yTile=64;

    private int groundConstant=12;
    private String backgroundString="";
    private List<Block> blockList;
    private List<Enemy> enemyList;
    private List<Object> objectList;
    private String levelName;
    public Level() {
        this.blockList=new ArrayList<Block>();
        enemyList=new ArrayList<Enemy>();
        objectList=new ArrayList<Object>();
    }


    public void parseLevelFile(String fileName) {
        InputStream is = getClass().getResourceAsStream("/res/lvl/" + fileName);
        System.out.println(is);
        BufferedReader reader = null;
        try {
            //reader = new BufferedReader(new FileReader(myFile));
            String text = null;
            int yAxis = 0;
            int xAxis = 0;
            reader=new BufferedReader(new InputStreamReader(is));
            System.out.println("YOLO");
            text=reader.readLine();
            while (text != null) {
                System.out.println(text);
                for (int i = 0; i < text.length(); i++) {
                    String newChar = text.charAt(i) + "";
                    String myTest="";
                    if (newChar.equals("[")) {
                        myTest="";
                        int j=i+1;
                        String sTest = text.charAt(j) + "";

                        while (!(sTest.equals("]"))) {
                            myTest = myTest + sTest;
                            j++;
                            sTest = text.charAt(j) + "";
                        }
                        text = text.substring(0, i) + text.substring(i + myTest.length()+1, text.length());
                    }
                    else  myTest=newChar;
                    System.out.println(myTest);
                    this.placeLevelItem(myTest, xAxis, yAxis);

                    xAxis += 1;
                }
                xAxis = 0;
                yAxis += 1;
                //System.out.println("Let's go again");
                text=reader.readLine();
            }
            //System.out.println("Smeega");
        }
        catch (IOException e) {
            System.out.println("ERROR: COULD NOT PARSE FILE");
        }
    }

    public void placeLevelItem(String item, int xAxis, int yAxis) {
        Block myBlock;
        Enemy myEnemy;
        Object myObject;
        if (item.equals("mb")) {
            myBlock=new MushroomBlock(xAxis*this.xTile,yAxis*this.yTile);
            this.addBlockToList(myBlock);
        }
        else if (item.equals("gb")) {
            myBlock=new GrassBlock(xAxis*this.xTile,yAxis*this.yTile);
            this.addBlockToList(myBlock);
        }
        else if (item.equals("eb")) {
            myBlock=new EndBlock(xAxis*this.xTile,yAxis*this.yTile);
            this.addBlockToList(myBlock);
        }
        else if (item.equals("db")) {
            myBlock=new DirtBlock(xAxis*this.xTile,yAxis*this.yTile);
            this.addBlockToList(myBlock);
        }
        else if (item.equals("sb")) {
            myBlock=new SolidBlock(xAxis*this.xTile,yAxis*this.yTile);
            this.addBlockToList(myBlock);
        }
        else if (item.equals("cb")) {
            myBlock=new CoinBlock(xAxis*this.xTile,yAxis*this.yTile);
            this.addBlockToList(myBlock);
        }
        else if (item.equals("eg")) {
            myEnemy=new Goomba(xAxis*this.xTile,yAxis*this.yTile);
            this.addEnemyToList(myEnemy);
        }
        else if (item.equals("ek")) {
            myEnemy=new Koopa(xAxis*this.xTile,yAxis*this.yTile);
            this.addEnemyToList(myEnemy);
        }

    }

    public String getBackgroundString() {
        return this.backgroundString;
    }
    public void setBackgroundString(String myString) {
        this.backgroundString=myString;
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
    public String getLevelName() {
        return this.levelName;
    }
    public void setLevelName(String myString) {
    this.levelName=myString;
    }
    public void drawGroundRectangle(double xPosition, double yPosition, double xEnd, double yEnd) {
        Block grass;
        Block dirt;
        while (xPosition < xEnd) {
            double tempY=yPosition;

            grass=new GrassBlock(xPosition, yPosition);
            this.addBlockToList(grass);

            while (tempY < yEnd) {
                dirt=new DirtBlock(xPosition, tempY+64);
                this.addBlockToList(dirt);
                tempY+=64;
            }
            xPosition+=63;
        }

    }
}

