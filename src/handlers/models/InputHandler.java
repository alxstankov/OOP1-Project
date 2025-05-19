package handlers.models;

import game.models.GameProcessor;
import game.models.RandomLevelGenerator;
import handlers.interfaces.FileHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
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
    private Map<String, Consumer<String>> parameterizedCommands = new HashMap<>() {{
        put("open", (fileLocation) -> open(fileLocation));
        put("save-as", (fileLocation) -> saveAs(fileLocation));
        put("new-game",(gamemode)->newGame(gamemode));

    }};
    /**
     * Holds general commands that does not require parameters.
     * The key represent the name of the command.
     * The values are the commands, that will be executed, when being called.
     */
    private Map<String,Runnable> nonParameterizedCommands = new HashMap<>(){{
        put("close", () -> close());
        put("save", () -> save());
        put("exit",() -> exit());
        put("help",()-> help());
        put("set-mode",()->setAdministrativeMode());
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
            Consumer<String> parameterizedCommand = parameterizedCommands.get(input[0]);
            if (parameterizedCommand != null)
            {
                parameterizedCommand.accept(input[1]);
                return null;
            }
        } else if (input.length == 1) {
            Runnable nonParameterizedCommand = nonParameterizedCommands.get(input[0]);
            if (nonParameterizedCommand != null)
            {
                nonParameterizedCommand.run();
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
     * Calls the fileHandler's {@link handlers.interfaces.FileHandler#saveAsFile(File, GameProcessor)} method for saving loaded game instance in a targeted file.
     * If a file is not opened or a game is not loaded, the method shows an appropriate message on the system's default output stream.
     *
     * @param fileLocation File location of the file, that will be used for saving a loaded game instance
     */
    private void saveAs(String fileLocation){
        if (fileHandler.isFileLoaded())
        {
            if (game != null)
            {
                File file = new File(fileLocation);
                fileHandler.saveAsFile(file,game);
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

    /**
     * Calls the fileHandler's {@link handlers.interfaces.FileHandler#saveFile(GameProcessor)} method for saving loaded game instance in the currently loaded file.
     * If a file is not opened or a game is not loaded, the method shows an appropriate message on the system's default output stream.
     */
    private void save(){
        if (fileHandler.isFileLoaded())
        {
            if (game != null)
            {
                fileHandler.saveFile(game);
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
    /**
     * Switches between normal and administrative mode of th program
     * If a file is not opened, the method shows an appropriate message on the system's default output stream.
     */
    private void setAdministrativeMode(){
        if (fileHandler.isFileLoaded())
        {
            administrativeMode  = !administrativeMode;

            System.out.println("Administrative mode set to "+administrativeMode);
        }
        else
        {
            System.out.println("You need to open a file!");
        }
    }
    /**
     * Shows if the program is in administrative mode.
     * @return Boolean representing if administrative mode is active.
     */
    public boolean isAdministrativeMode() {
        return administrativeMode;
    }
    /**
     * Calls the fileHandler's {@link handlers.interfaces.FileHandler#readFile(File)} method for loading the targeted file.
     * If the file handler reads a saved game, the game instance is loaded into the program.
     * @param fileLocation File location of the file, that will be read from
     */
    private void open(String fileLocation) {
        File file = new File(fileLocation);
        this.game = fileHandler.readFile(file);

        if (game != null)
        {
            this.game.setHandler(this);
        }
    }
    /**
     * Creates a new game instance.
     * Depending on the mode, the method will create a normal or custom instance of the game.
     * The custom instance is only accessible when in administrative mode.
     * If a file is not opened, the method shows an appropriate message on the system's default output stream.
     *
     * @param mode The game type, that decides what instance of the game needs to be created
     */
    private void newGame(String mode)
    {
        if (fileHandler.isFileLoaded())
        {
            if (administrativeMode)
            {
                if (mode.equals("custom"))
                {
                    this.game = new GameProcessor(this, RandomLevelGenerator.generateLevel());
                }
                else if (mode.equals("classic")) {
                    this.game = new GameProcessor(this);
                }
                else
                {
                    System.out.println("The given game mode is not recognized");
                    return;
                }
            }
            else
            {
                if (mode.equals("custom"))
                {
                    System.out.println("Administrative mode is not set!");
                    return;
                }
                else if (mode.equals("classic")) {
                    this.game = new GameProcessor(this);
                }
                else
                {
                    System.out.println("The given game mode is not recognized");
                    return;
                }
            }
            System.out.println("Started new game");
        }
        else
        {
            System.out.println("You need to open a file!");
        }
    }
    /**
     * Calls the fileHandler's {@link FileHandler#closeFile()} method for closing the currently loaded file.
     * When the loaded file is successfully closed, the loaded game instance is flushed.
     * If the file handler reads a saved game, the game instance is loaded into the program.
     */
    private void close()
    {
        if (fileHandler.isFileLoaded())
        {
            fileHandler.closeFile();
            this.game = null;
        }
        else
        {
            System.out.println("You need to open a file!");
        }

    }
    /**
     * Shows if the game is still active.
     * @return Boolean that represents if the game is active
     */
    public boolean isGameActive()
    {
        return game !=null;
    }
    /**
     * Exits the program.
     * The loaded game instance is flushed and the main loop for checking for loaded game instances and incoming user input is stopped.
     * After stopping the loop, the program exits.
     */
    private void exit()
    {
        this.game = null;
        this.isProgramActive = false;
        System.out.println("Exiting the program...");
    }
    /**
     * Shows all supported general commands on the system's default output stream.
     */
    private void help()
    {
        String administrativeGameMode = administrativeMode ? "/ custom " : "";
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
