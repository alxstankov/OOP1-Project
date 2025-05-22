package events.models;

import events.interfaces.Event;
import handlers.models.InputHandler;
import players.Player;
import treasures.models.Treasure;

/**
 * The {@code TreasureEvent} class implements the {@link events.interfaces.Event} interface.
 * This class is responsible for carrying out the event for acquiring a new item during gameplay.
 */
public class TreasureEvent implements Event {
    /**
     * The found treasure, that the player can decide if it wants to pick up.
     */
    private Treasure foundTreasure;
    /**
     * The input handler, that is going to be used for reading user input and checking program and game running status.
     */
    private transient InputHandler handler;

    /**
     * Constructs a {@code TreasureEvent} instance with the given found treasure, that the player can decide if it wants to pick up.
     *
     * @param treasure Instance of the found treasure
     */
    public TreasureEvent(Treasure treasure)
    {
        this.foundTreasure = treasure;
    }

    public void setHandler(InputHandler handler) {
        this.handler = handler;
    }

    /**
     * This is an implementation of the {@link events.interfaces.Event#startEvent(Player)} method.
     * This event gives the player the option to acquire the treasure, found on the game board.
     * If the player decides to pick up the treasure, the new item will replace the old treasure of the same type.
     * If the player decides to leave the treasure, the new item is flushed and cannot be found again.
     *
     * @param player The player who found the treasure.
     * @throws Exception Exceptions that happen during the event
     */
    @Override
    public void startEvent(Player player) throws Exception {
        Treasure playerTreasure = player.getTreasureItem(foundTreasure.getTreasureType());
        String[] eventInput;

        eventLogic: while (handler.isGameActive())
        {
            System.out.println("Found treasure!");
            System.out.println(foundTreasure.toString());
            System.out.println("Your item:");
            if (playerTreasure == null)
            {
                System.out.println("You do not have an item of this type");
            }
            else
            {
                System.out.println(playerTreasure);
            }
            System.out.println("Do you want to get this item? [y/n]");

            eventInput = handler.handleCommand();

            if (eventInput == null) {
                continue;
            }

            if (eventInput.length != 1)
            {
                System.out.println("No such input");
                continue;
            }

             switch (eventInput[0])
            {
                case "y":
                    player.pickUpItem(foundTreasure);
                    break eventLogic;
                case "n":
                    break eventLogic;
                case "help":
                    help();
                    break;
                default:
                    System.out.println("No such input");
                    break;
            }

        }
    }

    /**
     * Shows all supported event commands on the system's default output stream.
     */
    private void help()
    {
        String helpText = "Treasure event\n" +
                "The player has found treasure. This can be armour, weapon, or a special spell.\n" +
                "The player can choose if it wants to pick the particular item or to leave it.\n" +
                "If the item is picked, the old item is replaced with the new. If the new item is left, it cannot be picked up again.\n" +
                "Commands:\n" +
                "- y - Picks up the found item.\n" +
                "- n - Leaves the found item.\n" ;

        System.out.println(helpText);
    }
}
