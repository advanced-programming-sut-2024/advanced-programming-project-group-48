package model.card;

public class Card {

    private final positions positions;
    private final NameOfCards name;
    private int power;
    private String faction;


    public Card(positions positions, NameOfCards name, int power, int number, String faction) {
        this.positions = positions;
        this.name = name;
        this.power = power;
        this.faction = faction;
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

    public String getFaction(){
        return faction;
    }
}
