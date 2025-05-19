package treasures.models;

import handlers.models.RoundingCalculator;
import treasures.enums.TreasureType;
/**
 * The {@code Weapon} is a child of {@link treasures.models.Treasure} model. Extends to set stats for the Weapon treasure type.
 */
public class Weapon extends Treasure {
    /**
     * Constructs a {@code Weapon} instance with the corresponding given stat.
     * @param stat Effective stat of the treasure
     */
    public Weapon(double stat) {
        super(TreasureType.WEAPON, stat);
    }

    @Override
    public String toString() {
        return "Weapon:\n" +
                "- Attacking boost: "+ RoundingCalculator.roundDecimal(getStat()*100) +"%";
    }
}
