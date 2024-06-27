package model.card;

public class CloseCombat extends Card implements CloseCombatCardsAction {
    private int inDeckPower;
    private String placeInDeck;

    public CloseCombat(model.card.positions positions, NameOfCards name, int power, int number, String faction) {
        super(positions, name, power, number, faction);
    }
}
