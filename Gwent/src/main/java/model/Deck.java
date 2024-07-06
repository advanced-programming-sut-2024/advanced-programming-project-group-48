package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private String faction = "Monsters";
    private String commander;
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<SpecialCards> specialCards = new ArrayList<>();
    private static HashMap<String , Integer> numberOfCardsInDeckp = new HashMap<>();
    private static HashMap<String , Integer> numberOfSpecialCardsInDeck = new HashMap<>();
    static{
        for(SpecialCards i: SpecialCards.allSpecialCards){
            numberOfSpecialCardsInDeck.put(i.name , 0);
        }
        for(Card i: Card.allCards){
            numberOfCardsInDeckp.put(i.name , 0);
        }
    }

    public ArrayList<Card> getAllCards(){
        return cards;
    }
    public ArrayList<Card> getAllCards(User user){
        return user.getDeck().cards;
    }
    public String getFaction(){
            for(Card i: Card.allCards){
                numberOfCardsInDeckp.put(i.name , 0);
            }
            return faction;
    }
    public String getCommander(){
        return commander;
    }
    public String setCards(Card card){
        if(!card.faction.equals(faction)){
            return "invalid card name!";
        }
        else{
            cards.add(card);
        }
        return ("card added to deck successfully");
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public void setCommander(String commander){
        this.commander = commander;
    }

    public HashMap<String, Integer> getNumberOfCardsInDeckp() {
        return numberOfCardsInDeckp;
    }
    public HashMap<String , Integer> getNumberOfSpecialCardsInDeck() {
        return numberOfSpecialCardsInDeck;
    }

    public ArrayList<SpecialCards> getSpecialCards() {
        return specialCards;
    }
}
