package game.models;

import java.util.Random;

/**
 * The {@code RandomLevelGenerator} class is responsible for creating a random level, that will be used for custom games.
 */
public class RandomLevelGenerator {
    /**
     * The method uses {@link java.util.Random} instance to create a custom level object, that will be used for custom games.
     *
     * @return Custom level object
     */
    public static Level generateLevel()
    {
        Random randomNumberGenerator = new Random();
        return new Level(randomNumberGenerator.nextInt(5)+1,
                randomNumberGenerator.nextInt(50-10+1)+10,
                randomNumberGenerator.nextInt(50-10+1)+10,
                randomNumberGenerator.nextInt(15-2+1)+2,
                randomNumberGenerator.nextInt(15-2+1)+2);
    }
}
