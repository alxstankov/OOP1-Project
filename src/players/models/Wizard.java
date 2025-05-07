package players.models;

public class Wizard extends Player {
    public Wizard() {
        super(10, 40);
    }

    @Override
    public String toString() {
        return "Player Type: Wizard \n"+
                super.toString();
    }
}
