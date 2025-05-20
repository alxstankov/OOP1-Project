package handlers.commands;

import game.models.GameProcessor;
import handlers.interfaces.FileHandler;
import handlers.interfaces.ParametrizedCommand;
import handlers.models.InputHandler;

import java.io.File;

/**
 * The {@code SaveAsCommand} class is responsible for handling the saving the game instance to a targeted file.
 * This class implements the {@link handlers.interfaces.ParametrizedCommand} interface.
 */
public class SaveAsCommand implements ParametrizedCommand {
    /**
     * Handler that looks after game runtime and contains the file handler, that is used for saving the game to the targeted file.
     *
     */
    private InputHandler handler;

    public void setHandler(InputHandler handler) {
        this.handler = handler;
    }
    /**
     * Implements {@link handlers.interfaces.ParametrizedCommand#executeCommand(String)}.
     * Calls the fileHandler's {@link handlers.interfaces.FileHandler#saveAsFile(File, GameProcessor)} method for saving loaded game instance in a targeted file.
     * If a file is not opened or a game is not loaded, the method shows an appropriate message on the system's default output stream.
     *
     * @param parameter File location of the file, that will be used for saving a loaded game instance
     */
    @Override
    public void executeCommand(String parameter) {

        FileHandler fileHandler  = handler.getFileHandler();
        if (fileHandler.isFileLoaded())
        {
            if (handler.isGameActive())
            {
                File file = new File(parameter);
                fileHandler.saveAsFile(file,handler.getGame());
            }
            else
            {
                System.out.println("Game is not loaded!");
            }
        }
        else
        {
            System.out.println("You need to open a file!");
        }

    }
}
