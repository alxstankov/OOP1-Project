package handlers.interfaces;

import handlers.models.InputHandler;

/**
 * The {@code ParametrizedCommand} interface defines the method, that is used for carrying out different commands.
 */
public interface ParametrizedCommand {
    /**
     * Executes the implemented command.
     * @param parameter Parameter, that is used for carrying out the command
     */
    void executeCommand(String parameter);
    /**
     * Sets the handler, that will be used in the by the command.
     *
     * @param handler Handler, that will be used in the by the command
     */
    void setHandler(InputHandler handler);
}
