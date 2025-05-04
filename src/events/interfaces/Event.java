package events.interfaces;

import players.models.BasePlayer;


public interface Event {
    boolean startEvent(BasePlayer player) throws Exception;
}
