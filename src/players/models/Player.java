package players.models;

public abstract class Player {
    private int baseHealth = 50;
    private int basicAttack;
    private int spellAttack;

    public Player(int basicAttack, int spellAttack)
    {
        this.basicAttack = basicAttack;
        this.spellAttack = spellAttack;
    }

    public int getCurrentHealth() {
        return baseHealth;
    }

    public int getBasicAttack() {
        return basicAttack;
    }


    public int getSpellAttack() {
        return spellAttack;
    }


    public void updateBasicAttack(int additionalBasicAttack) {
        basicAttack += additionalBasicAttack;
    }


    public void updateSpellAttack(int additionalSpellAttack) {
        spellAttack += additionalSpellAttack;
    }


    public void updateHealth(int differentialHealth) {
        baseHealth += differentialHealth;
    }


    public String toString() {
        return "- Health: "+baseHealth+"\n" +
                "- Basic attack: "+basicAttack+"\n" +
                "- Spell attack: "+spellAttack;
    }
}
