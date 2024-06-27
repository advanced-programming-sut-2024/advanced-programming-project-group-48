package model.card;

public class Spell extends Card implements SpellAction {
    public Spell(model.card.positions positions, NameOfCards name, int power, int number , String faction) {
        super(positions, name, power, number,faction);
    }
}
