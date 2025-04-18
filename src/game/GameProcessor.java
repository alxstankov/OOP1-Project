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
        put("w",()->gameboard.movePlayer(playerLocation.getCordX()-1,playerLocation.getCordY()));
        put("a",()->gameboard.movePlayer(playerLocation.getCordX(),playerLocation.getCordY()-1));
        put("s",()->gameboard.movePlayer(playerLocation.getCordX()+1,playerLocation.getCordY()));
        put("d",()->gameboard.movePlayer(playerLocation.getCordX(),playerLocation.getCordY()+1));

    }};
    private InputHandler inputHandler = new ConsoleInputHandler();

    public GameProcessor(int length, int width)
    {
        this.gameboard = new Gameboard(generator.generateBoard(length,width),length,width);
        this.playerLocation = gameboard.getPlayerCords();
    }

    public void startGame() throws IOException {
        String gameInput;
        Runnable gameCommand;
        while (!gameboard.isMazeExit(playerLocation.getCordX(),playerLocation.getCordY()))
        {
            System.out.println(gameboard.toString());
            System.out.print(">> ");
            gameInput = inputHandler.readContent();
            gameCommand = gameControls.get(gameInput.toLowerCase());
            gameCommand.run();
            playerLocation = gameboard.getPlayerCords();
        }
        System.out.println(gameboard.toString());
    }
}
