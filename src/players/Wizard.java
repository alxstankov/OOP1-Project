package players;

/**
 * The {@code Wizard} is a child of {@link Player} model. Extends to set stats for the Person type.
 */
public class Wizard extends Player {
    /**
     * Constructs a {@code Wizard} instance with the corresponding stats.
     */
    public Wizard() {
        super(10, 40);
    }

    @Override
    public String toString() {
        return "Player Type: Wizard \n"+
                super.toString();
    }
}
