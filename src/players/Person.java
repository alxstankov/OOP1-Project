package players;

/**
 * The {@code Person} is a child of {@link Player} model. Extends to set stats for the Person type.
 */
public class Person extends Player {
    /**
     * Constructs a {@code Person} instance with the corresponding stats.
     */
    public Person() {
        super(30, 20);
    }

    @Override
    public String toString() {
        return "Player type: Person \n"+
                super.toString();
    }
}
