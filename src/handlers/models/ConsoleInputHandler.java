package handlers.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInputHandler {
    private static BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    public static String[] readContent() throws IOException {
        System.out.print(">> ");
        return consoleReader.readLine().toLowerCase().split(" ");
    }
}
