package events.interfaces;

import handlers.models.InputHandler;
import players.models.Player;

import java.io.Serializable;

/**
 * The {@code Event} interface defines the methods, that need to be implemented by the concrete implementations.
 * The interface implements the entry point of every event and the method, that sets the input handler.
 */
public interface Event extends Serializable {
    /**
     * Starts the game event.
     *
     * @param player Player instance that is used in the game
     * @throws Exception If an error occurs during the event
     */
    void startEvent(Player player) throws Exception;
    /**
     * Sets the handler, that will be used for reading user input.
     *
     * @param handler Input handler, used for user input and checking program and game running status
     */
    void setHandler(InputHandler handler);
}
