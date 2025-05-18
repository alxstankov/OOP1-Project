package game.models;

import java.util.Random;

public class RandomLevelGenerator {
    public static LevelProcessor generateLevel()
    {
        Random randomNumberGenerator = new Random();
        return new LevelProcessor(randomNumberGenerator.nextInt(5)+1,
                randomNumberGenerator.nextInt(50-10+1)+10,
                randomNumberGenerator.nextInt(50-10+1)+10,
                randomNumberGenerator.nextInt(15-2+1)+2,
                randomNumberGenerator.nextInt(15-2+1)+2);
    }
}
