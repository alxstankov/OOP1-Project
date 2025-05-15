package game.models;

import events.interfaces.Event;
import gameboard.models.Cell;
import gameboard.models.Gameboard;
import handlers.models.InputHandler;
import players.models.Player;
import players.models.Person;
import players.models.Warrior;
import players.models.Wizard;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GameProcessor implements Serializable {
    private Gameboard gameboard;
    private Cell playerLocation;
    private Player player;
    private transient InputHandler handler;
    private static final long serialVersionUID = -5058632480278207267L;
    private Event gameEvent;
    private Map<String, Supplier<Event>> gameboardControls = new HashMap<>()
    {{
        put("w",(Supplier<Event> & Serializable)()->gameboard.movePlayer(playerLocation.getCordX(),playerLocation.getCordY()-1));
        put("a",(Supplier<Event> & Serializable)()->gameboard.movePlayer(playerLocation.getCordX()-1,playerLocation.getCordY()));
        put("s",(Supplier<Event> & Serializable)()->gameboard.movePlayer(playerLocation.getCordX(),playerLocation.getCordY()+1));
        put("d",(Supplier<Event> & Serializable)()->gameboard.movePlayer(playerLocation.getCordX()+1,playerLocation.getCordY()));
        put("jump",(Supplier<Event> & Serializable)()->gameboard.movePlayer(level.getWidth()-2,level.getLength()-1));
    }};

    private Map<String, Runnable> miscellaneousCommands = new HashMap<>()
    {{
        put("see-stats",(Runnable & Serializable)()->seePlayerStats());
        put("help",(Runnable & Serializable)()->help());
    }};

    private LevelProcessor level = new LevelProcessor();
    private Map<String, Player> playerSelector = new HashMap<>()
    {{
        put("person",new Person());
        put("wizard",new Wizard());
        put("warrior",new Warrior());

    }};

    public GameProcessor(InputHandler handler)
    {
        this.handler = handler;
        this.gameboard = new Gameboard(level);
        this.playerLocation = gameboard.getPlayerCords();
    }

    private void playLevel() throws Exception
    {
        String[] gameInput;
        Event event;
        while (!gameboard.isMazeExit(playerLocation.getCordX(),playerLocation.getCordY())&&player.getCurrentHealth()>0&&handler.isGameActive())
        {
            if (gameEvent !=null)
            {
                gameEvent.setHandler(handler);
                gameEvent.startEvent(player);
                gameEvent = null;
                continue;
            }

            System.out.println(gameboard.toString());
            gameInput = handler.handleCommand();

            if (gameInput == null)
            {
                continue;
            }

            if (gameInput.length != 1)
            {
                System.out.println("No such input");
                continue;
            }

            Supplier<Event> gameCommand = gameboardControls.get(gameInput[0]);
            if(gameCommand!=null)
            {
                event = gameCommand.get();

                if (event != null) {
                    gameEvent = event;
                }
                continue;
            }

            Runnable miscellaneousCommand = miscellaneousCommands.get(gameInput[0]);
            if(miscellaneousCommand==null)
            {
                System.out.println("No such input");
            }
            else
            {
                miscellaneousCommand.run();
            }
        }
    }

    public void startGame() throws Exception {
        String[] gameInput;

        while (player == null)
        {
            System.out.println("Please, choose a player type [ person / wizard / warrior ]:");
            gameInput = handler.handleCommand();

            if (gameInput == null)
            {
                continue;
            }

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

        help();
        while (level.getLevel()<=5 && handler.isGameActive())
        {
            System.out.println(level.toString());
            playLevel();
            if (player.getCurrentHealth()<=0)
            {
                System.out.println("Player died. Game over");
                return;
            }
            generateNextLevel();
        }

        if (handler.isGameActive())
        {
            System.out.println("You win!");
        }
    }

    private void generateNextLevel()
    {
        level.increaseLevel();
        gameboard = new Gameboard(level);
        playerLocation = gameboard.getPlayerCords();
    }

    private void seePlayerStats()
    {
        System.out.println(player.toString());
    }

    private void help()
    {
        String helpText = "General game commands:\n" +
                "- w/a/s/d - Moves the player up/left/down/right\n" +
                "- see-stats - Shows player stats\n";

        System.out.println(helpText);
    }

    public void setHandler(InputHandler handler) {
        this.handler = handler;
    }


}
