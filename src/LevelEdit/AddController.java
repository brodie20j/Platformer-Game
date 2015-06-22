package LevelEdit;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by jonathanbrodie on 6/13/15.
 */
public class AddController {
    WorldController parentStage;
    @FXML
    private ChoiceBox idInput;
    @FXML private ChoiceBox typeInput;
    @FXML private TextField xInput;
    @FXML private TextField yInput;
    @FXML private GridPane mainBoard;

    private Stage myStage;



    //Hardcode the types of Objects here
    private final List<String> typeList= Arrays.asList("Object", "Enemy", "Block");


    //Hardcode the types of Blocks here
    private final List<String> blockList=Arrays.asList("Block","Breakable Block");
    private final List<String> objectList=Arrays.asList("Chest", "Coin", "Health Potion","Door");
    private final List<String> enemyList=Arrays.asList("Enemy","Koopa");

    public void initialize() {
        this.typeInput.setItems(FXCollections.observableArrayList(this.typeList));

        this.typeInput.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
                                                                                          public void changed(ObservableValue observable, java.lang.Object oldValue, java.lang.Object newValue) {
                                                                                              updateIDList();
                                                                                          }
                                                                                      });
    }

    public void setParentController(WorldController controller) {
        this.parentStage=controller;
    }
    public void setStage(Stage myStage) {
        this.myStage=myStage;
    }
    public void updateIDList() {
        if (this.typeInput.getValue().equals("Object")) {
            this.idInput.setItems(FXCollections.observableArrayList(this.objectList));
        }
        else if (this.typeInput.getValue().equals("Enemy")) {
            this.idInput.setItems(FXCollections.observableArrayList(this.enemyList));
        }
        else if (this.typeInput.getValue().equals("Block")) {
            this.idInput.setItems(FXCollections.observableArrayList(this.blockList));
        }
    }

    public void addButton() throws IOException{
        if (this.xInput.getText().isEmpty() || this.yInput.getText().isEmpty()) {
            System.out.println("Please specify both the x and y coordinates");
            return;
        }
        if (this.typeInput.getValue().equals("") || this.idInput.getValue().equals("")) {
            System.out.println("Please specify a type and id");
            return;
        }
        this.parentStage.add((String)this.idInput.getValue(),(String)this.typeInput.getValue(),this.xInput.getText(),this.yInput.getText());
        myStage.close();
    }
}
