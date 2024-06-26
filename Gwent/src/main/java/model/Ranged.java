package model;

public class Ranged extends Card implements RangedCardActions{
    private int inDeckPower;
    private String placeInDeck;

    public Ranged(model.positions positions, NameOfCards name, int power, int number) {
        super(positions, name, power, number);
    }
}
