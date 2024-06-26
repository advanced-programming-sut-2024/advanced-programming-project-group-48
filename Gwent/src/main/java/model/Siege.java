package model;

public class Siege extends Card implements SiegeCardsAction{
    private int inDeckPower;
    private String placeInDeck;

    public Siege(model.positions positions, NameOfCards name, int power, int number) {
        super(positions, name, power, number);
    }
}
