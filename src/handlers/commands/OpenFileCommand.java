package handlers.commands;

import game.models.GameProcessor;
import handlers.interfaces.ParametrizedCommand;
import handlers.models.InputHandler;

import java.io.File;

/**
 * The {@code OpenFileCommand} class is responsible for handling the reading a targeted file and loading the stored game save.
 * This class implements the {@link handlers.interfaces.ParametrizedCommand} interface.
 */
public class OpenFileCommand implements ParametrizedCommand {
    /**
     * Handler that looks after game runtime and contains the file handler, that is used for reading the file.
     */
    private InputHandler handler;

    public void setHandler(InputHandler handler) {
        this.handler = handler;
    }


    /**
     * Implements {@link handlers.interfaces.ParametrizedCommand#executeCommand(String)}.
     * Calls the fileHandler's {@link handlers.interfaces.FileHandler#readFile(File)} method for loading the targeted file.
     * If the file handler reads a saved game, the game instance is loaded into the program.
     * @param parameter File location of the file, that will be read from
     */
    @Override
    public void executeCommand(String parameter) {
        File file = new File(parameter);
        GameProcessor game = handler.getFileHandler().readFile(file);

        if (game != null)
        {
            game.setHandler(handler);
            handler.setGame(game);
        }
    }
}
