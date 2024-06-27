package model;

public class Spell extends Card implements SpellAction{
    public Spell(model.positions positions, NameOfCards name, int power, int number , String faction) {
        super(positions, name, power, number,faction);
    }
}
