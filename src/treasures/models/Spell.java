package treasures.models;

import handlers.models.RoundingCalculator;
import treasures.enums.TreasureType;

/**
 * The {@code Spell} is a child of {@link treasures.models.Treasure} model. Extends to set stats for the Spell treasure type.
 */
public class Spell extends Treasure {
    /**
     * Constructs a {@code Spell} instance with the corresponding given stat.
     * @param stat Effective stat of the treasure
     */
    public Spell(double stat) {
        super(TreasureType.SPELL, stat);
    }

    @Override
    public String toString() {
        return "Spell:\n" +
                "- Spell boost: "+ RoundingCalculator.roundDecimal(getStat()*100) +"%";
    }
}
