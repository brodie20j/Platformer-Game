package sample;

/**
 * Created by jonathanbrodie on 1/16/15.
 */
public class Globals {
    private int playerMaxHP;
    private int playerPower;

    public Globals() {
        this.playerMaxHP=5;
        this.playerPower=1;
    }

    public void setPlayerMaxHP(int nAmount) {
        this.playerMaxHP=nAmount;
    }
    public int getPlayerMaxHP() {
        return this.playerMaxHP;
    }
    public void setPlayerPower(int nAmount) {
        this.playerPower=nAmount;
    }
    public int getPlayerPower() {
        return this.playerPower;
    }

}
