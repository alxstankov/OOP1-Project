import game.models.GameProcessor;
import handlers.models.InputHandler;

public class Application {
    public static void main(String[] args) {
        GameProcessor game = new GameProcessor(new InputHandler());
        try{
            game.startGame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        Add administrative functions for custom levels
//        Add help command to each class, that uses different commands
//        Each Event has special inputs, that should not block main CLI inputs.
//        Make the key classes serializable, so they can be serialized and deserialized upon reading from and writing to a binary file.
    }
}
