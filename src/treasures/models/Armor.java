package treasures.models;

import handlers.models.RoundingCalculator;
import treasures.enums.TreasureType;

public class Armor extends Treasure {
    public Armor( double stat) {
        super(TreasureType.ARMOR, stat);
    }

    @Override
    public String toString() {
        return "Armor:\n" +
                "- Protection: "+ RoundingCalculator.roundDecimal(getStat()*100) +"%";
    }
}
