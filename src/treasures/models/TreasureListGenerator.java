package treasures.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TreasureListGenerator {
    public static List<Treasure> generateTreasureList(int numberOfItems, int levelMultiplier)
    {
        List<Treasure> treasureList = new ArrayList<>();
        Random randomNumberGenerator = new Random();
        int multipliedStats;
        for (int i=0; i<numberOfItems;i++)
        {
            if (levelMultiplier != 1) {
                multipliedStats = 20+((randomNumberGenerator.nextInt(levelMultiplier-1+1)+1)*5);
            }
            else
            {
                multipliedStats = 20;
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
