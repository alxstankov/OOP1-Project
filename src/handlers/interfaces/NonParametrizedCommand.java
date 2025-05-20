package handlers.interfaces;

import handlers.models.InputHandler;

/**
 * The {@code NonParametrizedCommand} interface defines the method, that is used for carrying out different commands.
 */
public interface NonParametrizedCommand {
    /**
     * Executes the implemented command.
     */
    void executeCommand();
    /**
     * Sets the handler, that will be used in the by the command.
     *
     * @param handler Handler, that will be used in the by the command
     */
    void setHandler(InputHandler handler);
}
