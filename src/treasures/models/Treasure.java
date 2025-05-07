package treasures.models;

import treasures.enums.TreasureType;

public abstract class Treasure {
    private TreasureType type;
    private int stat;

    public Treasure(TreasureType type, int stat)
    {
        this.type = type;
        this.stat = stat;
    }

    public TreasureType getTreasureType() {
        return type;
    }

    public int getStat() {
        return stat;
    }

    public abstract String toString();
}
