package model.card;

public class Ranged extends Card implements RangedCardActions{
    private int inDeckPower;
    private String placeInDeck;

    public Ranged(model.card.positions positions, NameOfCards name, int power, int number, String faction) {
        super(positions, name, power, number , faction);
    }
}
