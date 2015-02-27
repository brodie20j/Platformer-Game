package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jonathanbrodie on 1/3/15.
 */
public class EndBlock extends Block{
    public boolean bUsed;
    private ImageView imageView;
    private int AnimationCounter=0;
    private int AnimationEnd=7;
    public EndBlock(double startX, double startY) {

        Image image = new Image(getClass().getResourceAsStream("/res/img/unknownblock.png"));
        imageView = new ImageView();
        imageView.setImage(image);
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.getChildren().add(imageView);
    }

    public void step() {
        super.step();
        updateAnimationCounter();
    }
    public void updateAnimationCounter() {
        this.getChildren().clear();
        String sFileName="/res/img/endblock-"+AnimationCounter+".png";
        Image image = new Image(getClass().getResourceAsStream(sFileName));
        imageView = new ImageView();
        imageView.setImage(image);
        this.getChildren().add(imageView);
        AnimationCounter+=1;
        if (AnimationCounter > AnimationEnd) AnimationCounter=0;
    }
}
