package handlers.commands;

import handlers.interfaces.NonParametrizedCommand;
import handlers.models.InputHandler;

/**
 * The {@code SetAdministrativeModeCommand} class is responsible for setting the administrative mode of the program.
 * This class implements the {@link handlers.interfaces.NonParametrizedCommand} interface.
 */
public class SetAdministrativeModeCommand implements NonParametrizedCommand {
    /**
     * Handler that looks after the program runtime.
     */
    private InputHandler handler;

    public void setHandler(InputHandler handler) {
        this.handler = handler;
    }

    /**
     * Implements {@link handlers.interfaces.NonParametrizedCommand#executeCommand()}.
     * Switches between normal and administrative mode of th program
     * If a file is not opened, the method shows an appropriate message on the system's default output stream.
     */
    @Override
    public void executeCommand() {
        if (handler.getFileHandler().isFileLoaded())
        {
            handler.setAdministrativeMode(!handler.isAdministrativeMode());

            System.out.println("Administrative mode set to "+handler.isAdministrativeMode());
        }
        else
        {
            System.out.println("You need to open a file!");
        }
    }
}
