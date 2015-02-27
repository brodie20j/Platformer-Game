package sample;

/**
 * Created by jonathanbrodie on 1/4/15.
 */
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.io.IOException;


public class WorldController {
    @FXML private Button instructionButton;
    @FXML private Button startButton;
    @FXML private Label mainMenuLabel;
    @FXML private Label instructionLabel;
    @FXML private Button exitButton;
    Stage prevStage;

    public WorldController() {}


    //Initialize method
    public void initialize() {
        //this.setPrevStage();
        mainMenuLabel.setTextFill(Color.WHITE);
        mainMenuLabel.setAlignment(Pos.CENTER);

    }
    public void setPrevStage(Stage stage) {
        this.prevStage = stage;
    }

    /**
     * This method calls the main menu and closes the current window.
     * @param actionEvent
     * @throws IOException
     */
    public void goToMenu(ActionEvent actionEvent) throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Main Menu");
        Pane myPane = null;
        myPane = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Scene scene = new Scene(myPane);
        stage.setScene(scene);
        prevStage.close();
        this.setPrevStage(stage);
        stage.show();

    }

    /**
     * This method calls the game and closes the current window.
     * @param actionEvent
     * @throws IOException
     */
    public void goToGame(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Galaga");
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent)loader.load();
        final Controller controller = loader.getController();
        controller.currentLevel=new Level();
        //controller.setPrevStage(stage);
        root.setOnKeyPressed(controller);
        root.setOnKeyReleased(controller);
        Scene gameScreen=new Scene(root, 500, 500);
        stage.setScene(gameScreen);
        //prevStage.close();
        stage.show();

    }
    public void exitButton(ActionEvent actionEvent) throws IOException {
        Platform.exit();
        System.exit(0);
    }
    /**
     * gives the instruction label text
     * @param actionEvent
     * @throws IOException
     */
    public void goToInstructions(ActionEvent actionEvent) throws IOException{
        instructionLabel.setTextFill(Color.WHITE);
        instructionLabel.setText("Use the left and right arrow keys to move. \nPress the shift key to fire a laser!");

    }
}
