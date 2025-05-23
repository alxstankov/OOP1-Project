package events.models;

import events.interfaces.Event;
import handlers.models.InputHandler;
import players.Player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * The {@code PlayerLevelUpEvent} class implements the {@link events.interfaces.Event} interface.
 * This class is responsible for carrying out the leveling up of the player during gameplay.
 */
public class PlayerLevelUpEvent implements Event{
    /**
     * The current amount of points left for the user to use for leveling up.
     */
    private int levelUpPoints = 30;
    /**
     * The player that is going to be leveled up.
     */
    private Player player;
    /**
     * The input handler, that is going to be used for reading user input and checking program and game running status.
     */
    private transient InputHandler handler;
    /**
     * Holds the commands that are specific for this event.
     * The key represent the name of the command.
     * The values are the commands for leveling the specified stat, that will be executed, when being called.
     */
    private Map<String, Consumer<Integer>> eventControls = new HashMap<>()
    {{
        put("health",(Consumer<Integer> & Serializable)(points) -> player.updateHealth(points));
        put("attack",(Consumer<Integer> & Serializable)(points) -> player.updateBasicAttack(points));
        put("spell",(Consumer<Integer> & Serializable)(points) -> player.updateSpellAttack(points));
    }};


    public void setHandler(InputHandler handler) {
        this.handler = handler;
    }

    /**
     * This is an implementation of the {@link events.interfaces.Event#startEvent(Player)} method.
     * This event levels up the player, who has reached the end of the level.
     * The player has 30 points to level up his stats.
     *
     * @param player The player that is going to be leveled up
     * @throws Exception Exceptions that happen during the event
     */
    @Override
    public void startEvent(Player player) throws Exception {
        this.player = player;

        Consumer<Integer> eventCommand;
        String[] eventInput;
        int inputPoints;

        while (levelUpPoints>0 && handler.isGameActive())
        {
            System.out.println(player);
            System.out.println("Remaining amount of points: "+levelUpPoints);
            System.out.println("Choose which attribute to upgrade: health / attack / spell [amount of points]");

            eventInput = handler.handleCommand();

            if (eventInput == null) {
                continue;
            }

            if (eventInput.length == 1 && eventInput[0].equals("help"))
            {
                help();
                continue;
            }
            else if (eventInput.length != 2)
            {
                System.out.println("No such input");
                continue;
            }

            eventCommand = eventControls.get(eventInput[0]);
            if(eventCommand==null)
            {
                System.out.println("No such input");
                continue;
            }
            try
            {
                inputPoints = Integer.parseInt(eventInput[1]);
            }
            catch (NumberFormatException _e)
            {
                System.out.println("The points parameter is not a number");
                continue;
            }

            if (inputPoints>levelUpPoints)
            {
                System.out.println("The given amount of points is bigger than the remaining! Remaining points: "+levelUpPoints);
                continue;
            }
            else if (inputPoints<0)
            {
                System.out.println("The input amount of points cannot be a negative number");
                continue;
            } else if (inputPoints == 0) {
                System.out.println("The input number cannot be 0");
                continue;
            }

            eventCommand.accept(inputPoints);
            levelUpPoints -= inputPoints;
        }
    }
    /**
     * Shows all supported event commands on the system's default output stream.
     */
    private void help()
    {
        String helpText = "Player level up event\n" +
                "This event allows for leveling up the stats of a player.\n" +
                "At the end of the level, the player has 30 points, which can be distributed between health / attack / spell.\n" +
                "Commands:\n" +
                "- health <points> - Levels up health with the given amount of <points>\n" +
                "- attack <points> - Levels up attack with the given amount of <points>\n" +
                "- spell <points> - Levels up spell with the given amount of <points>\n";

        System.out.println(helpText);
    }
}
