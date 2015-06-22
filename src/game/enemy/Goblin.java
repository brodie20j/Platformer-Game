package game.enemy;
import game.*;
/**
 * Created by jonathanbrodie on 6/4/15.
 */
public class Goblin extends Enemy {

    private String startSprite="koopa_idle.png";

    public Goblin(double startX, double startY) {
        this.setLayoutX(startX);
        this.setLayoutX(startY);
        this.setHP(3);
        this.setPower(1);
        this.setSprite(this.startSprite);

    }

    @Override
    public void step() {
        super.step();
        super.updateSprite();
    }
}