package LevelEdit;

/**
 * Created by jonathanbrodie on 1/4/15.
 */
import game.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import game.Object;

import java.awt.*;
import java.util.Hashtable;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;
import javafx.collections.FXCollections;
import java.util.Arrays;

public class WorldController {
    @FXML private Button instructionButton;
    @FXML private Button startButton;
    @FXML private Label mainMenuLabel;
    @FXML private TextField xInput;
    @FXML private ChoiceBox fileMenu;
    @FXML private ScrollPane previewPane;
    @FXML private ChoiceBox idInput;
    @FXML private ChoiceBox typeInput;
    @FXML private ChoiceBox levelList;
    @FXML private Button deleteButton;
    private Pane contentPane=new Pane();
    @FXML private Label xlevelLabel;
    @FXML private Label ylevelLabel;

    @FXML private TextField levelName;
    private LevelTable selectedLevel=null;
    @FXML private TextField yInput;
    @FXML private Button exitButton;

    private final List<String> typeList=Arrays.asList("Object","Enemy","Block");
    private final List<String> blockList=Arrays.asList("Block","Breakable Block");
    private final List<String> objectList=Arrays.asList("Chest", "Coin", "Health Potion");
    private final List<String> enemyList=Arrays.asList("Enemy","Koopa");
    private Hashtable<String, Class> idMap=new Hashtable<String, Class>();
    private PreviewEngine previewEngine;


    Stage prevStage;

    public WorldController() {}


    //Initialize method
    public void initialize() {
        //this.setPrevStage();

        this.idMap.put("Block", Block.class);
        this.idMap.put("Koopa", Koopa.class);
        this.idMap.put("Coin", Coin.class);
        this.idMap.put("Health Potion", HealthPotion.class);
        this.idMap.put("Breakable Block", BreakableBlock.class);
        this.idMap.put("Chest", Chest.class);
        this.previewPane.setPrefSize(300,300);

        mainMenuLabel.setTextFill(Color.BLACK);
        mainMenuLabel.setAlignment(Pos.CENTER);
        this.typeInput.setItems(FXCollections.observableArrayList(this.typeList));
        this.fileMenu.setItems(FXCollections.observableArrayList("File",new Separator(),"Start","Load Level","Save Level","Preview"));

        this.typeInput.getSelectionModel().selectedItemProperty().addListener(new
                                                                                      ChangeListener() {
                                                                                          @Override
                                                                                          public void changed(ObservableValue observable, java.lang.Object oldValue, java.lang.Object newValue) {
                                                                                                updateIDList();
                                                                                          }
                                                                                      });
        this.levelList.getSelectionModel().selectedItemProperty().addListener(new
                                                                                      ChangeListener() {
                                                                                          @Override
                                                                                          public void changed(ObservableValue observable, java.lang.Object oldValue, java.lang.Object newValue) {
                                                                                              updateLevelLabels();
                                                                                          }
                                                                                      });
        this.fileMenu.getSelectionModel().selectedItemProperty().addListener(new
                                                                                      ChangeListener() {
                                                                                          @Override
                                                                                          public void changed(ObservableValue observable, java.lang.Object oldValue, java.lang.Object newValue) {
                                                                                              fileMenu();
                                                                                          }
                                                                                      });

    }
    public void updatePane() {

    }
    public void fileMenu() {

        if (this.fileMenu.getValue().equals("Start")) {
                try {
                    System.out.println("yay");
                    this.goToGame();
                } catch (IOException e) {
                    System.out.println("oh fuck");
                }

        }
        else if (this.fileMenu.getValue().equals("Save Level")) {
            this.saveLevel(this.selectedLevel,this.levelName.getText());

        }
        else if (this.fileMenu.getValue().equals("Load Level")) {
            this.loadLevel();
        }
        else if (this.fileMenu.getValue().equals("Preview")) {
            this.previewLevel();
          }
        System.out.println("daddy");
        this.fileMenu.setValue("File");
    }
    public void setStage(Stage stage) {
        this.prevStage = stage;
    }

    /**
     * This method calls the main menu and closes the current window.
     * @param actionEvent
     * @throws IOException
     *
     *
     *
     */
    public void goToMenu(ActionEvent actionEvent) throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Main Menu");
        Pane myPane = null;
        myPane = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Scene scene = new Scene(myPane);
        stage.setScene(scene);
        prevStage.close();
        this.setStage(stage);
        stage.show();

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


    public void goToGame() throws IOException {
        if (this.selectedLevel==null) {
            if (!this.levelName.getText().equals("")) {
                this.selectedLevel=this.getLevel(this.levelName.getText());
            }
            else {
                return;
            }
        }
        Stage stage = this.prevStage;
        stage.close();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        System.out.println("k");

        //this.selectedLevel=this.getSelectedLevel("tester2.ser");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/sample.fxml"));

        final Controller controller = new Controller(this.selectedLevel);
        loader.setController(controller);
        Parent root = (Parent)loader.load();

        controller.setStage(stage);
        root.setOnKeyPressed(controller);
        root.setOnKeyReleased(controller);

        Scene gameScreen=new Scene(root, 500, 500);
        stage.setScene(gameScreen);
        //this.prevStage.close();
        stage.show();
    }

    public void exitButton(ActionEvent actionEvent) throws IOException {
        Platform.exit();
        System.exit(0);
    }
    public void loadLevel() {
        if (this.selectedLevel == null) {
            if (!this.levelName.getText().equals("")) {
                this.selectedLevel = this.getLevel(this.levelName.getText());
                this.updateLevelList();

            } else {
                return;
            }
        }
    }

    public void deleteObject(ActionEvent actionEvent) throws IOException {
        if (this.levelList.getValue().equals("")) {
            return;
        }
        String myKey=(String) this.levelList.getValue();
        this.selectedLevel.delete(myKey);
        this.updateLevelList();


    }

    private void saveLevel(LevelTable table, String levelName) {
        try
        {
            FileOutputStream fileOut =
                    new FileOutputStream(levelName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            if (table==null) {
                table=new LevelTable();
                this.selectedLevel=table;
            }
            out.writeObject(table);
            out.close();
            fileOut.close();
            System.out.printf("");
            this.updateLevelList();
        }catch(IOException i)
        {
            i.printStackTrace();
        }

    }
    private void updateLevelList() {
        if (this.selectedLevel==null) {
            return;
        }
            this.levelList.setItems(FXCollections.observableArrayList(this.selectedLevel.getKeyList()));
    }
    private LevelTable getLevel(String fileName) {
        LevelTable table;

            try {
                FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                table = (LevelTable) in.readObject();
                in.close();
                fileIn.close();
                return table;
            } catch (IOException i) {
                i.printStackTrace();
            } catch (ClassNotFoundException c) {
                System.out.println("Employee class not found");
                c.printStackTrace();
            }
        return null;
    }

    public void updateLevelLabels() {
        this.xlevelLabel.setText(Double.toString(this.selectedLevel.getX((String) this.levelList.getValue())));
        this.ylevelLabel.setText(Double.toString(this.selectedLevel.getY((String) this.levelList.getValue())));
    }
    public void addButton(ActionEvent actionEvent) throws IOException {
        Class id=idMap.get(this.idInput.getValue());
        Class type;


       if (this.typeInput.getValue().equals("Enemy")) {
            type=Enemy.class;
        }
        else if (this.typeInput.getValue().equals("Block")) {
            type=Block.class;
        }
        else {
            type=Object.class;
        }
        double x = Double.parseDouble(this.xInput.getText());
        double y = Double.parseDouble(this.yInput.getText());
        System.out.println(x);
        System.out.println(y);
        System.out.println(type);
        System.out.println(id);
        ObjectNode myNode=new ObjectNode(type, id, x,y);
        if (this.selectedLevel==null) {
            System.out.println("First create a level");
            return;
        }
        this.selectedLevel.store(myNode);
    }

    public void previewLevel() {
            this.previewEngine=new PreviewEngine(this.selectedLevel);


            this.contentPane.getChildren().clear();
        List<Object> objectList=this.previewEngine.getCurrentLevel().getObjectList();
        List<Block> blockList=this.previewEngine.getCurrentLevel().getBlockList();

        List<Enemy> enemyList=this.previewEngine.getCurrentLevel().getEnemyList();

        for (int i=0; i<enemyList.size(); i++) {
                        this.contentPane.getChildren().add(enemyList.get(i));
        }
        for (int i=0; i<objectList.size(); i++) {
            this.contentPane.getChildren().add(objectList.get(i));
        }
        for (int i=0; i<blockList.size(); i++) {
            this.contentPane.getChildren().add(blockList.get(i));
        }
        this.previewPane.setContent(this.contentPane);
    }
}
