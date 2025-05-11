import game.models.GameProcessor;

public class Application {
    public static void main(String[] args) {
        GameProcessor game = new GameProcessor();
        try{
            game.startGame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        Each Event has special inputs, that should not block main CLI inputs.
//        Make the key classes serializable, so they can be serialized and deserialized upon reading from and writing to a binary file.
    }
}
