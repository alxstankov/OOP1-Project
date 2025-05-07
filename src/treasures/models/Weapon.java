package treasures.models;

import treasures.enums.TreasureType;

public class Weapon extends Treasure {
    public Weapon(int stat) {
        super(TreasureType.WEAPON, stat);
    }

    @Override
    public String toString() {
        return "Weapon:\n" +
                "- Attacking boost: "+getStat()+"%";
    }
}
