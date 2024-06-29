package model;

import java.util.ArrayList;

public class Card implements Cloneable{
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
    static CardAction NoAbility = new CardAction() {
        @Override
        public void execute() {
            // Define what you want this card to do here
        }
    };




    @Override
    public Card clone() {
        try {
            Card cloned = (Card) super.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }

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
        new Card("Menno Coehoorn", "Nilfgaardian Empire", 10, 1, "Close", true, Medic);
        new Card("Morvran Voorhis", "Nilfgaardian Empire", 10, 1, "Siege", true, NoAbility);
        new Card("Tibor Eggebracht", "Nilfgaardian Empire", 10, 1, "Ranged", true, NoAbility);
        new Card("Albrich", "Nilfgaardian Empire", 2, 1, "Ranged", false, NoAbility);
        new Card("Assire var Anahid", "Nilfgaardian Empire", 6, 1, "Ranged", false, NoAbility);
        new Card("Black Infantry Archer", "Nilfgaardian Empire", 10, 2, "Ranged", false, NoAbility);
        new Card("Cahir Mawr Dyffryn aep Ceallach", "Nilfgaardian Empire", 6, 1, "Close", false, NoAbility);
        new Card("Cynthia", "Nilfgaardian Empire", 4, 1, "Ranged", false, NoAbility);
        new Card("Etolian Auxiliary Archers", "Nilfgaardian Empire", 1, 2, "Ranged", false, Medic);
        new Card("Fringilla Vigo", "Nilfgaardian Empire", 6, 1, "Ranged", false, NoAbility);
        new Card("Geralt of Rivia", "All", 15, 1, "Close", false, NoAbility);
        new Card("Mysterious Elf", "All", 0, 1, "Close", false, Spy);
        new Card("Triss Merigold", "All", 7, 1, "Close", false, NoAbility);
        new Card("Yennefer of Vengerberg", "All", 7, 1, "Ranged", true, Medic);
        new Card("Cow", "All", 0, 1, "Ranged", false, Transformers);
        new Card("Dandelion", "All", 2, 1, "Close", false, CommandersHorn);
        new Card("Emiel Regis", "All", 5, 1, "Close", false, NoAbility);
        new Card("Gaunter O’Dimm", "All", 2, 1, "Siege", false, Muster);
        new Card("Gaunter O’Dimm Darkness", "All", 4, 3, "Ranged", false, Muster);
        new Card("Olgierd Von Everc", "All", 6, 1, "Agile", false, MoralBoost);
        new Card("Vesemir", "All", 6, 1, "Close", false, NoAbility);
        new Card("Villentretenmerth", "All", 7, 1, "Close", false, Scorch);
        new Card("Zoltan Chivay", "All", 5, 1, "Close", false, NoAbility);
        new Card("Draug", "Monsters", 10, 1, "Close", false, NoAbility);
        new Card("Imlerith", "Monsters", 10, 1, "Close", false, NoAbility);
        new Card("Kayran", "Monsters", 8, 1, "Agile", false, MoralBoost);
        new Card("Leshen", "Monsters", 10, 1, "Close", false, NoAbility);
        new Card("Arachas", "Monsters", 4, 3, "Close", false, Muster);
        new Card("Arachas Behemoth", "Monsters", 6, 1, "Siege", false, Muster);
        new Card("Botchling", "Monsters", 4, 1, "Close", false, NoAbility);
        new Card("Celaeno Harpy", "Monsters", 2, 1, "Agile", false, NoAbility);
        new Card("Cockatrice", "Monsters", 2, 1, "Ranged", false, NoAbility);
        new Card("Crone: Brewess", "Monsters", 6, 1, "Close", false, Muster);
        new Card("Crone: Weavess", "Monsters", 6, 1, "Close", false, Muster);
        new Card("Crone: Whispess", "Monsters", 6, 1, "Close", false, Muster);
        new Card("Earth Elemental", "Monsters", 6, 1, "Siege", false, NoAbility);
        new Card("Endrega", "Monsters", 2, 1, "Ranged", false, NoAbility);
        new Card("Fiend", "Monsters", 6, 1, "Close", false, NoAbility);
        new Card("Fire Elemental", "Monsters", 6, 1, "Siege", false, NoAbility);
        new Card("Foglet", "Monsters", 2, 1, "Close", false, NoAbility);
        new Card("Forktail", "Monsters", 5, 1, "Close", false, NoAbility);
        new Card("Frightener", "Monsters", 5, 1, "Close", false, NoAbility);
        new Card("Gargoyle", "Monsters", 2, 1, "Ranged", false, NoAbility);
        new Card("Ghoul", "Monsters", 1, 3, "Close", false, Muster);
        new Card("Grave Hag", "Monsters", 5, 1, "Ranged", false, NoAbility);
        new Card("Griffin", "Monsters", 5, 1, "Close", false, NoAbility);
        new Card("Harpy", "Monsters", 2, 1, "Agile", false, NoAbility);
        new Card("Ice Giant", "Monsters", 5, 1, "Siege", false, NoAbility);
        new Card("Nekker", "Monsters", 2, 3, "Close", false, Muster);
        new Card("Plague Maiden", "Monsters", 5, 1, "Close", false, NoAbility);
        new Card("Toad", "Monsters", 7, 1, "Ranged", false, Scorch);
        new Card("Vampire: Bruxa", "Monsters", 4, 1, "Close", false, Muster);
        new Card("Vampire: Ekimmara", "Monsters", 4, 1, "Close", false, Muster);
        new Card("Vampire: Fleder", "Monsters", 4, 1, "Close", false, Muster);
        new Card("Vampire: Garkain", "Monsters", 4, 1, "Close", false, Muster);
        new Card("Vampire: Katakan", "Monsters", 5, 1, "Close", false, Muster);
        new Card("Werewolf", "Monsters", 5, 1, "Close", false, NoAbility);
        new Card("Wyvern", "Monsters", 2, 1, "Ranged", false, NoAbility);
        new Card("Letho of Gulet", "Nilfgaardian Empire", 10, 1, "Close", false, NoAbility);
        new Card("Menno Coehoorn", "Nilfgaardian Empire", 10, 1, "Close", true, Medic);
        new Card("Morvran Voorhis", "Nilfgaardian Empire", 10, 1, "Siege", false, NoAbility);
        new Card("Tibor Eggebracht", "Nilfgaardian Empire", 10, 1, "Ranged", false, NoAbility);
        new Card("Albrich", "Nilfgaardian Empire", 2, 1, "Ranged", false, NoAbility);
        new Card("Assire var Anahid", "Nilfgaardian Empire", 6, 1, "Ranged", false, NoAbility);
        new Card("Black Infantry Archer", "Nilfgaardian Empire", 10, 2, "Ranged", false, NoAbility);
        new Card("Cahir Mawr Dyffryn aep Ceallach", "Nilfgaardian Empire", 6, 1, "Close", false, NoAbility);
        new Card("Cynthia", "Nilfgaardian Empire", 4, 1, "Ranged", false, NoAbility);
        new Card("Etolian Auxiliary Archers", "Nilfgaardian Empire", 1, 2, "Ranged", true, Medic);
        new Card("Fringilla Vigo", "Nilfgaardian Empire", 6, 1, "Ranged", false, NoAbility);
        new Card("Heavy Zerrikanian Fire Scorpion", "Nilfgaardian Empire", 10, 1, "Siege", false, NoAbility);
        new Card("Impera Brigade Guard", "Nilfgaardian Empire", 3, 4, "Close", false, TightBond);
        new Card("Morteisen", "Nilfgaardian Empire", 3, 1, "Close", false, NoAbility);
        new Card("Nausicaa Cavalry Rider", "Nilfgaardian Empire", 2, 3, "Close", false, TightBond);
        new Card("Puttkammer", "Nilfgaardian Empire", 3, 1, "Ranged", false, NoAbility);
        new Card("Rainfarn", "Nilfgaardian Empire", 4, 1, "Close", false, NoAbility);
        new Card("Renuald aep Matsen", "Nilfgaardian Empire", 5, 1, "Ranged", false, NoAbility);
        new Card("Rotten Mangonel", "Nilfgaardian Empire", 3, 1, "Siege", false, NoAbility);
        new Card("Shilard Fitz-Oesterlen", "Nilfgaardian Empire", 7, 1, "Close", true, Spy);
        new Card("Siege Engineer", "Nilfgaardian Empire", 6, 1, "Siege", false, NoAbility);
        new Card("Siege Technician", "Nilfgaardian Empire", 0, 1, "Siege", true, Medic);
        new Card("Stefan Skellen", "Nilfgaardian Empire", 9, 1, "Close", true, Spy);
        new Card("Sweers", "Nilfgaardian Empire", 2, 1, "Ranged", false, NoAbility);
        new Card("Vanhemar", "Nilfgaardian Empire", 4, 1, "Ranged", false, NoAbility);
        new Card("Vattier de Rideaux", "Nilfgaardian Empire", 4, 1, "Close", true, Spy);
        new Card("Vreemde", "Nilfgaardian Empire", 2, 1, "Close", false, NoAbility);
        new Card("Young Emissary", "Nilfgaardian Empire", 5, 2, "Close", false, TightBond);
        new Card("Zerrikanian Fire Scorpion", "Nilfgaardian Empire", 5, 1, "Siege", false, NoAbility);
        new Card("Esterad Thyssen", "Northern Realms", 10, 1, "Close", false, NoAbility);
        new Card("John Natalis", "Northern Realms", 10, 1, "Close", false, NoAbility);
        new Card("Philippa Eilhart", "Northern Realms", 10, 1, "Ranged", false, NoAbility);
        new Card("Vernon Roche", "Northern Realms", 10, 1, "Close", false, NoAbility);
        new Card("Ballista", "Northern Realms", 6, 2, "Siege", false, NoAbility);
        new Card("Blue Stripes Commando", "Northern Realms", 4, 3, "Close", false, TightBond);
        new Card("Catapult", "Northern Realms", 8, 2, "Siege", false, TightBond);
        new Card("Dethmold", "Northern Realms", 6, 1, "Ranged", false, NoAbility);
        new Card("Dragon Hunter", "Northern Realms", 5, 3, "Ranged", false, TightBond);
        new Card("Dun Banner Medic", "Northern Realms", 5, 1, "Siege", true, Medic);
        new Card("Kaedweni Siege Expert", "Northern Realms", 1, 3, "Siege", false, MoralBoost);
        new Card("Keira Metz", "Northern Realms", 5, 1, "Ranged", false, NoAbility);
        new Card("Poor Fucking Infantry", "Northern Realms", 1, 4, "Close", false, TightBond);
        new Card("Prince Stennis", "Northern Realms", 5, 1, "Close", true, Spy);
        new Card("Redanian Foot Soldier", "Northern Realms", 1, 2, "Close", false, NoAbility);
        new Card("Sabrina Glevissing", "Northern Realms", 4, 1, "Ranged", false, NoAbility);
        new Card("Sheldon Skaggs", "Northern Realms", 4, 1, "Ranged", false, NoAbility);
        new Card("Siege Tower", "Northern Realms", 6, 1, "Siege", false, NoAbility);
        new Card("Siegfried of Denesle", "Northern Realms", 5, 1, "Close", false, NoAbility);
        new Card("Sigismund Dijkstra", "Northern Realms", 4, 1, "Close", true, Spy);
        new Card("Síle de Tansarville", "Northern Realms", 5, 1, "Ranged", false, NoAbility);
        new Card("Thaler", "Northern Realms", 1, 1, "Siege", true, Spy);
        new Card("Trebuchet", "Northern Realms", 6, 2, "Siege", false, NoAbility);
        new Card("Ves", "Northern Realms", 5, 1, "Close", false, NoAbility);
        new Card("Yarpen Zirgrin", "Northern Realms", 2, 1, "Close", false, NoAbility);
        new Card("Eithne", "Scoia'tael", 10, 1, "Ranged", false, NoAbility);
        new Card("Iorveth", "Scoia'tael", 10, 1, "Ranged", false, NoAbility);
        new Card("Seasenthessis", "Scoia'tael", 10, 1, "Ranged", false, NoAbility);
        new Card("Barclay Els", "Scoia'tael", 6, 1, "Agile", false, NoAbility);
        new Card("Ciaran aep", "Scoia'tael", 3, 1, "Agile", false, NoAbility);
        new Card("Dennis Cranmer", "Scoia'tael", 6, 1, "Close", false, NoAbility);
        new Card("Dol Blathanna Archer", "Scoia'tael", 4, 1, "Ranged", false, NoAbility);
        new Card("Dol Blathanna Scout", "Scoia'tael", 6, 3, "Agile", false, NoAbility);
        new Card("Dwarven Skirmisher", "Scoia'tael", 3, 3, "Close", false, Muster);
        new Card("Elven Skirmisher", "Scoia'tael", 2, 3, "Ranged", false, Muster);
        new Card("Filavandrel", "Scoia'tael", 6, 1, "Agile", false, NoAbility);
        new Card("Havekar Healer", "Scoia'tael", 0, 3, "Ranged", true, Medic);
        new Card("Havekar Smuggler", "Scoia'tael", 5, 3, "Close", false, Muster);
        new Card("Ida Emean aep", "Scoia'tael", 6, 1, "Ranged", false, NoAbility);
        new Card("Isengrim Faoiltiarna", "Scoia'tael", 10, 1, "Close", false, MoralBoost);
        new Card("Mahakaman Defender", "Scoia'tael", 5, 5, "Close", false, NoAbility);
        new Card("Milva", "Scoia'tael", 10, 1, "Ranged", false, MoralBoost);
        new Card("Riordain", "Scoia'tael", 1, 1, "Ranged", false, NoAbility);
        new Card("Schirru", "Scoia'tael", 8, 1, "Siege", false, Scorch);
        new Card("Toruviel", "Scoia'tael", 2, 1, "Ranged", false, NoAbility);
        new Card("Vrihedd Brigade Recruit", "Scoia'tael", 4, 1, "Ranged", false, NoAbility);
        new Card("Vrihedd Brigade Veteran", "Scoia'tael", 5, 2, "Agile", false, NoAbility);
        new Card("Yaevinn", "Scoia'tael", 6, 1, "Agile", false, NoAbility);
        new Card("Cerys", "Skellige", 10, 1, "Close", false, Muster);
        new Card("Ermion", "Skellige", 8, 1, "Ranged", false, Mardroeme);
        new Card("Hjalmar", "Skellige", 10, 1, "Ranged", false, NoAbility);
        new Card("Kambi", "Skellige", 11, 1, "Close", false, Transformers);
        new Card("Berserker", "Skellige", 4, 1, "Close", false, Berserker);
        new Card("Birna Bran", "Skellige", 2, 1, "Close", false, Medic);
        new Card("Blueboy Lugos", "Skellige", 6, 1, "Close", false, NoAbility);
        new Card("Clan An Craite", "Skellige", 6, 3, "Close", false, TightBond);
        new Card("Clan Brokvar Archer", "Skellige", 6, 3, "Ranged", false, NoAbility);
        new Card("Clan Dimun Pirate", "Skellige", 6, 1, "Ranged", false, Scorch);
        new Card("Clan Drummond Shieldmaiden", "Skellige", 4, 3, "Close", false, TightBond);
        new Card("Clan Tordarroch Armorsmith", "Skellige", 4, 1, "Close", false, NoAbility);
        new Card("Donar an Hindar", "Skellige", 4, 1, "Close", false, NoAbility);
        new Card("Draig Bon-Dhu", "Skellige", 2, 1, "Siege", false, CommandersHorn);
        new Card("Holger Blackhand", "Skellige", 4, 1, "Siege", false, NoAbility);
        new Card("Light Longship", "Skellige", 4, 3, "Ranged", false, Muster);
        new Card("Madman Lugos", "Skellige", 6, 1, "Close", false, NoAbility);
        new Card("Olaf", "Skellige", 12, 1, "Agile", false, MoralBoost);
        new Card("Svanrige", "Skellige", 4, 1, "Close", false, NoAbility);
        new Card("Udalryk", "Skellige", 4, 1, "Close", false, NoAbility);
        new Card("Vidkaarl", "Skellige", 14, 0, "Close", false, MoralBoost);
        new Card("War Longship", "Skellige", 6, 3, "Siege", false, TightBond);
        new Card("Young Berserker", "Skellige", 2, 3, "Ranged", true, Berserker);
        new Card("Young Vidkaarl", "Skellige", 8, 0, "Ranged", false, TightBond);
    }
    // Method to call the passed method
    public void performAction() {
        action.execute();
    }

}




