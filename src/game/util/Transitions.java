package game.util;

/**
 * Created by jonathanbrodie on 6/20/15.
 */
import game.*;
import javafx.scene.layout.*;
import javafx.animation.FadeTransition;
import javafx.util.*;
public class Transitions {

    public static void DoorTransition(Door myDoor, Hero hero) {
        int heroLayer=hero.getLayer();
        hero.setLayoutX(myDoor.getTransportX());
        hero.setLayoutY(myDoor.getTransportY());

    }

    public static void FadeOut(Pane myPane, float fadetime) {
        FadeTransition ft = new FadeTransition(Duration.millis(fadetime), myPane);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
    }
    public static void FadeIn(Pane myPane, float fadetime) {
        FadeTransition ft = new FadeTransition(Duration.millis(fadetime), myPane);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }
}
