package game.enemy;
import game.*;
import game.AI.*;

/**
 * Created by jonathanbrodie on 6/5/15.
 */
public class GoblinKing extends Enemy {
    private String startSprite="koopa_idle.png";

    public GoblinKing(double startX, double startY) {
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
    @Override
    public void handleAction(Action myAction) {
        if (myAction.getAction()==6) {
            System.out.println("Special boss behavior!");
        }
        else {
            super.handleAction(myAction);
        }
    }
}
