package LevelEdit;

/**
 * Created by jonathanbrodie on 1/4/15.
 */
import game.*;
import game.Object;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Hashtable;
import java.util.List;
import java.io.IOException;
import java.io.*;
import javafx.collections.FXCollections;
import javafx.stage.FileChooser;

import java.util.Arrays;

public class WorldController {
    @FXML private Label mainMenuLabel;
    @FXML private TextField xInput;
    @FXML private ScrollPane previewPane;
    @FXML private ChoiceBox idInput;
    @FXML private ChoiceBox typeInput;
    @FXML private ListView levelList;
    @FXML private Button deleteButton;
    @FXML private Label xlevelLabel;
    @FXML private Label ylevelLabel;
    @FXML private TextField levelName;
    @FXML private TextField yInput;
    @FXML private Button exitButton;
    @FXML private GridPane mainBoard;


    private Pane contentPane=new Pane();
    private LevelTable selectedLevel=null;

    private final List<String> typeList=Arrays.asList("Object","Enemy","Block");
    private final List<String> blockList=Arrays.asList("Block","Breakable Block");
    private final List<String> objectList=Arrays.asList("Chest", "Coin", "Health Potion","Door");
    private final List<String> enemyList=Arrays.asList("Enemy","Koopa");
    private Hashtable<String, Class> idMap=new Hashtable<String, Class>();
    private PreviewEngine previewEngine;


    Stage prevStage;

    public WorldController() {}


    //Initialize method
    public void initialize() {
        //this.setPrevStage();

        //Menu

        MenuBar menuBar = new MenuBar();

        // --- Menu File
        Menu menuFile = new Menu("File");

        MenuItem save = new MenuItem("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                saveLevel(selectedLevel);
            }
        });
        MenuItem load = new MenuItem("Load");

        load.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                loadLevel();
            }
        });

        menuFile.getItems().addAll(save,load);
        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");
        MenuItem add = new MenuItem("Add Object");

        add.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                try {
                    addObject();
                } catch (IOException e) {
                    System.out.println("Could not add Object!");
                }
            }
        });

        MenuItem delete = new MenuItem("Delete Object");

        delete.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                deleteObject();
            }
        });
        menuEdit.getItems().addAll(add,delete);

        // --- Menu View
        Menu menuView = new Menu("Game");

        MenuItem play = new MenuItem("Play");
        play.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                try {
                    goToGame();
                } catch (IOException e) {
                    System.out.println("Could not transition to the game!");
                }
            }
        });
        MenuItem preview = new MenuItem("Preview");
        preview.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                previewLevel();
            }
        });

        menuView.getItems().addAll(play,preview);

        //add everything
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);

        //add the menu to the scene
        this.mainBoard.getChildren().addAll(menuBar);

        //hardcode all items from the game here
        this.idMap.put("Block", Block.class);
        this.idMap.put("Koopa", Koopa.class);
        this.idMap.put("Coin", Coin.class);
        this.idMap.put("Health Potion", HealthPotion.class);
        this.idMap.put("Breakable Block", BreakableBlock.class);
        this.idMap.put("Chest", Chest.class);
        this.idMap.put("Door", Door.class);

        this.previewPane.setPrefSize(350,300);

        mainMenuLabel.setTextFill(Color.BLACK);
        mainMenuLabel.setAlignment(Pos.CENTER);

        this.levelList.getSelectionModel().selectedItemProperty().addListener(new
                                                                                      ChangeListener() {
                                                                                          @Override
                                                                                          public void changed(ObservableValue observable, java.lang.Object oldValue, java.lang.Object newValue) {
                                                                                              updateLevelLabels();
                                                                                          }
                                                                                      });


    }
    public void updatePane() {

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

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(this.prevStage);
        if (selectedFile != null) {
            this.selectedLevel=this.getLevel(selectedFile);
            this.updateLevelList();
        }
        else {
            System.out.println("FILE WAS NULL!!!!");
        }

    }

    public void deleteObject() {
        if (this.levelList.getSelectionModel().getSelectedItem().equals("")) {
            System.out.println("No Object selected");
            return;
        }
        String myKey=(String) this.levelList.getSelectionModel().getSelectedItem();
        this.selectedLevel.delete(myKey);
        this.updateLevelList();
    }

    private void saveLevel(LevelTable table) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        File selectedFile = fileChooser.showSaveDialog(this.prevStage);

        try
        {
            FileOutputStream fileOut = new FileOutputStream(selectedFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            if (table==null) {
                System.out.println("Table is null. Creating new table....");
                table=new LevelTable();
                this.selectedLevel=table;
                System.out.println("Table created");
            }
            table.setLevelName(this.levelName.getText());
            out.writeObject(table);
            out.close();
            fileOut.close();
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
    private LevelTable getLevel(File fileName) {
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

        this.xlevelLabel.setText(Double.toString(this.selectedLevel.getX((String) this.levelList.getSelectionModel().getSelectedItem())));
        this.ylevelLabel.setText(Double.toString(this.selectedLevel.getY((String) this.levelList.getSelectionModel().getSelectedItem())));
    }

    public void add(String idInputValue, String typeInputValue, String sx, String sy) {
        Class id=idMap.get(idInputValue);
        Class type;


        if (typeInputValue.equals("Enemy")) {
            type=Enemy.class;
        }
        else if (typeInputValue.equals("Block")) {
            type=Block.class;
        }
        else {
            type= Object.class;
        }
        double x = Double.parseDouble(sx);
        double y = Double.parseDouble(sy);
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
        this.updateLevelList();
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
    public void addObject() throws IOException {
        Stage newStage=new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LevelEdit/addobject.fxml"));
        System.out.println("Here?");

        Parent root = (Parent)loader.load();

        AddController controller = loader.getController();
        controller.setStage(newStage);
        controller.setParentController(this);
        newStage.setTitle("Add Object");
        newStage.setScene(new Scene(root, 250, 250));
        newStage.show();
    }
}
