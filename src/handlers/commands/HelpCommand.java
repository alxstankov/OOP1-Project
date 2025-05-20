package handlers.commands;

import handlers.interfaces.NonParametrizedCommand;
import handlers.models.InputHandler;

/**
 * The {@code HelpCommand} class is responsible for showing all supported general commands on the system's default output stream.
 * This class implements the {@link handlers.interfaces.NonParametrizedCommand} interface.
 */
public class HelpCommand implements NonParametrizedCommand {
    /**
     * Handler that looks after the program runtime.
     */
    private InputHandler handler;

    public void setHandler(InputHandler handler) {
        this.handler = handler;
    }

    /**
     * Implements {@link handlers.interfaces.NonParametrizedCommand#executeCommand()}.
     * Shows all supported general commands on the system's default output stream.
     */
    @Override
    public void executeCommand() {
        String administrativeGameMode = handler.isAdministrativeMode() ? "/ custom " : "";
        String helpText = "The following commands are supported:\n" +
                "- open <file> - Opens <file>\n" +
                "- close - Closes currently opened file\n" +
                "- save - Saves the currently opened file\n" +
                "- save-as <file> - Saves the currently opened file in <file>\n" +
                "- new-game [ classic "+administrativeGameMode+"] - Starts a new game\n" +
                "- set-mode - Switches between normal and administrative mode\n" +
                "- help - Prints this information\n" +
                "- exit - Exits the program\n";
        System.out.println(helpText);
    }
}
