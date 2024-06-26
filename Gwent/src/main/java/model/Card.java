package model;

public abstract class Card {
    private final positions positions;
    private final NameOfCards name;
    private int power;
    private final int number;

    public Card(positions positions, NameOfCards name, int power, int number) {
        this.positions = positions;
        this.name = name;
        this.power = power;
        this.number = number;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public positions getPositions() {
        return positions;
    }

    public String getName() {
        return name.toString();
    }

    public int getPower() {
        return power;
    }

    public int getNumber() {
        return number;
    }
}
