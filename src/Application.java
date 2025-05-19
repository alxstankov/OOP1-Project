import handlers.models.DataFileHandler;
import handlers.models.InputHandler;
/**
 * {@code Application} class for entry point of the program.
 * */
public class Application {
    /**
     * Entry point of the program.
     */
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
