package handlers.models;

import handlers.interfaces.FileHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class InputHandler {
    private BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    private FileHandler fileHandler;

    private Map<String, Runnable> fileCommands = new HashMap<>() {{
        put("open", () -> System.out.println("Open"));
        put("close", () -> System.out.println("Close"));
        put("save", () -> System.out.println("Save"));
        put("save-as", () -> System.out.println("Save-as"));
    }};



    public String[] handleCommand() throws IOException {
        String[] input;
        Runnable generalCommand;

        System.out.print(">> ");
        input = consoleReader.readLine().toLowerCase().split(" ");

        if (input.length == 1)
        {
            generalCommand = fileCommands.get(input[0]);
            if (generalCommand != null)
            {
                generalCommand.run();
                return null;
            }
        }

        return input;
    }
}
