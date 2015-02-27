package sample;

/**
 * Created by jonathanbrodie on 11/26/14.
 */

import javafx.application.Platform;

import java.util.*;
import java.util.PriorityQueue;


public class Engine {


    private Hero mainHero;
    private List<Block> blockList;
    private List<Enemy> enemyList;
    private List<Object> objectList;
    public boolean inAir;
    private double currentAcceleration=0;
    private int coinCount;
    private boolean courseCompleted;

    //Idea, pass level into engine parameter?
    public Engine(Level currentLevel) {
        if (this.mainHero ==null) {
            this.mainHero =new Hero(0,0);
        }
        //currentLevel=new Level1_1();
        this.blockList=currentLevel.getBlockList();
        this.enemyList=currentLevel.getEnemyList();
        this.objectList=currentLevel.getObjectList();
        courseCompleted=false;

    }

    public void step() {
        this.marioStep();
        this.enemyListStep();
        this.blockListStep();
        this.objectListStep();
        this.mainHero.step();

    }

    public void enemyListStep() {
        List<Enemy> tempEnemyList=new ArrayList<Enemy>();
        Enemy currentEnemy;
        for (int i=0; i<this.enemyList.size(); i++) {
            currentEnemy = this.enemyList.get(i);
            applyEnemyPhysics(currentEnemy);
            this.GeneralEnemyAI(currentEnemy);
            currentEnemy.step();

            if (this.mainHero.collision(currentEnemy)) {
                tempEnemyList.add(currentEnemy);
            }
        }

        for (int i=0; i<tempEnemyList.size(); i++) {
            currentEnemy=tempEnemyList.get(i);
            if (this.mainHero.collisionDown(currentEnemy)) {
                handleMarioStomp(currentEnemy);
                if (currentEnemy.getHP() <= 0) {
                    destroyObject(currentEnemy);
                }
            }
            else {
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
            }
        }

    }
    public void blockListStep() {

    }

    public void objectListStep() {
        Object currentObject;

        List<Object> tempObjectList=new ArrayList<Object>();

        for (int i=0; i<this.objectList.size(); i++) {
            currentObject = this.objectList.get(i);
            currentObject.step();
            if (this.mainHero.collision(currentObject)) {
                tempObjectList.add(currentObject);
            }
        }
        //boolean canTransport=false;
        for (int i=0; i<tempObjectList.size(); i++) {
            currentObject=tempObjectList.get(i);

            if (currentObject instanceof Coin) {
                //add coin stuff here
                setCoinCount(this.getCoinCount() + 1);
                destroyObject(currentObject);
            }
            else if (currentObject instanceof Mushroom) {
                this.mainHero.setCurrentHP(this.mainHero.getCurrentHP()+5);
                destroyObject(currentObject);
            }
            else if (currentObject instanceof WarpPipe && this.mainHero.collisionDown(currentObject)) {
                //set
                //canTransport=true;
            }
        }


    }

    public PriorityQueue<Object> ConvertListToQueue(List<Object> enemyList) {
        PriorityQueue<Object> myQueue=new PriorityQueue<Object>();
        for (int i=0; i< enemyList.size(); i++) {
            double priority=this.mainHero.getLayoutX()-enemyList.get(i).getLayoutX();
            //myQueue.;
        }
        return myQueue;
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
            this.mainHero.setVelocityY(this.mainHero.getVelocityY()-25);
        }
    }

    public void marioStep() {

        //only check for collisions if Mario is moving

        if ((this.mainHero.getVelocityX() != 0) || (this.mainHero.getVelocityY() != 0)) {
            Block currentBlock;
            this.mainHero.inAir = true;
            List<Block> tempBlockList = new ArrayList<Block>();
            for (int i = 0; i < this.blockList.size(); i++) {
                currentBlock = this.blockList.get(i);
                currentBlock.step();
                if (this.mainHero.collision(currentBlock)) {
                    tempBlockList.add(currentBlock);
                }
            }

            for (int i = 0; i < tempBlockList.size(); i++) {
                currentBlock = tempBlockList.get(i);


                if (this.mainHero.collisionDown(currentBlock)) {
                    if ((this.mainHero.getVelocityY() > 0)) {
                        this.mainHero.setVelocityY(0);
                    }
                    this.mainHero.inAir = false;
                    this.mainHero.setLayoutY(currentBlock.getLayoutY() - this.mainHero.getHeight());
                } else if (this.mainHero.collisionUp(currentBlock)) {

                    if (this.mainHero.getVelocityY() < 0) {
                        this.mainHero.setVelocityY(0);
                        handleBlockHit(currentBlock);
                    }
                    if (this.mainHero.getLayoutY() < currentBlock.getHeight() + currentBlock.getLayoutY()) {
                        this.mainHero.setLayoutY(currentBlock.getHeight() + currentBlock.getLayoutY());
                    }


                }
                else if (this.mainHero.collisionRight(currentBlock) && !this.mainHero.collisionDown(currentBlock)
                        && !this.mainHero.collisionUp(currentBlock)) {
                    if (this.currentAcceleration > 0) this.currentAcceleration = 0;
                    this.mainHero.setVelocityX(0);
                    if (this.mainHero.getLayoutX() + this.mainHero.getWidth() > currentBlock.getLayoutX())
                        this.mainHero.setLayoutX(currentBlock.getLayoutX() - this.mainHero.getWidth() - 1);
                }
                else if (this.mainHero.collisionLeft(currentBlock) && !this.mainHero.collisionDown(currentBlock)
                        && !this.mainHero.collisionUp(currentBlock)) {
                    if (this.currentAcceleration < 0) this.currentAcceleration = 0;
                    this.mainHero.setVelocityX(0);
                    if (this.mainHero.getLayoutX() < currentBlock.getLayoutX() + currentBlock.getWidth())
                        this.mainHero.setLayoutX(currentBlock.getLayoutX() + currentBlock.getWidth() + 1);
                }


            }

        }

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
        this.mainHero.setVelocityX(this.mainHero.getVelocityX()+this.currentAcceleration);
        this.mainHero.setVelocityX(this.mainHero.getVelocityX()*0.9);




    }



    public void handleMarioStomp(Enemy enemy) {
        enemy.setHP(enemy.getHP()-this.mainHero.getPower());
        if (enemy instanceof Goomba) {
            //do Goomba stuff
            Goomba goomba=(Goomba) enemy;
            if (goomba.getHP() <= 0)
            goomba.spriteDead();

        }
        else if (enemy instanceof Koopa) {
            //do Koopa stuff
            ((Koopa) enemy).spriteDead();
        }
        /*else.....
         */
        this.mainHero.setVelocityY(-15);

}
    public void handleBlockHit(Block block) {

        if (block instanceof CoinBlock) {
            //Coin block does its thing
            CoinBlock usedBlock = (CoinBlock) block;
            if (usedBlock.bUsed) return;
            else {
                usedBlock.giveCoin();
                Coin newCoin = new Coin(usedBlock.getLayoutX(), usedBlock.getLayoutY() - usedBlock.getHeight(), true);
                this.addObjectToList(newCoin);
            }
        }
        else if (block instanceof MushroomBlock) {
            MushroomBlock usedBlock = (MushroomBlock) block;
            if (usedBlock.bUsed) return;
            else {
                usedBlock.giveMushroom();
                Mushroom newMushroom = new Mushroom(usedBlock.getLayoutX(), usedBlock.getLayoutY() - usedBlock.getHeight());
                this.addObjectToList(newMushroom);
            }
        }
        else if (block instanceof EndBlock) {
            EndBlock usedBlock=(EndBlock) block;
            Timer timer;
            TimerTask timerTask = new TimerTask() {
                public void run() {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            courseCompleted=true;
                        }
                    });
                }
            };
            timer = new java.util.Timer();
            timer.schedule(timerTask, 20);

        }
        else {
            //no effect, Mario hit a block that doesn't do anything
        }
    }
    public void destroyObject(Object object) {
        if (object instanceof Block) {
            this.blockList.remove(object);
        }
        else if (object instanceof Enemy) {
            this.enemyList.remove(object);
        }
        else {
            this.objectList.remove(object);
        }
        object.getChildren().clear();
        object=null;
    }
    public void applyEnemyPhysics(Enemy enemy) {
        int iGuess = getEnemyList().size() / 2;
        boolean inair = true;
        Block groundSample = null;
        int iListSize = this.getBlockList().size();
        for (int i = 0; i < iListSize; i++) {

            Block BlockGuess = this.getBlockList().get(i);
            if (enemy.collision(BlockGuess)) {
                groundSample = BlockGuess;

                if (enemy.collisionDown(BlockGuess)) {
                    if ((enemy.getVelocityY() > 0)) {
                        enemy.setVelocityY(0);
                    }
                    enemy.setLayoutY(BlockGuess.getLayoutY() - enemy.getHeight());
                    inair = false;

                }
            }
        }
        //Check if found no collision
        if (inair) {
            enemy.setVelocityY(enemy.getVelocityY() + 1.1);
        }
        else {
            enemy.setVelocityY(0);

        }
    }

    private boolean BlockAtPosition(double x, double y) {
        for (int i=0; i< this.blockList.size(); i++) {

        }
        return false;
    }
    private void GeneralEnemyAI(Enemy eTarget) {
        double xPos=eTarget.getLayoutX();
        double marioPos=this.mainHero.getLayoutX();

        if ((Math.abs(xPos-marioPos) < 300)
            && (!this.mainHero.collision(eTarget))
            && (Math.abs(xPos-marioPos) > 64)
                ) {
            int nDirection=1;
            if ((marioPos-xPos < 0)) {
                nDirection=-1;
            }
            eTarget.setVelocityX(2*nDirection);
        }
    }
}
