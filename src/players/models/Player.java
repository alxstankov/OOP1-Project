package players.models;

import handlers.models.RoundingCalculator;
import treasures.enums.TreasureType;
import treasures.models.Spell;
import treasures.models.Treasure;
import treasures.models.Weapon;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class Player implements Serializable {
    private double baseHealth = 50;
    private double basicAttack;
    private double spellAttack;
    private Map<TreasureType, Treasure> inventory = new HashMap<>()
    {{
        put(TreasureType.WEAPON,new Weapon(0.20));
        put(TreasureType.SPELL,new Spell(0.20));
    }};

    public Player(int basicAttack, int spellAttack)
    {
        this.basicAttack = basicAttack;
        this.spellAttack = spellAttack;
    }

    public double getCurrentHealth() {
        return baseHealth;
    }

    public double getBasicAttack() {
        if (inventory.containsKey(TreasureType.WEAPON))
        {
            double calculatedAttack = basicAttack * (1+inventory.get(TreasureType.WEAPON).getStat());
            return RoundingCalculator.roundDecimal(calculatedAttack);
        }
        return basicAttack;
    }

    public double getSpellAttack() {
        if (inventory.containsKey(TreasureType.SPELL))
        {
            double calculatedAttack = spellAttack * (1+inventory.get(TreasureType.SPELL).getStat());
            return RoundingCalculator.roundDecimal(calculatedAttack);
        }
        return spellAttack;
    }

    public void pickUpItem(Treasure treasure)
    {
        inventory.put(treasure.getTreasureType(),treasure);
    }

    public Treasure getTreasureItem(TreasureType type)
    {
        return inventory.get(type);
    }

    public void updateBasicAttack(int additionalBasicAttack) {
        basicAttack += additionalBasicAttack;
    }

    public void updateSpellAttack(int additionalSpellAttack) {
        spellAttack += additionalSpellAttack;
    }

    public void removeHealth(int dealtDamage)
    {
        if (inventory.containsKey(TreasureType.ARMOR))
        {
            double calculatedAttack = dealtDamage*(1-inventory.get(TreasureType.ARMOR).getStat());
            baseHealth -= calculatedAttack;
        }
        else
        {
            baseHealth -= dealtDamage;
        }
        baseHealth = RoundingCalculator.roundDecimal(baseHealth);
    }

    public void updateHealth(double additionalHealth) {
        baseHealth = RoundingCalculator.roundDecimal(baseHealth+additionalHealth);
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("- Health: ").append(baseHealth).append("\n");
        builder.append("- Basic attack: ").append(basicAttack).append("\n");
        builder.append("- Spell attack: ").append(spellAttack).append("\n");
        builder.append("Inventory:").append("\n");

        for (Map.Entry<TreasureType,Treasure> entry: inventory.entrySet())
        {
            builder.append(entry.getValue().toString()).append("\n");
        }

        return builder.toString();

    }
}
