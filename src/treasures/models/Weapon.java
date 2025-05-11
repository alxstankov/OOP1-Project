package treasures.models;

import handlers.models.RoundingCalculator;
import treasures.enums.TreasureType;

public class Weapon extends Treasure {
    public Weapon(double stat) {
        super(TreasureType.WEAPON, stat);
    }

    @Override
    public String toString() {
        return "Weapon:\n" +
                "- Attacking boost: "+ RoundingCalculator.roundDecimal(getStat()*100) +"%";
    }
}
