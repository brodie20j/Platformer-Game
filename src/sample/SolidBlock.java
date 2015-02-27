package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 11/28/14.
 */
public class SolidBlock extends Block {
    public boolean bUsed;
    private ImageView imageView;
    public SolidBlock(double startX, double startY) {

        Image image = new Image(getClass().getResourceAsStream("/res/img/solidblock.png"));
        imageView = new ImageView();
        imageView.setImage(image);
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.getChildren().add(imageView);
        bUsed=false;
    }

    public void giveMushroom() {
        if (!bUsed) {
            //coin block gives up its coin
            Image image=new Image(getClass().getResourceAsStream("/res/img/solidblock.png"));
            imageView.setImage(image);
            this.getChildren().clear();
            this.getChildren().add(imageView);
            bUsed=true;
        }
    }

    public void step() {
        super.step();
    }

}
