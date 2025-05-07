package treasures.models;

import treasures.enums.TreasureType;

public class Armor extends Treasure {
    public Armor( int stat) {
        super(TreasureType.ARMOR, stat);
    }

    @Override
    public String toString() {
        return "Armor:\n" +
                "- Protection: "+getStat()+"%";
    }
}
