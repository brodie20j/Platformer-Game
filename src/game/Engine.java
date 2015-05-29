package game;

/**
 * Created by jonathanbrodie on 11/26/14.
 */

import game.AI.EnemyState;
import game.util.ObjectComparator;
import game.AI.State;
import javafx.scene.media.AudioClip;

import java.util.*;
import java.util.Collections;


public class Engine {


    private Hero mainHero;
    private List<Block> blockList;
    private List<Enemy> enemyList;
    private List<Object> objectList;
    private List<Object> completeList;
    private double currentAcceleration=0;
    private int coinCount;
    private boolean courseCompleted;
    private final int CONSTANT_CLASS_BEAM=1;
    private final int CONSTANT_CLASS_PLASMA=2;
    private final int CONSTANT_CLASS_DEITY=3;

    public Engine(LevelTable levelTable) {
        if (this.mainHero ==null) {
            this.mainHero =new Hero(0,0,0);
        }

        Level currentLevel=this.convertGraphToLevel(levelTable);
        this.blockList=currentLevel.getBlockList();
        this.enemyList=currentLevel.getEnemyList();
        this.objectList=currentLevel.getObjectList();
        this.completeList=currentLevel.getCompleteList();
        completeList.add(this.mainHero);

        //sort the big list by x position
        Collections.sort(completeList,new ObjectComparator());
        this.courseCompleted=false;
    }
    public Engine(Level level) {
        if (this.mainHero ==null) {
            this.mainHero =new Hero(0,0,0);
        }
        //currentLevel=new Level1_1();

        Level currentLevel=level;
        this.blockList=currentLevel.getBlockList();
        this.enemyList=currentLevel.getEnemyList();
        this.objectList=currentLevel.getObjectList();
        this.completeList=currentLevel.getCompleteList();
        completeList.add(this.mainHero);
        //sort the big list by x position
        Collections.sort(completeList,new ObjectComparator());
        this.courseCompleted=false;
    }

    public Level convertGraphToLevel(LevelTable graph) {
        Level myLevel= new Level();

        List<String> keyList=graph.getKeyList();
        String currentKey;
        for (int i=0; i<keyList.size(); i++) {
            currentKey=keyList.get(i);
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

    public void step() {

        //sort the big list by x position
        this.marioStep();

        this.enemyListStep();
        this.blockListStep();
        this.objectListStep();

        System.out.println(this.completeList.size());

        this.completeList=new ArrayList<Object>();
        this.completeList.addAll(this.getBlockList());
        this.completeList.addAll(this.getObjectList());
        this.completeList.addAll(this.getEnemyList());
        this.completeList.add(this.getMario());



        Collections.sort(this.completeList, new ObjectComparator());

    }
    public List<Object> getDrawList() {
        List<Object> completeList=this.completeList;
        return completeList;

    }

    public void enemyListStep() {
        Enemy currentEnemy;
        for (int i = 0; i < this.enemyList.size(); i++) {
            currentEnemy = this.enemyList.get(i);
            if (Math.abs(currentEnemy.getLayoutX() - this.mainHero.getLayoutX()) < 500) {
                this.applyGravity(currentEnemy);
                currentEnemy.updateAI(this.getEnemyState(currentEnemy));
                currentEnemy.step();

                List<Object> tempEnemyList = this.getCollisionDetection(currentEnemy);
                for (int j = 0; j < tempEnemyList.size(); j++) {
                    Object currentObject = tempEnemyList.get(j);
                    //Handling the Enemy hitting the hero
                    if (currentObject instanceof Hero) {
                        this.handlePlayerHit(currentEnemy);
                    } else if (currentObject instanceof HeroWeapon) {
                        this.handleEnemyHit(currentEnemy);
                        this.destroyObject(currentObject);
                    }
                }
                if (currentEnemy.getDead()) destroyObject(currentEnemy);
                this.handleCollisions(currentEnemy);
            }

        }
    }
    public void blockListStep() {

    }
    public void objectListStep() {
        Object currentObject;
        //Only step through the objects on the screen.
        for (int i=0; i<this.objectList.size(); i++) {
            currentObject = this.objectList.get(i);
            if (currentObject == this.mainHero) {
                continue;
            }
            //only step through the objects on the screen.

            if (Math.abs(currentObject.getLayoutX()-this.mainHero.getLayoutX()) < 500) {
                currentObject.step();
                this.handleCollisions(currentObject);
            }
            else {
                if (currentObject instanceof HeroWeapon) {
                    this.destroyObject(currentObject);
                }
                if (currentObject instanceof EnemyWeapon) {
                    this.destroyObject(currentObject);
                }

            }



            //currentObject.step();

        }


    }

    public List<Object> getCollisionDetection(Object oObject) {
        List<Object> collisionList=new ArrayList<Object>();

        //binary search the list for it, then since it's sorted by xLayout, do a search from both directions until there
        //is no longer a collision
        int i;
        //return this.completeList;

        Object currentObject;
        for (int j=0; j<completeList.size(); j++) {

        }


        if (this.completeList.size() >= 2) {

            i = Collections.binarySearch(this.completeList,oObject,new ObjectComparator());


        }
        else return collisionList;
        if (i == -1) {
            System.out.println("Could not find the object in the list!!!!");
            return collisionList;
        }
        else {
            int k = i - 1;
            int j = i + 1;

            while (k >= 0) {
                if (this.completeList.get(k).collision(oObject)) {
                    collisionList.add(this.completeList.get(k));
                }
                if (Math.abs(this.completeList.get(k).getLayoutX() - oObject.getLayoutX()) > (oObject.getWidth()+this.completeList.get(k).getWidth())) {
                    break;
                }
                k = k - 1;
            }
            if (j >= 0) {
                while (j < this.completeList.size()) {
                    if (this.completeList.get(j).collision(oObject)) {

                        collisionList.add(this.completeList.get(j));
                    }
                    if (Math.abs(this.completeList.get(j).getLayoutX() - oObject.getLayoutX()) > (oObject.getWidth()+this.completeList.get(j).getWidth())) {
                        break;
                    }
                    j = j + 1;
                }
            }
        }

    return collisionList;
    }
    public void applyGravity(Object oObject) {
        if (oObject.inAir) {
            if (oObject.getVelocityY() < 0) {
                System.out.print("foor");
                oObject.setVelocityY(oObject.getVelocityY() * 0.972);
            }
            double Yaccel = oObject.getVelocityY() * 0.5;
            if (Yaccel < 1) Yaccel = 1;
            oObject.setVelocityY(oObject.getVelocityY() + Yaccel);

        } else if (oObject.getVelocityY() >= 0) oObject.setVelocityY(0);
    }
    public void handleCollisions(Object oObject) {
        List<Object> tempBlockList = this.getCollisionDetection(oObject);
        Object currentBlock;
        //Assume oObject is in the air
        boolean myBool=true;
        for (int i = 0; i < tempBlockList.size(); i++) {
            currentBlock = tempBlockList.get(i);
            if (currentBlock == oObject) {
                continue;
            }
            else if ((oObject instanceof HeroWeapon) || (oObject instanceof EnemyWeapon)) {
                if (currentBlock.getClass().isAssignableFrom(BreakableBlock.class)) {
                    destroyObject(currentBlock);
                    destroyObject(oObject);
                    return;
                }
                if (oObject instanceof EnemyWeapon) {
                    System.out.println("Enemy weapon instance");
                    if (currentBlock instanceof Hero) {
                        Enemy enemyWeapon=(Enemy) oObject;
                        this.handlePlayerHit(enemyWeapon);
                        }
                }
            }
            else if (currentBlock.getSolid()) {
                //COLLISION DETECTION: If the object is solid and oObject is in the air, we check if oObject is on top of
                //currentBlock
                if (oObject.collisionDown(currentBlock)) {
                    oObject.inAir = false;
                    myBool=false;
                    oObject.setLayoutY(currentBlock.getLayoutY() - oObject.getHeight());
                    if ((oObject.getVelocityY() != 0)) {
                        oObject.setVelocityY(0);
                    }

                }
                else if (oObject.collisionUp(currentBlock)) {
                    oObject.setVelocityY(0);

                    if (oObject.getLayoutY() < currentBlock.getHeight() + currentBlock.getLayoutY()) {
                        oObject.setLayoutY(currentBlock.getHeight() + currentBlock.getLayoutY());
                    }

                }
                //now determine if
                if (oObject.collisionRight(currentBlock) && (!oObject.collisionDown(currentBlock))) {
                    if (oObject == this.mainHero) {
                        if (this.currentAcceleration > 0) this.currentAcceleration = 0;
                    }
                    oObject.setVelocityX(0);
                    if (oObject.getLayoutX() + oObject.getWidth() > currentBlock.getLayoutX())
                        oObject.setLayoutX(currentBlock.getLayoutX() - oObject.getWidth() - 1);
                } else if (oObject.collisionLeft(currentBlock) && (!oObject.collisionDown(currentBlock))) {
                    if (oObject == this.mainHero) {
                        if (this.currentAcceleration < 0) this.currentAcceleration = 0;
                    }
                    oObject.setVelocityX(0);
                    if (oObject.getLayoutX() < currentBlock.getLayoutX() + currentBlock.getWidth())
                        oObject.setLayoutX(currentBlock.getLayoutX() + currentBlock.getWidth() + 1);
                }
            }
            else if (currentBlock instanceof Coin) {
                this.setCoinCount(this.getCoinCount()+1);
                this.destroyObject(currentBlock);
            }

            else if (currentBlock instanceof HealthPotion) {
                //check if the caller is an enemy or player
                HealthPotion myPotion=(HealthPotion) currentBlock;
                if (oObject instanceof Hero) {
                     Hero myHero=((Hero) oObject);
                    myPotion.heal(myHero);
                    this.destroyObject(myPotion);
                }

            }


        }
        if (myBool) {
            oObject.inAir=true;
        }
    }
    public double getCurrentAcceleration() {
        return this.currentAcceleration;
    }
    public void setCurrentAcceleration(double newAcceleration) {
        this.currentAcceleration=newAcceleration;
    }
    public Hero getMario() {
        return this.mainHero;
    }
    public List<Block> getBlockList() {
        return this.blockList;
    }
    public List<Enemy> getEnemyList() {
        return this.enemyList;
    }
    public List<Object> getCompleteList() {
        return this.completeList;
    }
    public List<Object> getObjectList() {
        return this.objectList;
    }
    public int getCoinCount() {
        return this.coinCount;
    }
    public boolean getCompleted() {
        return this.courseCompleted;
    }
    public void setCoinCount(int newCount) {
        this.coinCount=newCount;
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
    public void handleAButton() {
        if (!this.mainHero.inAir) {
            this.mainHero.setVelocityY(this.mainHero.getVelocityY()-30);
            this.playSound("mario_jump.wav");
        }
    }
    public void handleUp() {
        this.mainHero.setFacingUp(true);
    }
    public void handleUpReleased() {
        this.mainHero.setFacingUp(false);
    }
    public void playSound(String sPathToFile) {
        AudioClip clip=new AudioClip(getClass().getResource("/res/sfx/" + sPathToFile).toString());
        clip.play();
    }
    public void handleBButton() {
        int heroClass=this.mainHero.getCharacterWeapon();

        int instanceCount=0;
        this.mainHero.Attack();
        for (int i=0; i<this.objectList.size(); i++) {
            if (this.objectList.get(i) instanceof HeroWeapon) {
                instanceCount++;
            }
        }
        if (instanceCount < 4) {
            HeroWeapon weapon=new HeroWeapon(this.mainHero.getLayoutX(),this.mainHero.getLayoutY()+32,this.mainHero.bXOrientation);

            this.addObjectToList(weapon);
        }
        switch (heroClass) {
            case 0:
                System.out.println("Normal");
            break;
            case 1:
                System.out.println("Ice");
            break;
            case 2:
                System.out.println("Fire");
            case 3:
                System.out.println("Deity");
            break;
        }
    }
    public void handleBButtonReleased() {

    }
    public void marioStep() {
        this.mainHero.step();

        if (Math.abs(this.mainHero.getVelocityY()) > 30) {
            int nConstant=1;
            if (this.mainHero.getVelocityY() < 0) {
                nConstant=-1;
            }
            this.mainHero.setVelocityY(30*nConstant);
        }


        if (Math.abs(this.mainHero.getVelocityX()) > 10) {
            if (this.mainHero.getVelocityX() < 0) {
                this.mainHero.setVelocityX(-10);
            }
            else this.mainHero.setVelocityX(10);
        }
        System.out.println(this.currentAcceleration);

        this.mainHero.setVelocityX(this.mainHero.getVelocityX()+this.currentAcceleration);
        //apply friction if on the ground?
        this.mainHero.setVelocityX(this.mainHero.getVelocityX() * 0.9);

        this.handleCollisions(this.mainHero);

    }
    private void handleEnemyHit(Enemy enemy) {
        enemy.setHP(enemy.getHP()-this.mainHero.getPower());
        if (enemy.getHP() <= 0) {
            boolean nbool=this.enemyList.remove(enemy);
            System.out.println("NBOOL:");
            if (nbool) enemy=null;
            System.out.print(nbool);
        }
}
    private void handlePlayerHit(Enemy currentEnemy) {
        if (!this.mainHero.getInvincibility()) {
            this.mainHero.setCurrentHP(this.mainHero.getCurrentHP() - 1);
            if (this.mainHero.getLayoutX() + this.mainHero.getWidth() < currentEnemy.getLayoutX() + (currentEnemy.getWidth() / 2)) {
                this.mainHero.setLayoutX(this.mainHero.getLayoutX() - currentEnemy.getWidth());
                this.mainHero.setVelocityX(0);
            } else if (this.mainHero.getLayoutX() > currentEnemy.getLayoutX() + (currentEnemy.getWidth() / 2)) {
                this.mainHero.setLayoutX(this.mainHero.getLayoutX() + currentEnemy.getWidth());
                this.mainHero.setVelocityX(0);
            }
            this.mainHero.setInvincibility(true);
        }
        else {

        }
    }
    public void destroyObject(Block object) {
        boolean bFound = false;
        for (int i = 0; i < this.blockList.size(); i++) {
            if (this.blockList.get(i) == object) {
                bFound = true;
                this.blockList.remove(object);
            }
        }
        object.getChildren().clear();
        object=null;
    }
    public void destroyObject(Enemy object) {
        boolean bFound = false;
        for (int i = 0; i < this.enemyList.size(); i++) {
            if (this.enemyList.get(i) == object) {
                this.enemyList.remove(object);
                break;
            }
        }
        object.getChildren().clear();
        object=null;
    }
    public void destroyObject(Object object) {
        boolean bFound=false;
        for (int i=0; i<this.objectList.size(); i++) {
            if (this.objectList.get(i) == object) {
                bFound=true;
                this.objectList.remove(object);
            }
        }
        while (object.getChildren().size() != 0) object.getChildren().clear();
        object=null;
    }
    public void Finish() {
        this.courseCompleted=true;
    }
    private double getDistance(Object object1, Object object2) {
        return Math.sqrt(((object1.getLayoutY()-object2.getLayoutY())*(object1.getLayoutY()-object2.getLayoutY()))+
                ((object1.getLayoutX()-object2.getLayoutX())*(object1.getLayoutX()-object2.getLayoutX())));
    }
    private double getDistanceX(Object object1, Object object2) {
       return Math.sqrt((object1.getLayoutX()-object2.getLayoutX())*(object1.getLayoutX()-object2.getLayoutX()));
    }
    private double getDistanceY(Object object1, Object object2) {
        return Math.sqrt((object1.getLayoutY()-object2.getLayoutY())*(object1.getLayoutY()-object2.getLayoutY()));
    }

    public EnemyState getEnemyState(Enemy enemy) {
        return new EnemyState(this.mainHero,enemy,this.getObjectList(),this.getEnemyList(),this.getBlockList());
    }
    public State getState() {
        return new State(this.mainHero,this.getObjectList(),this.getEnemyList(),this.getBlockList());
    }

}
