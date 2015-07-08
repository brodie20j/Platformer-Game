package LevelEdit;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.lang.Object;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Constructor;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import game.*;

/**
 * Created by jonathanbrodie on 6/13/15.
 */
public class AddController {
    WorldController parentStage;
    @FXML
    private ChoiceBox idInput;
    @FXML private ChoiceBox typeInput;
    @FXML private GridPane mainBoard;
    @FXML private Label constructorLabel;
    @FXML private GridPane addBoard;


    private Stage myStage;
    private Class objectClass;
    private List<TextField> inputList;
    private List<Class> classList;



    //Hardcode the types of Objects here
    private final List<String> typeList= Arrays.asList("Object", "Enemy", "Block");


    //Hardcode the types of Blocks here
    private final List<String> blockList=Arrays.asList("Block","Breakable Block");
    private final List<String> objectList=Arrays.asList("Chest", "Coin", "Health Potion","Door");
    private final List<String> enemyList=Arrays.asList("Enemy","Koopa");

    public void initialize() {
        System.out.println("maks");
        this.typeInput.setItems(FXCollections.observableArrayList(this.typeList));

        this.typeInput.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
                                                                                          public void changed(ObservableValue observable, java.lang.Object oldValue, java.lang.Object newValue) {
                                                                                              updateIDList();
                                                                                          }
                                                                                      });
        this.idInput.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, java.lang.Object oldValue, java.lang.Object newValue) {
                updateFields();
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

    public void updateFields() {
        try {
            this.addBoard.getChildren().clear();
            System.out.println((String) this.idInput.getValue());
            Class selectedClass = Class.forName("game."+((String) this.idInput.getValue()));
            this.constructorLabel.setText(ObjectConstructor.getConstructorName(selectedClass));
            Class[] fieldList = ObjectConstructor.getPositionParameters(selectedClass);

            this.inputList=new ArrayList<TextField>();
            this.classList=new ArrayList<Class>();
            for (int i = 0; i < fieldList.length; i++) {
                System.out.println("here");
                Class current = fieldList[i];
                Label myLabel = new Label();
                myLabel.setText(current.getName());
                TextField myField = new TextField();
                this.inputList.add(myField);
                this.classList.add(current);
                this.addBoard.add(myLabel, 0, i + 2);
                this.addBoard.add(myField, 1, i + 2);
            }
            this.objectClass=selectedClass;
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void addButton() throws IOException {

        if (this.typeInput.getValue().equals("") || this.idInput.getValue().equals("")) {
            System.out.println("Please specify a type and id");
            return;
        }
        if (objectClass == null) {
            return;
        }



        for (int i=0; i< this.classList.size(); i++) {
            Class currentClass=this.classList.get(i);
            Constructor constructor = ObjectConstructor.getStringConstructor(currentClass);
            try {
                constructor.newInstance(currentClass);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            String currentField=this.inputList.get(i).getText();
            try {

                Object object=currentClass.newInstance()

            }
            catch (InstantiationException e) {

            }
            catch ()
            array.add()
        }



        //this.parentStage.add((String)this.idInput.getValue(),(String)this.typeInput.getValue(),this.xInput.getText(),this.yInput.getText());
        myStage.close();
    }
}
