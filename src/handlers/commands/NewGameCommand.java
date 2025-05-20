package handlers.commands;

import game.models.GameProcessor;
import game.models.RandomLevelGenerator;
import handlers.interfaces.ParametrizedCommand;
import handlers.models.InputHandler;

/**
 * The {@code NewGameCommand} class is responsible for creating new game instances.
 * This class implements the {@link handlers.interfaces.ParametrizedCommand} interface.
 */
public class NewGameCommand implements ParametrizedCommand {
    /**
     * Handler that looks after game runtime.
     *
     */
    private InputHandler handler;


    public void setHandler(InputHandler handler) {
        this.handler = handler;
    }

    /**
     * Implements {@link handlers.interfaces.ParametrizedCommand#executeCommand(String)}.
     * Creates a new game instance.
     * Depending on the mode, the method will create a normal or custom instance of the game.
     * The custom instance is only accessible when in administrative mode.
     * If a file is not opened, the method shows an appropriate message on the system's default output stream.
     *
     * @param parameter The game type, that decides what instance of the game needs to be created
     */
    @Override
    public void executeCommand(String parameter) {
        if (handler.getFileHandler().isFileLoaded())
        {
            // Check how to swap the games
            if (handler.isAdministrativeMode())
            {
                if (parameter.equals("custom"))
                {
                    handler.setGame(new GameProcessor(handler, RandomLevelGenerator.generateLevel()));
                    System.out.println("Started new game");
                }
                else if (parameter.equals("classic")) {
                    handler.setGame(new GameProcessor(handler));
                    System.out.println("Started new game");
                }
                else
                {
                    System.out.println("The given game mode is not recognized");
                }
            }
            else
            {
                if (parameter.equals("custom"))
                {
                    System.out.println("Administrative mode is not set!");
                }
                else if (parameter.equals("classic")) {
                    handler.setGame(new GameProcessor(handler));
                    System.out.println("Started new game");
                }
                else
                {
                    System.out.println("The given game mode is not recognized");
                }
            }

        }
        else
        {
            System.out.println("You need to open a file!");
        }
    }
}
