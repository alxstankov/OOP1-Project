package events.models;

import events.interfaces.Event;
import handlers.models.InputHandler;
import players.models.Player;
import treasures.models.Treasure;

public class TreasureEvent implements Event {
    private Treasure foundTreasure;
    private transient InputHandler handler;

    public TreasureEvent(Treasure treasure)
    {
        this.foundTreasure = treasure;
    }

    public void setHandler(InputHandler handler) {
        this.handler = handler;
    }

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
