package handlers.interfaces;

import game.models.GameProcessor;

import java.io.File;


public interface FileHandler {
    GameProcessor readFile(File file);
    void closeFile();
    void saveFile(GameProcessor game);
    void saveAsFile(File file, GameProcessor game);
    boolean isFileOpened();
}
