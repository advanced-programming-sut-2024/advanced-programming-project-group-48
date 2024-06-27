package model;

import java.util.ArrayList;

public class Card {
    public final String name;
    public final String faction;
    public final int power;
    public final int maxNumber;
    public final String type;
    public final boolean isHero;
    private final CardAction action; // The method to be passed
    public static ArrayList<Card> allCards = new ArrayList<>();

    static CardAction Medic = new CardAction() {
        @Override
        public void execute() {
            // Define what you want this card to do here
            System.out.println("Medic!");
        }
    };
    static CardAction CommandersHorn = new CardAction() {
        @Override
        public void execute() {
            // Define what you want this card to do here
            System.out.println("CommandersHorn!");
        }
    };
    static CardAction Decoy = new CardAction() {
        @Override
        public void execute() {
            // Define what you want this card to do here
            System.out.println("Decoy!");
        }
    };
    static CardAction MoralBoost = new CardAction() {
        @Override
        public void execute() {
            // Define what you want this card to do here
            System.out.println("MoralBoost!");
        }
    };
    static CardAction Muster = new CardAction() {
        @Override
        public void execute() {
            // Define what you want this card to do here
            System.out.println("Muster!");
        }
    };
    static CardAction Spy = new CardAction() {
        @Override
        public void execute() {
            // Define what you want this card to do here
            System.out.println("Spy!");
        }
    };
    static CardAction TightBond = new CardAction() {
        @Override
        public void execute() {
            // Define what you want this card to do here
            System.out.println("TightBond");
        }
    };
    static CardAction Scorch = new CardAction() {
        @Override
        public void execute() {
            // Define what you want this card to do here
            System.out.println("Scorch!");
        }
    };
    static CardAction Berserker = new CardAction() {
        @Override
        public void execute() {
            // Define what you want this card to do here
            System.out.println("Berserker!");
        }
    };
    static CardAction Mardroeme = new CardAction() {
        @Override
        public void execute() {
            // Define what you want this card to do here
            System.out.println("Mardroeme!");
        }
    };
    static CardAction Transformers = new CardAction() {
        @Override
        public void execute() {
            // Define what you want this card to do here
            System.out.println("Transformers!");
        }
    };













    // Constructor
    public Card(String name, String faction, int power, int maxNumber, String type, boolean isHero, CardAction action) {
        this.name = name;
        this.faction = faction;
        this.power = power;
        this.maxNumber = maxNumber;
        this.type = type;
        this.isHero = isHero;
        this.action = action;
        allCards.add(this);
    }

    static {
        new Card("mamad","mamadi",0,2,"mamadian",true,Medic);
    }
    // Method to call the passed method
    public void performAction() {
        action.execute();
    }

    public static void main(String[] args) {
        allCards.get(0).performAction();
    }

}




