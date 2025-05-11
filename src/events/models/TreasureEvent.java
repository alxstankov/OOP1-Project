package events.models;

import events.interfaces.Event;
import handlers.models.ConsoleInputHandler;
import players.models.Player;
import treasures.models.Treasure;

public class TreasureEvent implements Event {
    private Treasure foundTreasure;

    public TreasureEvent(Treasure treasure)
    {
        this.foundTreasure = treasure;
    }

    @Override
    public boolean startEvent(Player player) throws Exception {
        Treasure playerTreasure = player.getTreasureItem(foundTreasure.getTreasureType());
        String[] eventInput;

        eventLogic: while (true)
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
                System.out.println(playerTreasure.toString());
            }
            System.out.println("Do you want to get this item? [y/n]");

            eventInput = ConsoleInputHandler.readContent();

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
                default:
                    System.out.println("No such input");
                    break;
            }

        }

        return false;
    }
}
