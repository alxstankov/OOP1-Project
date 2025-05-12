package monster.models;

import handlers.models.RoundingCalculator;

import java.io.Serializable;
import java.util.Random;

public class Monster implements Serializable {
    private int strength;
    private int mana;
    private double health;
    private double armor;
    private Random attackDecider = new Random();

    public Monster(int level)
    {
        level--;
        this.strength = 25 + (10 * level);
        this.mana = 25 + (10 * level);
        this.health = 50 + (10 * level);
        this.armor = RoundingCalculator.roundDecimal(0.15 + (0.05 * level));

    }

    public double getHealth() {
        return health;
    }

    public int attack()
    {
        int chosenAttack = attackDecider.nextInt(2);
        if (chosenAttack == 1)
        {
            return strength;
        }

        return mana;
    }

    public void dealDamage(double dealtDamage) {
        double calculatedAttack = dealtDamage*(1-armor);
        this.health = RoundingCalculator.roundDecimal(health-calculatedAttack);
    }

    @Override
    public String toString() {
        return "Monster:\n" +
                "- Strength: "+strength+"\n" +
                "- Mana: "+mana+"\n" +
                "- Armor: "+RoundingCalculator.roundDecimal(armor*100)+"% \n"+
                "- Health: "+health+"\n";
    }
}
