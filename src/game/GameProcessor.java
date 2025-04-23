package game;

import gameboard.Cell;
import gameboard.Gameboard;
import gameboard.GameboardGenerator;
import handlers.models.ConsoleInputHandler;
import handlers.interfaces.InputHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameProcessor {
    private Gameboard gameboard;
    private Cell playerLocation;
    private GameboardGenerator generator = new GameboardGenerator();
    private Map<String,Runnable> gameControls = new HashMap<>()
    {{
        put("w",()->gameboard.movePlayer(playerLocation.getCordX(),playerLocation.getCordY()-1));
        put("a",()->gameboard.movePlayer(playerLocation.getCordX()-1,playerLocation.getCordY()));
        put("s",()->gameboard.movePlayer(playerLocation.getCordX(),playerLocation.getCordY()+1));
        put("d",()->gameboard.movePlayer(playerLocation.getCordX()+1,playerLocation.getCordY()));
        put("jump",()->gameboard.movePlayer(level.getWidth()-2,level.getLength()-1));
    }};
    private InputHandler inputHandler = new ConsoleInputHandler();
    private LevelProcessor level = new LevelProcessor();

    public GameProcessor()
    {
        this.gameboard = new Gameboard(generator.generateBoard(level.getLength(), level.getWidth(), level.getMonsters(), level.getTreasures()), level.getLength(), level.getWidth());
        this.playerLocation = gameboard.getPlayerCords();
    }

    private void playLevel() throws IOException
    {
        String gameInput;
        Runnable gameCommand;
        while (!gameboard.isMazeExit(playerLocation.getCordX(),playerLocation.getCordY()))
        {
            System.out.println(gameboard.toString());
            System.out.print(">> ");
            gameInput = inputHandler.readContent();
            gameCommand = gameControls.get(gameInput.toLowerCase());
            if(gameCommand==null)
            {
                System.out.println("No such input");
                continue;
            }
            gameCommand.run();
        }
    }

    public void startGame() throws IOException {

        while (true)
        {
            System.out.println("Level: "+level.getLevel()+" Size: "+level.getWidth()+"x"+level.getLength()+" Monsters: "+level.getMonsters()+" Treasures: "+level.getTreasures());
            playLevel();
            generateNextLevel();
        }
    }

    private void generateNextLevel()
    {
        level.increaseLevel();
        gameboard = new Gameboard(generator.generateBoard(level.getLength(), level.getWidth(), level.getMonsters(), level.getTreasures()), level.getLength(), level.getWidth());
        playerLocation = gameboard.getPlayerCords();
    }
}
