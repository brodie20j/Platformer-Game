package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 3/1/15.
 */
public class Chest extends Object {
    public boolean bOpen=false;
    private ImageView imageView;
    public Chest() {
        Image image = new Image(getClass().getResourceAsStream("/res/img/chest-closed.png"));
        imageView = new ImageView();
        imageView.setImage(image);
        this.getChildren().add(imageView);
    }
    public Chest(double startX, double startY) {

        Image image = new Image(getClass().getResourceAsStream("/res/img/chest-closed.png"));
        imageView = new ImageView();
        imageView.setImage(image);
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.getChildren().add(imageView);
    }

    public void giveTreasure() {
        if (!bOpen) {
            //Update image
            Image image=new Image(getClass().getResourceAsStream("/res/img/chest-open.png"));
            imageView.setImage(image);
            this.getChildren().clear();
            this.getChildren().add(imageView);
            bOpen=true;
        }
    }
    @Override
    public void step() {
        super.step();
    }
}
