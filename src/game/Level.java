package game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * The {@code Level} class is responsible for containing current level parameters and calculating next level parameters.
 */
public class Level implements Serializable {
    /**
     * Holds level parameters for the current level.
     */
    private Map<String,Integer> currentLevel;

    /**
     * Holds parameters for the previous level.
     */
    private Map<String,Integer> previousLevel;

    /**
     * Constructs a {@code Level} with custom level parameters.
     *
     * @param level Numerical values of level difficulty
     * @param length Length of game board
     * @param width Width of game board
     * @param monsters Number of monsters on the game board
     * @param treasures Number of treasures on the game board
     */
    public Level(int level, int length, int width, int monsters, int treasures)
    {
        this.currentLevel = new HashMap<>()
        {{
            put("level",level);
            put("length",length);
            put("width",width);
            put("monsters",monsters);
            put("treasures",treasures);
        }};
    }

    /**
     * Constructs a {@code Level} with default level parameters.
     */
    public Level()
    {
        this.currentLevel = new HashMap<>()
        {{
            put("level",1);
            put("length",10);
            put("width",10);
            put("monsters",2);
            put("treasures",2);
        }};
    }

    /**
     * Gets the length of the game board of the current level.
     * @return Length of the game board
     */
    public int getLength() {
        return currentLevel.get("length");
    }

    /**
     * Gets the width of the game board of the current level.
     * @return Width of the game board
     */
    public int getWidth() {
        return currentLevel.get("width");
    }

    /**
     * Gets the number of monsters on the current level.
     * @return Number of monsters on the current level
     */
    public int getMonsters() {
        return currentLevel.get("monsters");
    }
    /**
     * Gets the number of treasures on the current level.
     * @return Number of treasures on the current level
     */
    public int getTreasures() {
        return currentLevel.get("treasures");
    }

    /**
     * Gets level difficulty of the current level.
     * @return Numerical value of the difficulty level of the current level
     */
    public int getLevel() {
        return currentLevel.get("level");
    }
    /**
     * Increases the current level parameters.
     */
    public void increaseLevel()
    {
        Map<String,Integer> levelBuffer = new HashMap<>(currentLevel);

        levelBuffer.computeIfPresent("level",(_k,v)->v+1);

        if (levelBuffer.get("level")==2)
        {
            levelBuffer.computeIfPresent("width",(_k,v)->v+5);
            levelBuffer.computeIfPresent("monsters",(_k,v)->v+1);
        }
        else
        {
            for (Map.Entry<String,Integer> entry: levelBuffer.entrySet())
            {
                if (!entry.getKey().equals("level"))
                {
                    levelBuffer.computeIfPresent(entry.getKey(),(_k,v)->v+previousLevel.get(entry.getKey()));
                }
            }
        }
        previousLevel = new HashMap<>(currentLevel);
        currentLevel = levelBuffer;
    }
    /**
     * Gets string representation of the current level.
     *
     * @return String representation of the current level
     */
    @Override
    public String toString() {
        return "Level: "+getLevel()+" Size: "+getWidth()+"x"+getLength()+" Monsters: "+getMonsters()+" Treasures: "+getTreasures();
    }
}
