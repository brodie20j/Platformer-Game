package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 3/1/15.
 */
public class Chest extends Object {
    public boolean bOpen=false;
    public Chest() {

        this.setSprite("chest-closed.png");
    }
    public Chest(double startX, double startY) {

        this.setSprite("chest-closed.png");
        this.setLayoutX(startX);
        this.setLayoutY(startY);
    }

    public void giveTreasure() {
        if (!bOpen) {
            //Update image
            this.setSprite("chest-open.png");
            bOpen=true;
        }
    }
    @Override
    public void step() {
        super.step();
    }
}
