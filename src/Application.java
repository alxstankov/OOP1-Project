import handlers.models.DataFileHandler;
import handlers.models.InputHandler;

public class Application {
    public static void main(String[] args) {
        InputHandler handler = new InputHandler(new DataFileHandler());
        try{
            handler.startProgram();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        Add administrative functions for custom levels
    }
}
