package model;

public class CloseCombat extends Card implements CloseCombatCardsAction {
    private int inDeckPower;
    private String placeInDeck;

    public CloseCombat(model.positions positions, NameOfCards name, int power, int number) {
        super(positions, name, power, number);
    }
}