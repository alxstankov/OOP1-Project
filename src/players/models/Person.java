package players.models;

public class Person extends Player {
    public Person() {
        super(30, 20);
    }

    @Override
    public String toString() {
        return "Player type: Person \n"+
                super.toString();
    }
}
