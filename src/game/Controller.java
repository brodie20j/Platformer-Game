package game;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


import java.util.*;

import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Controller implements EventHandler<KeyEvent> {
    @FXML private Rectangle HealthFill;
    @FXML private Label HealthLabel;
    @FXML private Rectangle HealthOutline;
    @FXML private Label CoinLabel;
    @FXML private Label txtLabel;
    final private double FRAMES_PER_SECOND = 30.0;
    @FXML private Pane gameBoard;
    @FXML private Pane gameView;
    @FXML private Pane backgroundPane;

    private Engine mainEngine;
    private final double frameWidth=500;
    private final double frameHeight=500;
    private Hero playerView;
    private boolean paused;
    private Timer timer;
    private AudioClip clip;
    public LevelTable currentLevel;
    private double boardWidth;
    private double boardHeight;
    private Stage myStage;
    private final int viewWidth=500;
    private final int viewHeight=500;

    public Controller(LevelTable myLevel) {
        this.currentLevel=myLevel;
    }

    public void setStage(Stage stage) {
        this.myStage = stage;
    }

    public void initialize() {
        //this.currentLevel.parseLevelFile("myTest.ser")
        //this.gameBoard.getChildren().clear();
        this.currentLevel.setBackgroundString("star-bg.jpg");
        //this.currentLevel=new Level1_1();
        //this.gameView.setStyle("-fx-background-image:url('res/img/"+currentLevel.getBackgroundString()+"'); -fx-background-repeat: repeat-x; -fx-background-repeat:repeat-y;");
        this.mainEngine=new Engine(this.currentLevel);
        this.playerView = this.mainEngine.getMario();

        this.setUpAnimationTimer();
        this.setupGameScene();

        this.updateBlocks();

        this.txtLabel.setText(currentLevel.getLevelName());
    }

    /**
     * Sets the animation for the game
     */
    private void setUpAnimationTimer() {
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        updateAnimation();
                    }
                });
            }
        };

        final long startTimeInMilliseconds = 0;
        final long repetitionPeriodInMilliseconds = 100;
        long frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        this.timer = new java.util.Timer();
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    /**
     * Sets up game scene. Adds the spaceship and enemies to the gameboard
     */
    public void setupGameScene() {
        this.gameBoard.getChildren().clear();
        this.gameBoard.setLayoutX(0);
        this.gameBoard.setLayoutY(0);
        this.gameBoard.getChildren().add(this.playerView);
        List<Block> bList=this.mainEngine.getBlockList();
        double maxX=-10000000;
        double maxY=-10000000;
        for (int i=0; i<bList.size(); i++) {
            if (bList.get(i).getLayoutX() > maxX) {
                maxX=bList.get(i).getLayoutX();
            }
            if (bList.get(i).getLayoutY() > maxY) {
                maxY=bList.get(i).getLayoutY();
            }
        }
        this.boardHeight=maxY;
        this.boardWidth=maxX;
        this.backgroundPane.setMinHeight(this.boardHeight);
        this.backgroundPane.setMinWidth(this.boardWidth);
        this.backgroundPane.setStyle("-fx-background-image:url('res/img/"+currentLevel.getBackgroundString()+"'); -fx-background-repeat: repeat-x; -fx-background-repeat:repeat-y;");
        System.out.println(maxX);
        System.out.println(maxY);

    }

    /**
     * Handles keyboard events.
     * @param keyEvent
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        // Set the arrow keys
        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            if (code == KeyCode.LEFT) {
                this.mainEngine.setCurrentAcceleration(-4);

            } else if (code == KeyCode.RIGHT) {
                this.mainEngine.setCurrentAcceleration(4);
            }

            if ((this.playerView.getVelocityX()/this.mainEngine.getCurrentAcceleration()) != Math.abs(this.playerView.getVelocityX()/this.mainEngine.getCurrentAcceleration())) {
            }
            if (code == KeyCode.Z) {
                this.mainEngine.handleAButton();

            } else if (code == KeyCode.X) {
                //B Button stuff
                this.mainEngine.handleBButton();
            } else if (code == KeyCode.UP) {
                this.mainEngine.handleUp();
            }
        }
        if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
            if (code == KeyCode.LEFT || code == KeyCode.RIGHT)
            this.mainEngine.setCurrentAcceleration(0);
            if (code == KeyCode.Z)
            this.playerView.setVelocityY(this.playerView.getVelocityY()/2);
            if (code == KeyCode.X) {
                this.mainEngine.handleBButtonReleased();
            }
            if (code == KeyCode.UP) {
                this.mainEngine.handleUpReleased();
            }
        }

    }

    public void updateAnimation() {
        this.gameBoard.getChildren().clear();
        this.mainEngine.step();
        this.updateMario();
        this.drawGameObjects();
        this.updateHealthBar();
        this.CoinLabel.setText("Money: "+this.mainEngine.getCoinCount());
        this.updateView();
        if (this.mainEngine.getCompleted()) {
            //kill the timer
            this.timer.cancel();


        }


    }
    public void updateHealthBar() {
        double cHealthRatio=(float) this.mainEngine.getMario().getCurrentHP()/this.mainEngine.getMario().getMaxHP();
        this.HealthFill.setWidth(100.0*cHealthRatio);
        this.HealthOutline.setWidth(this.HealthFill.getWidth()/cHealthRatio);
        this.HealthFill.setHeight(10);
        this.HealthFill.setFill(Color.AQUA);
        this.HealthOutline.setStroke(Color.BLACK);
        this.HealthOutline.setFill(Color.BLACK);
        this.HealthOutline.setHeight(this.HealthFill.getHeight());
        this.HealthOutline.setOpacity(0.5);
        this.HealthLabel.setText("HP:");
    }
    //to do: the bulk of this work should be out here,not within the engine, since the android shit will all be in here too, it will make it easier for me to
    //slide my code over.
    public void drawGameObjects() {
        //List<Object> completeList=this.mainEngine.getCompleteList();
        /*Object currentObject;
        for (int i=0; i<completeList.size(); i++) {
            currentObject=completeList.get(i);
            if (
                    (!(this.gameBoard.getChildren().contains(currentObject)))
                    && (Math.abs(currentObject.getLayoutX()-this.playerView.getLayoutX()) < frameWidth)
                    && (Math.abs(currentObject.getLayoutY()-this.playerView.getLayoutY()) < frameHeight)
               )
                this.gameBoard.getChildren().add(currentObject);
        }*/
        this.updateBlocks();
        this.updateEnemies();
        this.updateObjects();

    }


    public void updateView() {

        this.gameView.setLayoutX((-1*this.playerView.getLayoutX())+200);
        if ((this.gameView.getLayoutX()*-1) < 0) this.gameView.setLayoutX(0);
        if (((this.gameView.getLayoutX()*-1)+500) > this.boardWidth) this.gameView.setLayoutX(this.boardWidth*-1+500);
        this.gameView.setLayoutY(-1*(this.playerView.getLayoutY()-this.viewHeight)-200);
        if (((-1*this.gameView.getLayoutY())+this.viewHeight) > (this.boardHeight)) {
            this.gameView.setLayoutY(-1*(this.boardHeight-this.viewHeight));
        }
        if (this.gameView.getLayoutY() > 0) {
            this.gameView.setLayoutY(0);
            System.out.println("daddy");
        }

    }

    //make sure there is a corresponding "ResumeEngine"
    public void PauseEngine() {
        this.timer.cancel();
    }
    public void ResumeEngine() {
        this.setUpAnimationTimer();
    }

    public void updateMario() {
        if (this.playerView != this.mainEngine.getMario()) {
            this.playerView =this.mainEngine.getMario();

        }
        this.gameBoard.getChildren().add(this.playerView);
        if ((this.playerView.getLayoutY()+96 > this.gameBoard.getHeight())
                || (this.playerView.getCurrentHP() <= 0)
                ) {
            System.out.println("Mario died");
            resetLevel();
        }
        if (((this.playerView.getLayoutX()-10) < 0) && (this.playerView.getVelocityX()<0)) {
            this.mainEngine.setCurrentAcceleration(0);
            this.playerView.setVelocityX(0);
        }
        if (((this.playerView.getLayoutX()+500) > this.boardWidth)  && (this.playerView.getVelocityX()>0)) {
            this.playerView.setVelocityX(0);
        }
    }

    public void updateBlocks() {
        List<Block> blockList = this.mainEngine.getBlockList();
        for (int i = 0; i < blockList.size(); i++) {

            if ((!this.gameBoard.getChildren().contains(blockList.get(i)))) {
                if ((this.playerView.getLayoutX() > (blockList.get(i).getLayoutX() - frameWidth))
                        && (this.playerView.getLayoutX() < (blockList.get(i).getLayoutX() + frameWidth))) {
                    this.gameBoard.getChildren().add(blockList.get(i));
                    //enemyList.get(i).setVelocityX(10.0);

                }
            }
        }
    }

    public void resetEngine() {
        this.mainEngine=null;
        this.initialize();
    }

    public void resetLevel() {
        this.timer.cancel();
        this.resetEngine();
    }


    public void updateEnemies() {
        List<Enemy> enemyList=this.mainEngine.getEnemyList();
        for (int i=0; i<enemyList.size(); i++) {
            if (!this.gameBoard.getChildren().contains(enemyList.get(i))) {
                if (this.playerView.getLayoutX() > (enemyList.get(i).getLayoutX() - frameWidth))
                    if (this.playerView.getLayoutX() < (enemyList.get(i).getLayoutX() + frameWidth)) {
                        this.gameBoard.getChildren().add(enemyList.get(i));
                }
            }
        }
    }
    public void updateObjects() {

        List<Object> objectList=this.mainEngine.getObjectList();

        for (int i=0; i<objectList.size(); i++) {
            if (!gameBoard.getChildren().contains(objectList.get(i))) {
                if ((this.playerView.getLayoutX() > (objectList.get(i).getLayoutX() - frameWidth)))
                    if ((this.playerView.getLayoutX() < (objectList.get(i).getLayoutX() + frameWidth))) {
                        gameBoard.getChildren().add(objectList.get(i));
                    }
            }
        }
    }

   /*   public void onPauseButton(ActionEvent actionEvent) {
        if (this.paused) {
            this.setUpAnimationTimer();
        } else {
            this.timer.cancel();
        }
        this.paused = !this.paused;
    }

*/
}