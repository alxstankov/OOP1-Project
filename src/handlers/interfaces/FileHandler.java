package handlers.interfaces;

import game.GameProcessor;

import java.io.File;

/**
 * The {@code FileHandler} interface defines the methods, that can be accessed from the concrete handler implementations.
 * The interface defines all the needed methods, needed for handling reading and writing files.
 */
public interface FileHandler {
    /**
     * Loads the file contents and returns the loaded game instance
     * @param file The file that is going to be read from.
     * @return Loaded game instance
     */
    GameProcessor readFile(File file);
    /**
     * Creates a new file, that is loaded in the program
     * @param file The file, that is going to be created.
     */
    void newFile(File file);
    /**
     * Closes the currently loaded file.
     */
    void closeFile();
    /**
     * Saves the given game instance in the currently loaded file.
     *
     * @param game The game instance, that needs to be saved.
     */
    void saveFile(GameProcessor game);
    /**
     * Saves the given game instance in the targeted file.
     *
     * @param file The targeted file, where the game instance is going to be saved
     * @param game The game instance, that needs to be saved.
     */
    void saveAsFile(File file, GameProcessor game);
    /**
     * Shows if a file is currently loaded.
     *
     * @return Boolean, that represents if a files is currently loaded.
     */
    boolean isFileLoaded();
}
