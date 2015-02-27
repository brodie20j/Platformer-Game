package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;


import java.util.*;

import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

public class Controller implements EventHandler<KeyEvent> {
    @FXML private Label HealthLabel;
    @FXML private Label CoinLabel;
    @FXML private Label txtLabel;
    final private double FRAMES_PER_SECOND = 20.0;
    @FXML private Pane gameBoard;
    @FXML private AnchorPane gameView;
    private Engine mainEngine;
    private Hero playerView;
    private boolean paused;
    private Timer timer;
    private AudioClip clip;
    public Level currentLevel;
    public Controller() {
    }

    public void initialize() {
        currentLevel=new Level();
        this.currentLevel.parseLevelFile("1-1.txt");
        this.currentLevel.setBackgroundString("background-grassland.png");
        //this.currentLevel=new Level1_1();
        this.gameView.setStyle("-fx-background-color: red;");
        this.gameView.setStyle("-fx-background-image:url('res/img/"+currentLevel.getBackgroundString()+"'); -fx-background-repeat: repeat-x;");
        System.out.println(currentLevel.getBackgroundString());
        this.mainEngine=new Engine(currentLevel);
        this.playerView =this.mainEngine.getMario();
        this.setUpAnimationTimer();
        this.setupGameScene();
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
        this.updateBlocks();

    }


    public void keyPressed(KeyEvent e) {
        KeyCode code=e.getCode();
            if (code == KeyCode.LEFT) {
                this.mainEngine.setCurrentAcceleration(-4);

            } else if (code == KeyCode.RIGHT) {
                this.mainEngine.setCurrentAcceleration(4);
            }

            if ((this.playerView.getVelocityX()/this.mainEngine.getCurrentAcceleration()) != Math.abs(this.playerView.getVelocityX()/this.mainEngine.getCurrentAcceleration())) {
            }
            if (code == KeyCode.Z ) {
                this.playerView.setVelocityY(this.playerView.getVelocityY()-25);
                this.playSound("mario_jump.wav");

            } else if (code == KeyCode.X) {
                //B Button stuff
            }
    }
    public void keyReleased(KeyEvent keyEvent) {
        KeyCode code=keyEvent.getCode();
        if (code == KeyCode.LEFT || code == KeyCode.RIGHT)
            this.mainEngine.setCurrentAcceleration(0);
        if (code == KeyCode.Z)
            this.playerView.setVelocityY(this.playerView.getVelocityY()/2);
    }

    public void keyTyped(KeyEvent e) {

    }
    /**
     * Handles keyboard events.
     * @param keyEvent
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        // Set the arrow keys
        System.out.println("Handling key events");
        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            if (code == KeyCode.LEFT) {
                this.mainEngine.setCurrentAcceleration(-4);

            } else if (code == KeyCode.RIGHT) {
                this.mainEngine.setCurrentAcceleration(4);
            }

            if ((this.playerView.getVelocityX()/this.mainEngine.getCurrentAcceleration()) != Math.abs(this.playerView.getVelocityX()/this.mainEngine.getCurrentAcceleration())) {
            }
            if (code == KeyCode.Z && !this.playerView.inAir) {
                this.playerView.setVelocityY(this.playerView.getVelocityY()-20.5);
                this.playSound("mario_jump.wav");

            } else if (code == KeyCode.X) {
                //B Button stuff
            }
        }
        if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
            if (code == KeyCode.LEFT || code == KeyCode.RIGHT)
            this.mainEngine.setCurrentAcceleration(0);
            if (code == KeyCode.Z)
            this.playerView.setVelocityY(this.playerView.getVelocityY()/2);
        }

    }

    public void playSound(String sPathToFile) {
        AudioClip clip=new AudioClip(getClass().getResource("/res/sfx/" + sPathToFile).toString());
        clip.play();
    }

    public void updateAnimation() {
        this.gameBoard.getChildren().clear();
        this.mainEngine.step();
        this.updateMario();
        this.updateBlocks();
        this.updateEnemies();
        this.updateObjects();
        this.HealthLabel.setText("HP:"+this.mainEngine.getMario().getCurrentHP()+"/"+this.mainEngine.getMario().getMaxHP());
        this.CoinLabel.setText("Coins: "+this.mainEngine.getCoinCount());
        this.updateView();
        if (this.mainEngine.getCompleted()) {
            //kill the timer
            this.timer.cancel();


        }

    }

    public void DialogBox() {

    }

    public void updateView() {
        System.out.println("View is");
        System.out.print(this.gameView.getLayoutY());
        this.gameView.setLayoutY(-1*this.playerView.getLayoutY()+300);
        if (this.gameView.getLayoutY()!=0) this.gameView.setLayoutY(0);
        this.gameView.setLayoutX((-1*this.playerView.getLayoutX())+200);
        if (this.gameView.getLayoutX() > 0) this.gameView.setLayoutX(0);
        if (this.gameView.getLayoutX() > this.gameBoard.getWidth()) this.gameView.setLayoutX(this.gameBoard.getWidth());
    }

    //make sure there is a corresponding "ResumeEngine"
    public void PauseEngine() {
        this.timer.cancel();
    }
    public void ResumeEngine() {
        this.setUpAnimationTimer();
    }

    //

    public void updateMario() {
        // Check if spaceship is on the gameboard, If not then add it
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
    }

    public void updateBlocks() {
        List<Block> blockList = this.mainEngine.getBlockList();
        for (int i = 0; i < blockList.size(); i++) {

            if ((!this.gameBoard.getChildren().contains(blockList.get(i)))) {

                if ((this.playerView.getLayoutX() > (blockList.get(i).getLayoutX() - 700))
                        && (this.playerView.getLayoutX() < (blockList.get(i).getLayoutX() + 700))) {
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
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        resetEngine();
                    }
                });
            }
        };

        long frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        this.timer = new java.util.Timer();
        this.timer.schedule(timerTask, 50);

    }

    public void updateEnemies() {
        List<Enemy> enemyList=this.mainEngine.getEnemyList();
        for (int i=0; i<enemyList.size(); i++) {
            if (!gameBoard.getChildren().contains(enemyList.get(i))) {
                gameBoard.getChildren().add(enemyList.get(i));
                //enemyList.get(i).setVelocityX(10.0);
            }
        }
    }
    public void updateObjects() {

        List<Object> objectList=this.mainEngine.getObjectList();

        for (int i=0; i<objectList.size(); i++) {
            if (!gameBoard.getChildren().contains(objectList.get(i))) {
                gameBoard.getChildren().add(objectList.get(i));

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