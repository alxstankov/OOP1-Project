package game;

import events.interfaces.Event;
import events.models.PlayerLevelUpEvent;
import gameboard.Cell;
import gameboard.Gameboard;
import handlers.models.InputHandler;
import players.Player;
import players.Person;
import players.Warrior;
import players.Wizard;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
/**
 * The {@code GameProcessor} class is responsible for playing out the game logic.
 */
public class GameProcessor implements Serializable {
    /**
     * The game board of the current level.
     */
    private Gameboard gameboard;
    /**
     * The current player location of the player.
     */
    private Cell playerLocation;
    /**
     * Instance of the player.
     */
    private Player player;
    /**
     * Current game event.
    */
    private Event gameEvent;
    /**
     * {@link Level} instance that is tracking level progression.
     */
    private Level level;
    /**
     * Shows if the game is a custom or classic.
     */
    private boolean isCustom;
    /**
     * The input handler, that is going to be used for reading user input and checking program and game running status.
     */
    private transient InputHandler handler;
    /**
     * The number of levels that should be played
     */
    private int numberOfLevels = 5;
    /**
     * Serial version unique ID for comparing {@code GameProcessor} versions.
     */
    @Serial
    private static final long serialVersionUID = -5058632480278207267L;

    /**
     * Holds the commands that are specific for the game.
     * The key represent the name of the command.
     * The values are the commands for handling player movement, that will be executed, when being called.
     */
    private Map<String, Supplier<Event>> gameboardControls = new HashMap<>()
    {{
        put("w",(Supplier<Event> & Serializable)()->gameboard.movePlayer(playerLocation.getCordX(),playerLocation.getCordY()-1));
        put("a",(Supplier<Event> & Serializable)()->gameboard.movePlayer(playerLocation.getCordX()-1,playerLocation.getCordY()));
        put("s",(Supplier<Event> & Serializable)()->gameboard.movePlayer(playerLocation.getCordX(),playerLocation.getCordY()+1));
        put("d",(Supplier<Event> & Serializable)()->gameboard.movePlayer(playerLocation.getCordX()+1,playerLocation.getCordY()));
        put("jump",(Supplier<Event> & Serializable)()->jump());
    }};

    /**
     * Holds the commands that are specific for the game.
     * The key represent the name of the command.
     * The values are the miscellaneous commands, that will be executed, when being called.
     */
    private Map<String, Runnable> miscellaneousCommands = new HashMap<>()
    {{
        put("see-stats",(Runnable & Serializable)()->seePlayerStats());
        put("help",(Runnable & Serializable)()->help());
    }};

    /**
     * Holds the different player types.
     * The key represent the player type.
     * The values are the constructors that create the different player types.
     */
    private Map<String, Player> playerSelector = new HashMap<>()
    {{
        put("person",new Person());
        put("wizard",new Wizard());
        put("warrior",new Warrior());

    }};

    /**
     * Constructs a {@code GameProcessor} instance for a classic game.
     *
     * @param handler The input handler, that is going to be used for reading user input and checking program and game running status
     */
    public GameProcessor(InputHandler handler)
    {
        this.handler = handler;
        this.level = new Level();
        this.gameboard = new Gameboard(level);
        this.playerLocation = gameboard.getPlayerCords();
        this.isCustom = false;
    }

    /**
     * Constructs a {@code GameProcessor} instance for a custom game.
     *
     * @param handler The input handler, that is going to be used for reading user input and checking program and game running status
     * @param level Custom level instance
     */
    public GameProcessor(InputHandler handler, Level level)
    {
        this.handler = handler;
        this.level = level;
        this.gameboard = new Gameboard(this.level);
        this.playerLocation = gameboard.getPlayerCords();
        this.isCustom = true;
    }
    /**
     * This method is the main game loop for playing a level that detects game commands and carries them out.
     * The loop is active while 3 conditions are met: The player has not reached the end of the game board, the player is alive, the game is active.
     * If an event is encountered, the method starts the event.
     *
     * @throws Exception If an error occurs while carrying out the game level logic
     */
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
                    if ((isCustom || level.getLevel()==numberOfLevels) && event instanceof PlayerLevelUpEvent)
                    {
                        continue;
                    }
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
    /**
     * This method starts the game logic.
     * The main responsibilities of the method are to set the player type and carry the level progression.
     * If the game is custom, only one level will be played. A classic game will play up to 5 levels.
     *
     * @throws Exception If an error occurs while carrying out the game level logic
     */
    public void startGame() throws Exception {
        String[] gameInput;

        while (player == null && handler.isGameActive())
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

        if (handler.isGameActive())
        {
            help();
        }

        if (isCustom)
        {
            if (handler.isGameActive())
            {
                System.out.println(level.toString());
                playLevel();
                if (player.getCurrentHealth()<=0)
                {
                    System.out.println("Player died. Game over");
                    return;
                }
            }
        }
        else
        {
            while (level.getLevel()<=numberOfLevels && handler.isGameActive())
            {
                System.out.println(level.toString());
                playLevel();
                if (player.getCurrentHealth()<=0)
                {
                    System.out.println("Player died. Game over");
                    return;
                }
                if (level.getLevel()<numberOfLevels)
                {
                    generateNextLevel();
                }
                else
                {
                    break;
                }
            }
        }

        if (handler.isGameActive())
        {
            System.out.println("You win!");
        }
    }

    /**
     * Increases the level parameters. Creates a new game board and sets new player location.
     */
    private void generateNextLevel()
    {
        level.increaseLevel();
        gameboard = new Gameboard(level);
        playerLocation = gameboard.getPlayerCords();
    }

    /**
     * Shows current player stats on the system's default output stream.
     */
    private void seePlayerStats()
    {
        System.out.println(player.toString());
    }

    /**
     * Shows all supported game commands on the system's default output stream.
     */
    private void help()
    {
        String helpText = "General game commands:\n" +
                "- w/a/s/d - Moves the player up/left/down/right\n" +
                "- see-stats - Shows player stats\n";

        if (handler.isAdministrativeMode())
        {
            helpText += "- jump - Moves the player to the end of the maze\n";
        }

        System.out.println(helpText);
    }
    /**
     * Moves the player to the exit of the current game board.
     * The command is only accessible in administrative mode.
     *
     * @return Event, appropriate for the player position on the game board
     */
    private Event jump()
    {
        Event event = null;
        if (handler.isAdministrativeMode())
        {
            event =  gameboard.movePlayer(level.getWidth()-2,level.getLength()-1);
        }
        else
        {
            System.out.println("Administrative mode is not set!");
        }
         return event;
    }

    /**
     * Sets the handler, that will be used for reading user input.
     *
     * @param handler Input handler, used for user input and checking program and game running status
     */
    public void setHandler(InputHandler handler) {
        this.handler = handler;
    }


}
