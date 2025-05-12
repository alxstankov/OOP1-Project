package handlers.models;

import game.models.GameProcessor;
import handlers.interfaces.FileHandler;

import java.io.*;

public class DataFileHandler implements FileHandler {
    private String file;

    @Override
    public GameProcessor readFile(String file) throws Exception{
        GameProcessor game;
        ObjectInputStream gameDataReader;
        gameDataReader = new ObjectInputStream(new FileInputStream(file));
        game = (GameProcessor) gameDataReader.readObject();
        gameDataReader.close();
        this.file = file;
        System.out.println("Successfully opened "+file);
        return game;
    }

    @Override
    public void closeFile() {
        System.out.println("Successfully closed "+file);
        this.file = null;
    }

    @Override
    public void saveFile(GameProcessor game) throws IOException {
        ObjectOutputStream gameDataWriter = new ObjectOutputStream(new FileOutputStream(file));
        gameDataWriter.writeObject(game);
        gameDataWriter.flush();
        gameDataWriter.close();
        System.out.println("Successfully saved "+file);
    }

    @Override
    public void safeAsFile(String file, GameProcessor game) throws IOException{
        ObjectOutputStream gameDataWriter = new ObjectOutputStream(new FileOutputStream(file));
        gameDataWriter.writeObject(game);
        gameDataWriter.flush();
        gameDataWriter.close();
        System.out.println("Successfully saved another"+file);
    }
}
