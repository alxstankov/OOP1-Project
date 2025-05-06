package players.models;

public class Warrior extends BasePlayer {
    public Warrior() {
        super(40,10);
    }

    @Override
    public String toString() {
        return "Player type: Warrior \n"+
                super.toString();
    }
}
