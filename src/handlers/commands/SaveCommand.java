package handlers.commands;

import game.models.GameProcessor;
import handlers.interfaces.FileHandler;
import handlers.interfaces.NonParametrizedCommand;
import handlers.models.InputHandler;

/**
 * The {@code SaveCommand} class is responsible for creating new game instances.
 * This class implements the {@link handlers.interfaces.NonParametrizedCommand} interface.
 */
public class SaveCommand implements NonParametrizedCommand {
    /**
     * Handler that looks after game runtime and contains the file handler, that is used for saving the loaded game instance.
     */
    private InputHandler handler;

    public void setHandler(InputHandler handler) {
        this.handler = handler;
    }

    /**
     * Implements {@link handlers.interfaces.NonParametrizedCommand#executeCommand()}.
     *  Calls the fileHandler's {@link handlers.interfaces.FileHandler#saveFile(GameProcessor)} method for saving loaded game instance in the currently loaded file.
     * If a file is not opened or a game is not loaded, the method shows an appropriate message on the system's default output stream.
     */
    @Override
    public void executeCommand() {
        FileHandler fileHandler = handler.getFileHandler();
        if (fileHandler.isFileLoaded())
        {
            if (handler.isGameActive())
            {
                fileHandler.saveFile(handler.getGame());
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
