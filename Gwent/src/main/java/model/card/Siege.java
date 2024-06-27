package model.card;

public class Siege extends Card implements SiegeCardsAction{
    private int inDeckPower;
    private String placeInDeck;

    public Siege(model.card.positions positions, NameOfCards name, int power, int number, String faction) {
        super(positions, name, power, number , faction);
    }
}
