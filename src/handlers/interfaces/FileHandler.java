package handlers.interfaces;

import game.models.GameProcessor;

import java.io.IOException;


public interface FileHandler {
    GameProcessor readFile(String file) throws Exception;
    void closeFile();
    void saveFile(GameProcessor game) throws IOException;
    void safeAsFile(String file, GameProcessor game) throws IOException;
}
