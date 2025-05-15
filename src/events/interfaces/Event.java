package events.interfaces;

import handlers.models.InputHandler;
import players.models.Player;

import java.io.Serializable;


public interface Event extends Serializable {
    void startEvent(Player player) throws Exception;
    void setHandler(InputHandler handler);
}
