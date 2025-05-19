package treasures.models;

import handlers.models.RoundingCalculator;
import treasures.enums.TreasureType;

/**
 * The {@code Armor} is a child of {@link treasures.models.Treasure} model. Extends to set stats for the Armour treasure type.
 */
public class Armor extends Treasure {
    /**
     * Constructs a {@code Armor} instance with the corresponding given stat.
     * @param stat Effective stat of the treasure
     */
    public Armor( double stat) {
        super(TreasureType.ARMOR, stat);
    }

    @Override
    public String toString() {
        return "Armor:\n" +
                "- Protection: "+ RoundingCalculator.roundDecimal(getStat()*100) +"%";
    }
}
