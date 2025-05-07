import game.models.GameProcessor;

public class Application {
    public static void main(String[] args) {
        GameProcessor game = new GameProcessor();
        try{
            game.startGame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        Create Events Interface
//        Where this Interface is used to create Player upgrade event, Monster Battle event, Treasure Find event.
//        Each Event has special inputs, that probably block main CLI inputs until the event is done.
//        Make the key classes serializable, so they can be serialized and deserialized upon reading from and writing to a binary file.
    }
}
