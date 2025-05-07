package game.models;

import events.interfaces.Event;
import gameboard.models.Cell;
import gameboard.models.Gameboard;
import gameboard.models.GameboardGenerator;
import handlers.models.ConsoleInputHandler;
import players.models.Player;
import players.models.Person;
import players.models.Warrior;
import players.models.Wizard;
import treasures.models.TreasureListGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GameProcessor {
    private Gameboard gameboard;
    private Cell playerLocation;
    private GameboardGenerator generator = new GameboardGenerator();
    private Player player;
    private Map<String, Supplier<Event>> gameboardControls = new HashMap<>()
    {{
        put("w",()->gameboard.movePlayer(playerLocation.getCordX(),playerLocation.getCordY()-1));
        put("a",()->gameboard.movePlayer(playerLocation.getCordX()-1,playerLocation.getCordY()));
        put("s",()->gameboard.movePlayer(playerLocation.getCordX(),playerLocation.getCordY()+1));
        put("d",()->gameboard.movePlayer(playerLocation.getCordX()+1,playerLocation.getCordY()));
        put("jump",()->gameboard.movePlayer(level.getWidth()-2,level.getLength()-1));
    }};
    private LevelProcessor level = new LevelProcessor();
    private Map<String, Player> playerSelector = new HashMap<>()
    {{
        put("person",new Person());
        put("wizard",new Wizard());
        put("warrior",new Warrior());

    }};

    public GameProcessor()
    {
        this.gameboard = new Gameboard(generator.generateBoard(level.getLength(), level.getWidth(), level.getMonsters(), level.getTreasures()), level.getLength(), level.getWidth(), TreasureListGenerator.generateTreasureList(level.getTreasures(), level.getLevel()));
        this.playerLocation = gameboard.getPlayerCords();
    }

    private void playLevel() throws Exception
    {
        String[] gameInput;
        Supplier<Event> gameCommand;
        Event event;
        while (!gameboard.isMazeExit(playerLocation.getCordX(),playerLocation.getCordY()))
        {
            System.out.println(gameboard.toString());
            System.out.print(">> ");
            gameInput = ConsoleInputHandler.readContent();
            if (gameInput.length != 1)
            {
                System.out.println("No such input");
                continue;
            }

            gameCommand = gameboardControls.get(gameInput[0]);
            if(gameCommand==null)
            {
                System.out.println("No such input");
                continue;
            }
            event = gameCommand.get();

            if (event != null)
            {
                event.startEvent(player);
            }
        }
    }

    public void startGame() throws Exception {
        String[] gameInput;

        while (player == null)
        {
            System.out.println("Please, choose a player type [ person / wizard / warrior ]:");
            System.out.print(">> ");
            gameInput = ConsoleInputHandler.readContent();

            if (gameInput.length != 1)
            {
                System.out.println("No such input");
                continue;
            }

            this.player = playerSelector.get(gameInput[0]);

            if(player==null)
            {
                System.out.println("No such player type");
            }
        }

        while (level.getLevel()<=5)
        {
            System.out.println("Level: "+level.getLevel()+" Size: "+level.getWidth()+"x"+level.getLength()+" Monsters: "+level.getMonsters()+" Treasures: "+level.getTreasures());
            playLevel();
            generateNextLevel();
        }
    }

    private void generateNextLevel()
    {
        level.increaseLevel();
        gameboard = new Gameboard(generator.generateBoard(level.getLength(), level.getWidth(), level.getMonsters(), level.getTreasures()), level.getLength(), level.getWidth(),TreasureListGenerator.generateTreasureList(level.getTreasures(), level.getLevel()));
        playerLocation = gameboard.getPlayerCords();
    }
}
