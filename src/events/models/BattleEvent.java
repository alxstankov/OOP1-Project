package events.models;

import events.interfaces.Event;
import handlers.models.InputHandler;
import monster.Monster;
import players.Player;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

/**
 * The {@code BattleEvent} class implements the {@link events.interfaces.Event} interface.
 * This class is responsible for carrying out the battles during gameplay.
 */
public class BattleEvent implements Event {
    /**
     * The monster that the users battles.
     */
    private Monster monster;
    /**
     * The health points, with which the player enters the battle.
     */
    private double initialHealth;
    /**
     * The player that has encountered the monster.
     */
    private Player player;
    /**
     * The input handler, that is going to be used for reading user input and checking program and game running status.
     */
    private transient InputHandler handler;
    /**
     * Holds the commands that are specific for this event.
     * The key represent the name of the command.
     * The values are the commands for the player attacks, that will be executed, when being called.
     */
    private Map<String, Supplier<Double>> eventCommands = new HashMap<>()
    {{
        put("use-attack",(Supplier<Double> & Serializable)()->player.getBasicAttack());
        put("use-spell",(Supplier<Double> & Serializable)()->player.getSpellAttack());

    }};

    /**
     * Constructs a {@code BattleEvent} instance with the given level difficulty, that decides the stats of the encountered monster.
     *
     * @param level Numerical representation of the difficulty level
     */
    public BattleEvent(int level)
    {
        this.monster = new Monster(level);
    }


    public void setHandler(InputHandler handler) {
        this.handler = handler;
    }

    /**
     * This is an implementation of the {@link events.interfaces.Event#startEvent(Player)} method.
     * The method starts the event by deciding who attacks first.
     * Both the player and the monster take turns to attack.
     * The event ends, when one of them dies.
     *
     * If the player dies. The game is over.
     * If the player survives, it heals a certain percentage of the health, depending on the health after the battle.
     * The player heals 50% of the initial health if is under 50% of the current health.
     * If the current health is above the half mark, the player heals 25% of the initial health.
     *
     * @param player The player that has encountered the monster
     * @throws Exception Exceptions that happen during the event.
     */
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
    /**
     * Handles all the commands of the event.
     * If an event command is detected, the result of the attack will be returned.
     *
     * @return The attack points, that will be dealt to the monster
     * @throws IOException If an error occurs with reading the user input
     */
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

    /**
     * Shows all supported event commands on the system's default output stream.
     */
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
