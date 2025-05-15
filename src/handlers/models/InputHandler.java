package handlers.models;

import game.models.GameProcessor;
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

    private Map<String, Consumer<String>> parameterizedCommands = new HashMap<>() {{
        put("open", (fileLocation) -> open(fileLocation));
        put("save-as", (fileLocation) -> saveAs(fileLocation));
    }};

    private Map<String,Runnable> nonParameterizedCommands = new HashMap<>(){{
        put("close", () -> close());
        put("save", () -> save());
        put("exit",() -> exit());
        put("new-game",()->newGame());
        put("help",()-> help());
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

    private void open(String fileLocation) {
        File file = new File(fileLocation);
        this.game = fileHandler.readFile(file);

        if (game != null)
        {
            this.game.setHandler(this);
        }
    }

    private void newGame()
    {
        if (fileHandler.isFileOpened())
        {
            this.game = new GameProcessor(this);
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
        String helpText = "The following commands are supported:\n" +
                "- open <file> - opens <file>\n" +
                "- close - closes currently opened file\n" +
                "- save - saves the currently opened file\n" +
                "- save-as <file> - saves the currently opened file in <file>\n" +
                "- new-game - starts a new game\n" +
                "- help - prints this information\n" +
                "- exit - exits the program\n";
        System.out.println(helpText);
    }
}
