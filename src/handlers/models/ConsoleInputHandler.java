package handlers.models;

import handlers.interfaces.InputHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInputHandler implements InputHandler {
    private BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String readContent() throws IOException {
        return consoleReader.readLine();
    }
}
