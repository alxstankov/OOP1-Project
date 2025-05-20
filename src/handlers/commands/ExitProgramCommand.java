package handlers.commands;

import handlers.interfaces.NonParametrizedCommand;
import handlers.models.InputHandler;

/**
 * The {@code ExitProgramCommand} class is responsible for exiting the program.
 * This class implements the {@link handlers.interfaces.NonParametrizedCommand} interface.
 */
public class ExitProgramCommand implements NonParametrizedCommand {
    /**
     * Handler that looks after the program runtime.
     */
    private InputHandler handler;

    public void setHandler(InputHandler handler) {
        this.handler = handler;
    }

    /**
     * Implements {@link handlers.interfaces.NonParametrizedCommand#executeCommand()}.
     * Exits the program.
     * The loaded game instance is flushed and the main loop for checking for loaded game instances and incoming user input is stopped.
     * After stopping the loop, the program exits.
     */
    @Override
    public void executeCommand() {
        handler.setGame(null);
        handler.setProgramActive(false);
        System.out.println("Exiting the program...");
    }
}
