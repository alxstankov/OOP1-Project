package monster.models;

import handlers.models.RoundingCalculator;

import java.io.Serializable;
import java.util.Random;
/**
 * The Monster class represent the monsters, that are fought during the {@link events.models.BattleEvent}.
 */
public class Monster implements Serializable {
    /**
     * The strength of the monster.
     */
    private int strength;
    /**
     * The spell strength of the monster.
     */
    private int mana;
    /**
     * The current health of the monster.
     */
    private double health;
    /**
     * The armour of the monster.
     * It represents the fraction of the damage, that will not be removed from the current health.
     */
    private double armor;
    /**
     * {@link java.util.Random} instance that is used for randomly deciding monster's next attack.
     */
    private Random attackDecider = new Random();

    /**
     * Constructs  a {@code Monster} instance with stats, depending on the provided level difficulty.
     *
     * @param level Numerical representation of the difficulty level, that is used for calculating monster's stats.
     */
    public Monster(int level)
    {
        level--;
        this.strength = 25 + (10 * level);
        this.mana = 25 + (10 * level);
        this.health = 50 + (10 * level);
        this.armor = RoundingCalculator.roundDecimal(0.15 + (0.05 * level));

    }
    /**
     * Gets the current health of the monster
     *
     * @return The current health of the monster
     */
    public double getHealth() {
        return health;
    }
    /**
     * Randomly decides the monster's attack using attackDecider's {@link java.util.Random} instance and gets the attack stat of the used attack.
     *
     * @return The attack stat of the monster.
     */
    public int attack()
    {
        int chosenAttack = attackDecider.nextInt(2);
        if (chosenAttack == 1)
        {
            return strength;
        }

        return mana;
    }
    /**
     * Sets the new current health of the monster by removing the given dealt damage.
     *
     * @param dealtDamage Damage that is dealt to the monster.
     */
    public void dealDamage(double dealtDamage) {
        double calculatedAttack = dealtDamage*(1-armor);
        this.health = RoundingCalculator.roundDecimal(health-calculatedAttack);
    }

    /**
     *  Gives the string representation of the monster.
     *
     * @return String representation of the monster and its stats.
     */
    @Override
    public String toString() {
        return "Monster:\n" +
                "- Strength: "+strength+"\n" +
                "- Mana: "+mana+"\n" +
                "- Armor: "+RoundingCalculator.roundDecimal(armor*100)+"% \n"+
                "- Health: "+health+"\n";
    }
}
