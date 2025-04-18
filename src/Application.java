import gameboard.GameboardGenerator;

public class Application {
    public static void main(String[] args) {
        GameboardGenerator gameboardGenerator = new GameboardGenerator(25,20);
        gameboardGenerator.generateBoard();
        System.out.println(gameboardGenerator.toString());
    }
}
