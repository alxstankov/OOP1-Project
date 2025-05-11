package treasures.models;

import treasures.enums.TreasureType;

public class Spell extends Treasure {
    public Spell(double stat) {
        super(TreasureType.SPELL, stat);
    }

    @Override
    public String toString() {
        return "Spell:\n" +
                "- Spell boost: "+getStat()*100+"%";
    }
}
