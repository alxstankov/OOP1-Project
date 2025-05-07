package events.models;

import events.interfaces.Event;
import handlers.models.ConsoleInputHandler;
import players.models.Player;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class PlayerLevelUpEvent implements Event{
    private int levelUpPoints = 30;
    private Player player;
    private Map<String, Consumer<Integer>> eventControls = new HashMap<>()
    {{
        put("health",(points) -> player.updateHealth(points));
        put("attack",(points) -> player.updateBasicAttack(points));
        put("spell",(points) -> player.updateSpellAttack(points));
    }};


    @Override
    public boolean startEvent(Player player) throws Exception {
        this.player = player;

        Consumer<Integer> eventCommand;
        String[] gameInput;
        int inputPoints;

        while (levelUpPoints>0)
        {
            System.out.println(player);
            System.out.println("Remaining amount of points: "+levelUpPoints);
            System.out.println("Choose which attribute to upgrade: health / attack / spell [amount of points]");
            System.out.print(">>");
            gameInput = ConsoleInputHandler.readContent();

            if (gameInput.length != 2)
            {
                System.out.println("No such input");
                continue;
            }

            eventCommand = eventControls.get(gameInput[0]);
            if(eventCommand==null)
            {
                System.out.println("No such input");
                continue;
            }
            try
            {
                inputPoints = Integer.parseInt(gameInput[1]);
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
        return true;
    }
}
