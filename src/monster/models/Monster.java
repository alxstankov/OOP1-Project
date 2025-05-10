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
        this.armor = 15 + (5 * level);

    }

    public double getHealth() {
        return health;
    }

    public double attack()
    {
        int chosenAttack = attackDecider.nextInt(2);
        if (chosenAttack == 1)
        {
            return strength;
        }

        return mana;
    }

    public void dealDamage(double dealtDamage) {
        double calculatedAttack = dealtDamage*(armor/100);
        BigDecimal bd = new BigDecimal(calculatedAttack);
        this.health -= bd.setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public String toString() {
        return "Monster:\n" +
                "- Strength: "+strength+"\n" +
                "- Mana: "+mana+"\n" +
                "- Health: "+health+"\n";
    }
}
