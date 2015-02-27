package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("worldselect.fxml"));
        Parent root = (Parent)loader.load();
        WorldController controller = loader.getController();
        //root.setOnKeyPressed(controller);
        //root.setOnKeyReleased(controller);
        primaryStage.setTitle("Super Mario Bros");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
