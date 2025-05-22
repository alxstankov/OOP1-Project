package handlers.models;

import game.GameProcessor;
import handlers.interfaces.FileHandler;

import java.io.*;
/**
 * The {@code DataFileHandler} class is responsible for handling the reading and writing of game instances from data files.
 * This class implements {@link handlers.interfaces.FileHandler} interface.
 */
public class DataFileHandler implements FileHandler {
    /**
     * The currently loaded data file.
     */
    private File file;

    /**
     * This method is an implementation of the {@link handlers.interfaces.FileHandler#readFile(File)} method.
     * It reads the given file and sets it at the current load file, if it is opened successfully.
     * If the file has a saved game, the reader loads the game and returns it to the caller of the method.
     * If the reader encounters an exception, the method handles the exception.
     *
     * @param file The file that the handler will read from.
     * @return The loaded game save or null, if a game save is not present
     */
    @Override
    public GameProcessor readFile(File file){
        GameProcessor game = null;

        try (ObjectInputStream gameDataReader = new ObjectInputStream(new FileInputStream(file)))
        {
                game = (GameProcessor) gameDataReader.readObject();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File is not found!");
            return game;
        }catch (NotSerializableException e)
        {
            System.out.println("The game file is not compatible with the latest version of the game!");
            return game;
        }
        catch (EOFException e) {
            System.out.println("File does not have a game save!");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.file = file;
        System.out.println("Successfully opened "+file.getName());
        return game;
    }

    @Override
    public boolean newFile(File file) {
        boolean fileExists;
        try
        {
            fileExists = file.createNewFile();

            if (!fileExists)
            {
                System.out.println("The file already exists!");

            }
            else
            {
                this.file = file;
                System.out.println("Successfully opened "+file.getName());
            }

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        return fileExists;
    }

    /**
     * This method is an implementation of the {@link FileHandler#closeFile()} method.
     * Closes the currently loaded file.
     */
    @Override
    public void closeFile() {
        System.out.println("Successfully closed "+file.getName());
        this.file = null;
    }
    /**
     * This method is an implementation of the {@link handlers.interfaces.FileHandler#saveFile(GameProcessor)} method.
     * The method saves the given game instance to the currently loaded file.
     * If the reader encounters an exception, the method handles the exception.
     *
     * @param game The game instance that needs to be saved
     */
    @Override
    public void saveFile(GameProcessor game){
        try(ObjectOutputStream gameDataWriter = new ObjectOutputStream(new FileOutputStream(file)))
        {
            gameDataWriter.writeObject(game);
            gameDataWriter.flush();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        System.out.println("Successfully saved "+file.getName());
    }
    /**
     * This method is an implementation of the {@link handlers.interfaces.FileHandler#saveAsFile(File, GameProcessor)} method.
     * The method saves the given game instance to the targeted file.
     * If the reader encounters an exception, the method handles the exception.
     *
     * @param file The targeted file, where the game instance is going to be saved
     * @param game The game instance that needs to be saved
     */
    @Override
    public void saveAsFile(File file, GameProcessor game){

        try (ObjectOutputStream gameDataWriter = new ObjectOutputStream(new FileOutputStream(file))) {
                gameDataWriter.writeObject(game);
                gameDataWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Successfully saved another " + file.getName());
    }

    /**
     * Shows if a files is currently loaded
     *
     * @return Boolean that represent if a file is currently loaded
     */
    @Override
    public boolean isFileLoaded() {
        return file != null;
    }
}
