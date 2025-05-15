package events.models;

import events.interfaces.Event;
import handlers.models.InputHandler;
import monster.models.Monster;
import players.models.Player;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class BattleEvent implements Event {
    private Monster monster;
    private double initialHealth;
    private Player player;
    private transient InputHandler handler;
    private Map<String, Supplier<Double>> eventCommands = new HashMap<>()
    {{
        put("use-attack",(Supplier<Double> & Serializable)()->player.getBasicAttack());
        put("use-spell",(Supplier<Double> & Serializable)()->player.getSpellAttack());

    }};

    public BattleEvent(int level)
    {
        this.monster = new Monster(level);
    }

    public void setHandler(InputHandler handler) {
        this.handler = handler;
    }

    @Override
    public void startEvent(Player player) throws Exception {
        this.player = player;
        this.initialHealth = this.player.getCurrentHealth();

        int firstMove = new Random().nextInt(2);

        help();

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
        while(player.getCurrentHealth()>0 && monster.getHealth()>0 && handler.isGameActive());

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
            player.updateHealth(calculatedHealth);
        }
    }

    private double selectAttack() throws IOException {
        String[] eventInput;
        Supplier<Double> eventCommand;
        double attack = 0;
        while (attack == 0 && handler.isGameActive()) {
            System.out.println("Select an attack:");
            eventInput = handler.handleCommand();

            if (eventInput == null) {
                continue;
            }

            if (eventInput.length != 1) {
                System.out.println("No such input");
                continue;
            }

            if (eventInput[0].equals("help"))
            {
                help();
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

    private void help()
    {
        String helpText = "Battle rules:\n" +
                "When a monster is encountered, it is randomly decided who attacks first.\n" +
                "The battle ends when the monster or the players dies.\n" +
                "If the player dies, the game ends.\n" +
                "If the monster dies, the players is healed with 50% of the health points, with which it started,\n" +
                "when the player wins with health below 50%.\n" +
                "If the player wins with more than 50% health, he get 25% of the initial health.\n" +
                "Commands:\n" +
                "- use-attack - The player uses basic attack\n" +
                "- use-spell -  The player uses spell attack \n";

        System.out.println(helpText);
    }
}
