package model;

import model.card.Card;
import model.card.Faction;

import java.util.ArrayList;

public class Deck {
    private String faction;
    private String commander;
    private ArrayList<Card> cards;
    public ArrayList<Card> getAllCards(){
        return cards;
    }
    public String getFaction(){
        return faction;
    }
    public String getCommander(){
        return commander;
    }
    public String setCards(Card card){
        if(!card.getFaction().equals(faction)){
            return "invalid card name!";
        }
        else{
            cards.add(card);
        }
        return ("card added to deck successfully");
    }

    public String setFaction(String faction) {
        if(!Faction.getFaction().containsKey(faction)){
            return "invalid faction name";
        }
        else{
            this.faction = faction;
        }
        return "The realm was successfully selected";
    }

    public String setCommander(String commander){
        if(!Faction.getFaction().get(faction).contains(commander)){
            return "invalid commander";
        }
        else{
            this.commander = commander;
        }
        return "The commander was successfully selected";
    }
}
