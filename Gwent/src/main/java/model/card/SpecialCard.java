package model.card;

import model.Card;
import model.CardAction;

import java.util.ArrayList;

public class SpecialCard {
    public final String name;
    public final String faction;
    public final int maxNumber;
    public final String type;
    private final SpecialCardAction action;
    public static ArrayList<SpecialCard> allSpecialCards = new ArrayList<>();



    @Override
    public SpecialCard clone() {
        try {
            return (SpecialCard) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }
    static SpecialCardAction Mardroeme = new SpecialCardAction() {
        @Override
        public void execute() {
            System.out.println("Mardroeme");
        }
    };
    static SpecialCardAction NoAbility = new SpecialCardAction() {
        @Override
        public void execute() {
            System.out.println("NoAbility");
        }
    };



    //Constructor
    public SpecialCard(String name, String faction, int maxNumber, String type, SpecialCardAction action) {
        this.name = name;
        this.faction = faction;
        this.maxNumber = maxNumber;
        this.type = type;
        this.action = action;
        allSpecialCards.add(this);
    }

    static {
        new SpecialCard("Mardroeme", "Skellige", 3, "Spell", Mardroeme);
        new SpecialCard("Scorch", "All", 3, "Spell", NoAbility);
        new SpecialCard("Commander’s horn", "All", 3, "Spell", NoAbility);
        new SpecialCard("Decoy", "All", 3, "Spell", NoAbility);
        new SpecialCard("Biting Frost", "All", 3, "Weather", NoAbility);
        new SpecialCard("Impenetrable fog", "All", 3, "Weather", NoAbility);
        new SpecialCard("Torrential Rain", "All", 3, "Weather", NoAbility);
        new SpecialCard("Skellige Storm", "All", 3, "Weather", NoAbility);
        new SpecialCard("Clear Weather", "All", 3, "Weather", NoAbility);
    }

    // Method to call the passed method
    public void performAction() {
        action.execute();
    }

    public static void main(String[] args) { allSpecialCards.get(0).performAction(); }
}
