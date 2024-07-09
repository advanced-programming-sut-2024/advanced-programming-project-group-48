package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private String faction;
    private String commander;
    private final ArrayList<Card> cards = new ArrayList<>();
    private final HashMap<String, Integer> numberOfCardsInDeck = new HashMap<>();

    public ArrayList<Card> getAllCards() {
        return cards;
    }

    public String getFaction() {
        for (Card i : Card.allCards) {
            numberOfCardsInDeck.put(i.name, 0);
        }
        return faction;
    }

    public String getCommander() {
        return commander;
    }

    public boolean addCard(String cardName, int count) {
        Card card = Card.getCardByName(cardName);
        if (card == null) return false;
        // Check if the card can be added without exceeding the max number allowed
        int currentCount = numberOfCardsInDeck.getOrDefault(cardName, 0);

        if (currentCount + count > card.maxNumber)
            return false; // Check if adding the new cards exceeds the max allowed

        // Update the count for the card
        numberOfCardsInDeck.put(cardName, currentCount + count);
        for (int i = 0; i < count; i++) {
            cards.add(card);
        }
        return true; // Return true to indicate the card was successfully added
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public void setCommander(String commander) {
        this.commander = commander;
    }

    public String showDeck() {
        StringBuilder deck = new StringBuilder();
        if(faction == null||faction.isEmpty())
            deck.append("You dont have Faction").append("\n");

        else deck.append("Faction: ").append(this.faction).append("\n");
        if(commander == null||commander.isEmpty())
            deck.append("You dont have Commander").append("\n");
        else deck.append("Commander: ").append(this.commander).append("\n");

        deck.append("Cards : ").append("\n");
        for (Card i : cards) {
            deck.append(i.name).append("\n");
        }
        return deck.toString();
    }
}
