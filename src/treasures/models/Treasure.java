package treasures.models;

import treasures.enums.TreasureType;

import java.io.Serializable;

public abstract class Treasure implements Serializable {
    private TreasureType type;
    private double stat;

    public Treasure(TreasureType type, double stat)
    {
        this.type = type;
        this.stat = stat;
    }

    public TreasureType getTreasureType() {
        return type;
    }

    public double getStat() {
        return stat;
    }

    public abstract String toString();
}
