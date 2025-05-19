package players.models;

import handlers.models.RoundingCalculator;
import treasures.enums.TreasureType;
import treasures.models.Spell;
import treasures.models.Treasure;
import treasures.models.Weapon;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * This {@code Player} class is responsible for implementing base functionality for all player types.
 */
public abstract class Player implements Serializable {
    /**
     * Current health.
     */
    private double baseHealth = 50;
    /**
     * Strength of basic attack.
     */
    private double basicAttack;
    /**
     * Strength of spell attack.
     */
    private double spellAttack;
    /**
     * Inventory, containing treasures, enhancing player statistics.
     * The player can hold only one treasure instance of each treasure type.
     * 
     * The key represents the treasure type.
     * The value are the treasure instances.
     */
    private Map<TreasureType, Treasure> inventory = new HashMap<>()
    {{
        put(TreasureType.WEAPON,new Weapon(0.20));
        put(TreasureType.SPELL,new Spell(0.20));
    }};
    /**
     * Sets a base constructor for the player types.
     * The {@code Player} class is abstract, so it cannot create instances.
     */
    public Player(int basicAttack, int spellAttack)
    {
        this.basicAttack = basicAttack;
        this.spellAttack = spellAttack;
    }

    /**
     * Gets current health.
     * 
     * @return Current health
     */
    public double getCurrentHealth() {
        return baseHealth;
    }

    /**
     * Gets basic attack.
     * If the player has a weapon, the basic attack is buffed.
     *
     * @return Basic attack
     */
    public double getBasicAttack() {
        if (inventory.containsKey(TreasureType.WEAPON))
        {
            double calculatedAttack = basicAttack * (1+inventory.get(TreasureType.WEAPON).getStat());
            return RoundingCalculator.roundDecimal(calculatedAttack);
        }
        return basicAttack;
    }

    /**
     * Gets spell attack.
     * If the spell has a weapon, the spell attack is buffed.
     *
     * @return Spell attack
     */
    public double getSpellAttack() {
        if (inventory.containsKey(TreasureType.SPELL))
        {
            double calculatedAttack = spellAttack * (1+inventory.get(TreasureType.SPELL).getStat());
            return RoundingCalculator.roundDecimal(calculatedAttack);
        }
        return spellAttack;
    }

    /**
     *  Picks up new treasure and replaces the old treasure in the inventory.
     *  
     * @param treasure The new treasure, that will be put in the inventory
     */
    public void pickUpItem(Treasure treasure)
    {
        inventory.put(treasure.getTreasureType(),treasure);
    }

    /**
     * Gets the treasure of the given treasure type.
     *
     * @param type The treasure type, that is searched for in the inventory
     * @return Instance of the treasure, found in the inventory
     */
    public Treasure getTreasureItem(TreasureType type)
    {
        return inventory.get(type);
    }

    /**
     * Adds points in the basic attack stat.
     * @param additionalBasicAttack Additional points, that are going to be added to basic attack stat
     */
    public void updateBasicAttack(int additionalBasicAttack) {
        basicAttack += additionalBasicAttack;
    }

    /**
     * Adds points in the spell attack stat.
     * @param additionalSpellAttack Additional points, that are going to be added to spell attack stat
     */
    public void updateSpellAttack(int additionalSpellAttack) {
        spellAttack += additionalSpellAttack;
    }

    /**
     * Removes health from the health stat.
     * If armour is present, the dealt damage is lowered.
     *
     * @param dealtDamage Damage, that is going to be subtracted from the base health of the player
     */
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

    /**
     * Adds points in the health stat.
     * @param additionalHealth Additional points, that are going to be added to health stat
     */
    public void updateHealth(double additionalHealth) {
        baseHealth = RoundingCalculator.roundDecimal(baseHealth+additionalHealth);
    }

    /**
     * Gets string representation of the player stats and treasure.
     *
     * @return String representation of the player stats and treasure
     */
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
