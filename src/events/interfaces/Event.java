package events.interfaces;

import players.models.Player;


public interface Event {
    boolean startEvent(Player player) throws Exception;
}
