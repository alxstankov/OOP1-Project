package treasures.models;

import treasures.enums.TreasureType;

public abstract class Treasure {
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
