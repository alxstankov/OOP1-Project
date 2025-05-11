package monster.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class Monster {
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
        this.armor = BigDecimal.valueOf(0.15 + (0.05 * level)).setScale(1,RoundingMode.HALF_UP).doubleValue();

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
        this.health = BigDecimal.valueOf(health-calculatedAttack).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public String toString() {
        return "Monster:\n" +
                "- Strength: "+strength+"\n" +
                "- Mana: "+mana+"\n" +
                "- Armor: "+armor*100+"% \n"+
                "- Health: "+health+"\n";
    }
}
