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

public class InputHandler {
    private BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    private FileHandler fileHandler;
    private boolean isProgramActive = true;
    private boolean administrativeMode = false;

    private Map<String, Consumer<String>> parameterizedCommands = new HashMap<>() {{
        put("open", (fileLocation) -> open(fileLocation));
        put("save-as", (fileLocation) -> saveAs(fileLocation));
        put("new-game",(gamemode)->newGame(gamemode));

    }};

    private Map<String,Runnable> nonParameterizedCommands = new HashMap<>(){{
        put("close", () -> close());
        put("save", () -> save());
        put("exit",() -> exit());
        put("help",()-> help());
        put("set-mode",()->setAdministrativeMode());
    }};

    private GameProcessor game;

    public InputHandler(FileHandler fileHandler)
    {
        this.fileHandler = fileHandler;
    }

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

    private void saveAs(String fileLocation){
        if (fileHandler.isFileOpened())
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

    private void save(){
        if (fileHandler.isFileOpened())
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

    private void setAdministrativeMode(){
        if (fileHandler.isFileOpened())
        {
            administrativeMode  = !administrativeMode;

            System.out.println("Administrative mode set to "+administrativeMode);
        }
        else
        {
            System.out.println("You need to open a file!");
        }
    }

    public boolean isAdministrativeMode() {
        return administrativeMode;
    }

    private void open(String fileLocation) {
        File file = new File(fileLocation);
        this.game = fileHandler.readFile(file);

        if (game != null)
        {
            this.game.setHandler(this);
        }
    }

    private void newGame(String mode)
    {
        if (fileHandler.isFileOpened())
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

    private void close()
    {
        if (fileHandler.isFileOpened())
        {
            fileHandler.closeFile();
            this.game = null;
        }
        else
        {
            System.out.println("You need to open a file!");
        }

    }

    public boolean isGameActive()
    {
        return game !=null;
    }

    private void exit()
    {
        this.game = null;
        this.isProgramActive = false;
        System.out.println("Exiting the program...");
    }

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
