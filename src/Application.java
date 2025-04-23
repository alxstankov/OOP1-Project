import game.GameProcessor;

public class Application {
    public static void main(String[] args) {
        GameProcessor game = new GameProcessor();
        try{
            game.startGame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
