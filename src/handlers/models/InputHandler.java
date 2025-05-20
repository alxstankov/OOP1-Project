package handlers.models;

import game.models.GameProcessor;

import handlers.commands.*;
import handlers.interfaces.FileHandler;
import handlers.interfaces.NonParametrizedCommand;
import handlers.interfaces.ParametrizedCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
/**
 * The {@code InputHandler} Class is responsible for handling the input, provided by the user.
 * It is used to handle commands for the program state, file system and gameplay.
 */
public class InputHandler {
    /**
     * The reader, that fetched user input from the system's default input stream.
     * The reader is essential part of the class, and it is set up automatically upon creating an object of this class.
     */
    private BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    /**
    * The file handler is responsible for the basic functionality, while handling the file system.
    */
    private FileHandler fileHandler;
    /**
     * Shows if the program is active, while executing commands.
     * Used for the main loop for receiving and executing user input.
     */
    private boolean isProgramActive = true;
    /**
     * Shows if the program is in administrative mode.
     * This mode unlocks certain functionalities of the program.
     */
    private boolean administrativeMode = false;
    /**
     * Holds general commands that require parameters.
     * The key represent the name of the command.
     * The values are the commands, that will be executed, when being called.
     */
    private Map<String, ParametrizedCommand> parameterizedCommands = new HashMap<>() {{
        put("open", new OpenFileCommand());
        put("save-as", new SaveAsCommand());
        put("new-game",new NewGameCommand());

    }};
    /**
     * Holds general commands that does not require parameters.
     * The key represent the name of the command.
     * The values are the commands, that will be executed, when being called.
     */
    private Map<String, NonParametrizedCommand> nonParameterizedCommands = new HashMap<>(){{
        put("close", new CloseFileCommand());
        put("save", new SaveCommand());
        put("exit",new ExitProgramCommand());
        put("help",new HelpCommand());
        put("set-mode",new SetAdministrativeModeCommand());
    }};
    /**
     * An instance of the game.
     * Upon starting the program, the field is null.
     * Starting a new game or reading a save file, sets a game instance to the field, that can be later played.
     */
    private GameProcessor game;

    /**
     * Constructs a {@code InputHandler} instance with the given instance of a file handler.
     *
     * @param fileHandler Handler that will be used for handling files
     */
    public InputHandler(FileHandler fileHandler)
    {
        this.fileHandler = fileHandler;
    }
    /**
     * Starts the program and the main loop.
     * While the program is active, the method checks if a game instance is presents and starts the game logic.
     * If a game instance is not set, the method calls the handleCommand method, that carries out the general commands of the program.
     *
     * @throws Exception when other exceptions have been thrown from the command handling or game logic
     * @see #handleCommand()
     */
    public void startProgram() throws Exception {

        while (isProgramActive)
        {
            if (game == null)
            {
                handleCommand();
            }
            else
            {
                game.startGame();
                game = null;
            }
        }
    }
    /**
     * Handles all the commands of the program.
     * If a general command is detected, it is carried out in this method.
     * If a game command is called, the method prepares a string array with the command name and given parameters.
     *
     * @return A string array, needed for calling game commands or null, if the input calls for a general command
     * @throws IOException If an error occurs with reading the user input
     */
    public String[] handleCommand() throws IOException {
        String[] input;

        System.out.print(">> ");
        input = consoleReader.readLine().toLowerCase().split(" ");

        if (input.length == 2)
        {
            ParametrizedCommand parameterizedCommand = parameterizedCommands.get(input[0]);
            if (parameterizedCommand != null)
            {
                parameterizedCommand.setHandler(this);
                parameterizedCommand.executeCommand(input[1]);
                return null;
            }
        } else if (input.length == 1) {
            NonParametrizedCommand nonParameterizedCommand = nonParameterizedCommands.get(input[0]);
            if (nonParameterizedCommand != null)
            {
                nonParameterizedCommand.setHandler(this);
                nonParameterizedCommand.executeCommand();
                if (input[0].equals("help"))
                {
                    return input;
                }
                return null;
            }
        }

        if (game == null)
        {
            System.out.println("No such input");
        }

        return input;
    }

    /**
     * Shows if the program is in administrative mode.
     * @return Boolean representing if administrative mode is active.
     */
    public boolean isAdministrativeMode() {
        return administrativeMode;
    }

    /**
     * Sets the runtime status of the program.
     *
     * @param programActive Boolean representing the runtime state of the program
     */
    public void setProgramActive(boolean programActive) {
        isProgramActive = programActive;
    }

    /**
     * Sets the administrative mode of the program.
     *
     * @param administrativeMode Boolean representing the administrative mode of the program
     */
    public void setAdministrativeMode(boolean administrativeMode) {
        this.administrativeMode = administrativeMode;
    }

    /**
     * Gets the loaded file handler.
     *
     * @return File handler instance
     */
    public FileHandler getFileHandler() {
        return fileHandler;
    }

    /**
     * Gets the loaded game.
     *
     * @return Game instance
     */
    public GameProcessor getGame() {
        return game;
    }

    /**
     * Sets a new game instance.
     *
     * @param game New game instance
     */
    public void setGame(GameProcessor game) {
        this.game = game;
    }

    /**
     * Shows if the game is still active.
     * @return Boolean that represents if the game is active
     */
    public boolean isGameActive()
    {
        return game !=null;
    }

}
