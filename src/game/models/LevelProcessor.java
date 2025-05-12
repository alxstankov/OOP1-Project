package game.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LevelProcessor implements Serializable {
    private Map<String,Integer> currentLevel = new HashMap<>()
    {{
        put("level",1);
        put("length",10);
        put("width",10);
        put("monsters",2);
        put("treasures",2);
    }};

    private Map<String,Integer> previousLevel;

    public int getLength() {
        return currentLevel.get("length");
    }

    public int getWidth() {
        return currentLevel.get("width");
    }

    public int getMonsters() {
        return currentLevel.get("monsters");
    }

    public int getTreasures() {
        return currentLevel.get("treasures");
    }

    public int getLevel() {
        return currentLevel.get("level");
    }

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

    @Override
    public String toString() {
        return "Level: "+getLevel()+" Size: "+getWidth()+"x"+getLength()+" Monsters: "+getMonsters()+" Treasures: "+getTreasures();
    }
}
