package view;

import model.Card;

import java.util.ArrayList;

public class Deck {
    private Enum Faction;
    private Enum Commander;
    private ArrayList<Card> allCards;


    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    public Enum getFaction() {
        return Faction;
    }

    public Enum getCommander() {
        return Commander;
    }

    public void setFaction(Enum faction) {
        Faction = faction;
    }
}
