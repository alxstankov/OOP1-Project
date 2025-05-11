package events.models;

import events.interfaces.Event;
import handlers.models.ConsoleInputHandler;
import monster.models.Monster;
import players.models.Player;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class BattleEvent implements Event {
    private Monster monster;
    private double initialHealth;
    private Player player;
    private Map<String, Supplier<Double>> eventCommands = new HashMap<>()
    {{
        put("use-attack",()->player.getBasicAttack());
        put("use-spell",()->player.getSpellAttack());

    }};

    public BattleEvent(int level)
    {
        this.monster = new Monster(level);
    }

    @Override
    public boolean startEvent(Player player) throws Exception {
        this.player = player;
        this.initialHealth = this.player.getCurrentHealth();

        int firstMove = new Random().nextInt(2);

        System.out.println("Monster encountered");

        if (firstMove == 1)
        {
            System.out.println("Player starts");
        }
        else
        {
            System.out.println("Monster starts");
        }

        do
        {
            if (firstMove == 1)
            {
                System.out.println(player);
                System.out.println(monster);
                monster.dealDamage(selectAttack());
                if (monster.getHealth()<=0)
                {
                    break;
                }
                player.removeHealth(monster.attack());
            }
            else
            {
                player.removeHealth(monster.attack());
                System.out.println(player);
                System.out.println(monster);
                if (player.getCurrentHealth()<= 0)
                {
                    break;
                }
                monster.dealDamage(selectAttack());
            }
        }
        while(player.getCurrentHealth()>0 && monster.getHealth()>0);

        if (player.getCurrentHealth()>0)
        {
            double calculatedHealth;
            if (player.getCurrentHealth()<=initialHealth/2)
            {
                calculatedHealth = initialHealth/2;
            }
            else
            {
                calculatedHealth = initialHealth/4;
            }
            BigDecimal bd = new BigDecimal(calculatedHealth);
            player.updateHealth(bd.setScale(1, RoundingMode.HALF_UP).doubleValue());
        }
        return false;
    }

    private double selectAttack() throws IOException {
        String[] eventInput;
        Supplier<Double> eventCommand;
        double attack = 0;
        while (attack == 0) {
            System.out.println("Select an attack:");
            System.out.print(">> ");
            eventInput = ConsoleInputHandler.readContent();

            if (eventInput.length != 1) {
                System.out.println("No such input");
                continue;
            }

            eventCommand = eventCommands.get(eventInput[0]);

            if (eventCommand == null) {
                System.out.println("No such input");
                continue;
            }

            attack = eventCommand.get();
        }

        return attack;
    }
}
