package treasures.models;

import treasures.enums.TreasureType;

import java.io.Serializable;

/**
 * This {@code Treasure} class is responsible for implementing base functionality for all treasure types.
 */
public abstract class Treasure implements Serializable {
    /**
     * Treasure type of the treasure.
     */
    private TreasureType type;
    /**
     * Effective stat of the treasure.
     */
    private double stat;

    /**
     * Sets a base constructor for the treasure types.
     * The {@code Treasure} class is abstract, so it cannot create instances.
     */
    public Treasure(TreasureType type, double stat)
    {
        this.type = type;
        this.stat = stat;
    }

    /**
     * Gets the treasure type of the treasure.
     *
     * @return Treasure type of the treasure
     */
    public TreasureType getTreasureType() {
        return type;
    }

    /**
     * Gets the stat of the treasure.
     *
     * @return Stat of the treasure
     */
    public double getStat() {
        return stat;
    }

    /**
     * Gets the string representation of the treasure.
     *
     * @return String representation of the treasure
     */
    public abstract String toString();
}
