import gameboard.GameboardGenerator;

public class Application {
    public static void main(String[] args) {
        GameboardGenerator gameboardGenerator = new GameboardGenerator(10,10);
        gameboardGenerator.generateBoard();
        System.out.println(gameboardGenerator.toString());
    }
}
