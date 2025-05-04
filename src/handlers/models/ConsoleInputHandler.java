package handlers.models;

import handlers.interfaces.InputHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInputHandler implements InputHandler {
    private static BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    public static String[] readContent() throws IOException {
        return consoleReader.readLine().toLowerCase().split(" ");
    }
}
