package players;

/**
 * The {@code Warrior} is a child of {@link Player} model. Extends to set stats for the Person type.
 */
public class Warrior extends Player {
    /**
     * Constructs a {@code Warrior} instance with the corresponding stats.
     */
    public Warrior() {
        super(40,10);
    }

    @Override
    public String toString() {
        return "Player type: Warrior \n"+
                super.toString();
    }
}
