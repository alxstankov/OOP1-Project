package handlers.models;

import game.models.GameProcessor;
import handlers.interfaces.FileHandler;

import java.io.*;

public class DataFileHandler implements FileHandler {
    private File file;

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
    public void closeFile() {
        System.out.println("Successfully closed "+file.getName());
        this.file = null;
    }

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

    @Override
    public boolean isFileOpened() {
        return file != null;
    }
}
