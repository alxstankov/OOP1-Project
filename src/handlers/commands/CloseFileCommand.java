package handlers.commands;

import handlers.interfaces.FileHandler;
import handlers.interfaces.NonParametrizedCommand;
import handlers.models.InputHandler;

/**
 * The {@code CloseFileCommand} class is responsible for creating new game instances.
 * This class implements the {@link handlers.interfaces.NonParametrizedCommand} interface.
 */
public class CloseFileCommand implements NonParametrizedCommand {
    /**
     * Handler that looks after game runtime and contains the file handler, that is used for closing the file.
     */
    private InputHandler handler;

    public void setHandler(InputHandler handler) {
        this.handler = handler;
    }

    /**
     * Implements {@link handlers.interfaces.NonParametrizedCommand#executeCommand()}.
     * Calls the fileHandler's {@link FileHandler#closeFile()} method for closing the currently loaded file.
     * When the loaded file is successfully closed, the loaded game instance is flushed.
     * If the file handler reads a saved game, the game instance is loaded into the program.
     */
    @Override
    public void executeCommand() {
        FileHandler fileHandler = handler.getFileHandler();
        if (fileHandler.isFileLoaded())
        {
            fileHandler.closeFile();
            handler.setGame(null);
        }
        else
        {
            System.out.println("You need to open a file!");
        }
    }

}
