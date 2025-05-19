package treasures.models;

import handlers.models.RoundingCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The {@code TreasureListGenerator} class implements the static function for generating treasure, corresponding to level needs.
 */
public class TreasureListGenerator {
    /**
     * This method randomly generates a list of treasures, with random stats, depending on the level multiplier.
     *
     * @param numberOfItems Number of items that need to be generated
     * @param levelMultiplier Numerical representation of the  level multiplier, that is used for calculating treasure's stat
     * @return List of randomly generated treasures
     */
    public static List<Treasure> generateTreasureList(int numberOfItems, int levelMultiplier)
    {
        List<Treasure> treasureList = new ArrayList<>();
        Random randomNumberGenerator = new Random();
        double multipliedStats;
        for (int i=0; i<numberOfItems;i++)
        {
            if (levelMultiplier != 1) {
                multipliedStats = RoundingCalculator.roundDecimal(0.2+((randomNumberGenerator.nextInt(levelMultiplier-1+1)+1)*0.05));
            }
            else
            {
                multipliedStats = 0.2;
            }

            switch (randomNumberGenerator.nextInt(3-1+1)+1)
            {
                case 1:
                    treasureList.add(new Armor(multipliedStats));
                    break;
                case 2:
                    treasureList.add(new Weapon(multipliedStats));
                    break;
                case 3:
                    treasureList.add(new Spell(multipliedStats));
                    break;
            }
        }
        return treasureList;
    }
}
