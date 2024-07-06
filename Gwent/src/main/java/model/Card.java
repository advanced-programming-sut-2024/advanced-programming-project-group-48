package model;


import java.util.*;

public class Card implements Cloneable {
    public String name;
    public final String faction;
    public int power;
    public final int maxNumber;
    public final String type;
    public final boolean isHero;
    private final CardAction action; // The method to be passed
    public static ArrayList<Card> allCards = new ArrayList<>();
    public static ArrayList<Card> discardPile = new ArrayList<>();
    public String ability;


    @Override
    public Card clone() {
        try {
            return (Card) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }


    static CardAction Medic = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.inHandCards[i] == null){
                    gameEnvironment.inHandCards[i] = gameEnvironment.discardPile.get(0);
                    break;
                }
            }
        }
    };
    static CardAction CommandersHorn = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
            if(gameEnvironment.recentPlaceCardRow==1){
                for (int i = 0; i < 10; i++) {
                    if (!(gameEnvironment.closedRow[i] == null))
                        gameEnvironment.closedRow[i].power *= 2;
                }
            }
            if(gameEnvironment.recentPlaceCardRow==2){
                for (int i = 0; i < 10; i++) {
                    if (!(gameEnvironment.rangedRow[i] == null))
                        gameEnvironment.rangedRow[i].power *= 2;
                }
            }
            if(gameEnvironment.recentPlaceCardRow==3){
                for (int i = 0; i < 10; i++) {
                    if (!(gameEnvironment.siegeRow[i] == null))
                        gameEnvironment.siegeRow[i].power *= 2;
                }
            }
        }
    };
    static CardAction MoralBoost = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
            if(gameEnvironment.recentPlaceCardRow==1){
                for (int i = 0; i < 10; i++) {
                    if (!(gameEnvironment.closedRow[i] == null))
                        gameEnvironment.closedRow[i].power += 1;
                }
            }
            if(gameEnvironment.recentPlaceCardRow==2){
                for (int i = 0; i < 10; i++) {
                    if (!(gameEnvironment.rangedRow[i] == null))
                        gameEnvironment.rangedRow[i].power += 1;
                }
            }
            if(gameEnvironment.recentPlaceCardRow==3){
                for (int i = 0; i < 10; i++) {
                    if (!(gameEnvironment.siegeRow[i] == null))
                        gameEnvironment.siegeRow[i].power += 1;
                }
            }
        }
    };
    static CardAction Muster = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
            for (int i = 0; i < 10; i++) {
                if(gameEnvironment.inHandCards[i]!=null){
                    playCard(gameEnvironment.inHandCards[i],gameEnvironment);
                    gameEnvironment.inHandCards[i]=null;
                }
            }
        }
    };

    private static void playCard(Card card, GameEnvironment gameEnvironment) {
        if(card.type.equals("Close")||card.type.equals("Agile")){
            for (int i = 0; i < 10; i++) {
                if(gameEnvironment.closedRow[i]==null){
                    gameEnvironment.closedRow[i]=card;
                    card.performAction(gameEnvironment);
                    break;
                }
            }
        }
        if(card.type.equals("Ranged")){
            for (int i = 0; i < 10; i++) {
                if(gameEnvironment.rangedRow[i]==null){
                    gameEnvironment.rangedRow[i]=card;
                    card.performAction(gameEnvironment);
                    break;
                }
            }
        }
        if(card.type.equals("Siege")){
            for (int i = 0; i < 10; i++) {
                if(gameEnvironment.siegeRow[i]==null){
                    gameEnvironment.siegeRow[i]=card;
                    card.performAction(gameEnvironment);
                    break;
                }
            }
        }
    }

    static CardAction Spy = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {


        }
    };
    public void drawRandomCards(List<Card> allCards, Card[] inHandCards, int numberOfCards) {
        Collections.shuffle(allCards); // Randomize the order of all cards
        int cardsToAdd = Math.min(numberOfCards, allCards.size()); // Determine the number of cards to add
        int inHandCount = 0; // Counter for the number of cards currently in hand

        // Find out how many cards are already in hand
        for (Card card : inHandCards) {
            if (card != null) {
                inHandCount++;
            }
        }

        // Add cards to the inHandCards array until it's full or all cards to add are added
        for (int i = 0; i < cardsToAdd && inHandCount < inHandCards.length; i++, inHandCount++) {
            inHandCards[inHandCount] = allCards.get(i); // Add card to the next available slot in the array
        }

        // Remove the added cards from the original deck
        for (int i = 0; i < cardsToAdd; i++) {
            allCards.remove(0);
        }
    }
    static CardAction TightBond = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
                if(gameEnvironment.recentPlaceCardRow==1)
                    applyTightBond(gameEnvironment.closedRow);
                if(gameEnvironment.recentPlaceCardRow==2)
                    applyTightBond(gameEnvironment.rangedRow);
                if(gameEnvironment.recentPlaceCardRow==3)
                    applyTightBond(gameEnvironment.siegeRow);

    };
        private void applyTightBond(Card[] row) {
            // Create a map to count cards with the same power and ability
            HashMap<Integer, Integer> cardCountMap = new HashMap<>();
            for (Card card : row) {
                if (card != null && "TightBond".equals(card.ability)) {
                    cardCountMap.put(card.power, cardCountMap.getOrDefault(card.power, 0) + 1);
                }
            }

            // Apply the Tight Bond effect
            for (Card card : row) {
                if (card != null && "TightBond".equals(card.ability)) {
                    int count = cardCountMap.get(card.power);
                    card.power *= count;
                }
            }
        }
    };

    static CardAction Scorch = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
            int sumEnemyNoneHeroCards=0;
            for (int i = 0; i < 10; i++) {
                if(gameEnvironment.enemySiegeRow[i]!=null&&!gameEnvironment.enemySiegeRow[i].isHero){
                    sumEnemyNoneHeroCards+=gameEnvironment.enemySiegeRow[i].power;
                }
            }
            if(sumEnemyNoneHeroCards>=10){
                int indexStrongestCard=-1;
                int maxPower=0;
                for (int i = 0; i < 10; i++) {
                    if(gameEnvironment.enemySiegeRow[i]!=null&&!gameEnvironment.enemySiegeRow[i].isHero){
                        if(gameEnvironment.enemySiegeRow[i].power>maxPower){
                            maxPower=gameEnvironment.enemySiegeRow[i].power;
                            indexStrongestCard=i;
                        }
                    }
                }
                gameEnvironment.enemySiegeRow[indexStrongestCard]=null;
            }
        }
    };
    static CardAction Berserker = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
//          Dose nothing
        }
    };
    static CardAction Mardroeme = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
            if(gameEnvironment.recentPlaceCardRow==1){
                for (int i = 0; i < 10; i++) {
                    if (gameEnvironment.closedRow[i] != null&&gameEnvironment.closedRow[i].type.equals("Berserker")){
                        gameEnvironment.closedRow[i]= Objects.requireNonNull(Card.getCardByName("YoungVidkaarl")).clone();
                    }
                }
            }
            if(gameEnvironment.recentPlaceCardRow==2){
                for (int i = 0; i < 10; i++) {
                    if (gameEnvironment.rangedRow[i] != null&&gameEnvironment.rangedRow[i].type.equals("Berserker")){
                        gameEnvironment.rangedRow[i]= Objects.requireNonNull(Card.getCardByName("YoungVidkaarl")).clone();
                    }
                }
            }
            if(gameEnvironment.recentPlaceCardRow==3){
                for (int i = 0; i < 10; i++) {
                    if (gameEnvironment.siegeRow[i] != null&&gameEnvironment.siegeRow[i].type.equals("Berserker")){
                        gameEnvironment.siegeRow[i]= Objects.requireNonNull(Card.getCardByName("YoungVidkaarl")).clone();
                    }
                }
            }
        }

    };
    static CardAction Transformers = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {
            //handle this in the GameEnvironmentController
        }

    };
    static CardAction NoAbility = new CardAction() {
        @Override
        public void execute(GameEnvironment gameEnvironment) {

        }

    };

    public Card(String name, String faction, int power, int maxNumber, String type, boolean isHero, CardAction action, String ability) {
        this.name = name;
        this.faction = faction;
        this.power = power;
        this.maxNumber = maxNumber;
        this.type = type;
        this.isHero = isHero;
        this.action = action;
        allCards.add(this);
        this.ability = ability;
//        if(this.faction.equals("Nilfgaardian Empire") || this.faction.equals("All") )
//            Faction.getCardsOfNilfgaardianEmpire().add(this.name);
//        else if(this.faction.equals("Monsters") || this.faction.equals("All"))
//            Faction.getCardsOfMonsters().add(this.name);
//        else if(this.faction.equals("Northern Realms") || this.faction.equals("All"))
//            Faction.getCardsOfNorthenRealms().add(this.name);
//        else if(this.faction.equals("Scoia'tael") || this.faction.equals("All"))
//            Faction.getCardsOfScoiaTaell().add(this.name);
//        else if(this.faction.equals("Skellige") || this.faction.equals("All"))
//            Faction.getCardsOfSkellige().add(this.name);
    }

    static {
        new Card("MennoCoehoorn", "NilfgaardianEmpire", 10, 1, "Close", true, Medic, "Medic");
        new Card("MorvranVoorhis", "NilfgaardianEmpire", 10, 1, "Siege", true, NoAbility, "NoAbility");
        new Card("TiborEggebracht", "NilfgaardianEmpire", 10, 1, "Ranged", true, NoAbility, "NoAbility");
        new Card("Albrich", "NilfgaardianEmpire", 2, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("AssirevarAnahid", "NilfgaardianEmpire", 6, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("BlackInfantryArcher", "NilfgaardianEmpire", 10, 2, "Ranged", false, NoAbility, "NoAbility");
        new Card("CahirMawrDyffrynaepCeallach", "NilfgaardianEmpire", 6, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Cynthia", "NilfgaardianEmpire", 4, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("EtolianAuxiliaryArchers", "NilfgaardianEmpire", 1, 2, "Ranged", false, Medic, "Medic");
        new Card("FringillaVigo", "NilfgaardianEmpire", 6, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("LethoOfGulet", "NilfgaardianEmpire", 10, 1, "Close", false, NoAbility, "NoAbility");
        new Card("HeavyZerrikanianFireScorpion", "NilfgaardianEmpire", 10, 1, "Siege", false, NoAbility, "NoAbility");
        new Card("ImperaBrigadeGuard", "NilfgaardianEmpire", 3, 4, "Close", false, TightBond, "TightBond");
        new Card("Morteisen", "NilfgaardianEmpire", 3, 1, "Close", false, NoAbility, "NoAbility");
        new Card("NausicaaCavalryRider", "NilfgaardianEmpire", 2, 3, "Close", false, TightBond, "TightBond");
        new Card("Puttkammer", "NilfgaardianEmpire", 3, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("Rainfarn", "NilfgaardianEmpire", 4, 1, "Close", false, NoAbility, "NoAbility");
        new Card("RenualdAepMatsen", "NilfgaardianEmpire", 5, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("RottenMangonel", "NilfgaardianEmpire", 3, 1, "Siege", false, NoAbility, "NoAbility");
        new Card("ShilardFitz-Oesterlen", "NilfgaardianEmpire", 7, 1, "Close", true, Spy, "Spy");
        new Card("SiegeEngineer", "NilfgaardianEmpire", 6, 1, "Siege", false, NoAbility, "NoAbility");
        new Card("SiegeTechnician", "NilfgaardianEmpire", 0, 1, "Siege", true, Medic, "Medic");
        new Card("StefanSkellen", "NilfgaardianEmpire", 9, 1, "Close", true, Spy, "Spy");
        new Card("Sweers", "NilfgaardianEmpire", 2, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("Vanhemar", "NilfgaardianEmpire", 4, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("VattierDeRideaux", "NilfgaardianEmpire", 4, 1, "Close", true, Spy, "Spy");
        new Card("Vreemde", "NilfgaardianEmpire", 2, 1, "Close", false, NoAbility, "NoAbility");
        new Card("YoungEmissary", "NilfgaardianEmpire", 5, 2, "Close", false, TightBond, "TightBond");
        new Card("ZerrikanianFireScorpion", "NilfgaardianEmpire", 5, 1, "Siege", false, NoAbility, "NoAbility");
        new Card("GeraltOfRivia", "All", 15, 1, "Close", false, NoAbility, "NoAbility");
        new Card("MysteriousElf", "All", 0, 1, "Close", false, Spy, "Spy");
        new Card("TrissMerigold", "All", 7, 1, "Close", false, NoAbility, "NoAbility");
        new Card("YenneferOfVengerberg", "All", 7, 1, "Ranged", true, Medic, "Medic");
        new Card("Cow", "All", 0, 1, "Ranged", false, Transformers, "Transformers");
        new Card("Dandelion", "All", 2, 1, "Close", false, CommandersHorn, "CommandersHorn");
        new Card("EmielRegis", "All", 5, 1, "Close", false, NoAbility, "NoAbility");
        new Card("GaunterO’Dimm", "All", 2, 1, "Siege", false, Muster, "Muster");
        new Card("GaunterO’DimmDarkness", "All", 4, 3, "Ranged", false, Muster, "Muster");
        new Card("OlgierdVonEverc", "All", 6, 1, "Agile", false, MoralBoost, "MoralBoost");
        new Card("Vesemir", "All", 6, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Villentretenmerth", "All", 7, 1, "Close", false, Scorch, "Scorch");
        new Card("ZoltanChivay", "All", 5, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Draug", "Monsters", 10, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Imlerith", "Monsters", 10, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Kayran", "Monsters", 8, 1, "Agile", false, MoralBoost, "MoralBoost");
        new Card("Leshen", "Monsters", 10, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Arachas", "Monsters", 4, 3, "Close", false, Muster, "Muster");
        new Card("ArachasBehemoth", "Monsters", 6, 1, "Siege", false, Muster, "Muster");
        new Card("Botchling", "Monsters", 4, 1, "Close", false, NoAbility, "NoAbility");
        new Card("CelaenoHarpy", "Monsters", 2, 1, "Agile", false, NoAbility, "NoAbility");
        new Card("Cockatrice", "Monsters", 2, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("Crone:Brewess", "Monsters", 6, 1, "Close", false, Muster, "Muster");
        new Card("Crone:Weavess", "Monsters", 6, 1, "Close", false, Muster, "Muster");
        new Card("Crone:Whispess", "Monsters", 6, 1, "Close", false, Muster, "Muster");
        new Card("EarthElemental", "Monsters", 6, 1, "Siege", false, NoAbility, "NoAbility");
        new Card("Endrega", "Monsters", 2, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("Fiend", "Monsters", 6, 1, "Close", false, NoAbility, "NoAbility");
        new Card("FireElemental", "Monsters", 6, 1, "Siege", false, NoAbility, "NoAbility");
        new Card("Foglet", "Monsters", 2, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Forktail", "Monsters", 5, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Frightener", "Monsters", 5, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Gargoyle", "Monsters", 2, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("Ghoul", "Monsters", 1, 3, "Close", false, Muster, "Muster");
        new Card("GraveHag", "Monsters", 5, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("Griffin", "Monsters", 5, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Harpy", "Monsters", 2, 1, "Agile", false, NoAbility, "NoAbility");
        new Card("IceGiant", "Monsters", 5, 1, "Siege", false, NoAbility, "NoAbility");
        new Card("Nekker", "Monsters", 2, 3, "Close", false, Muster, "Muster");
        new Card("PlagueMaiden", "Monsters", 5, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Toad", "Monsters", 7, 1, "Ranged", false, Scorch, "Scorch");
        new Card("Vampire:Bruxa", "Monsters", 4, 1, "Close", false, Muster, "Muster");
        new Card("Vampire:Ekimmara", "Monsters", 4, 1, "Close", false, Muster, "Muster");
        new Card("Vampire:Fleder", "Monsters", 4, 1, "Close", false, Muster, "Muster");
        new Card("Vampire:Garkain", "Monsters", 4, 1, "Close", false, Muster, "Muster");
        new Card("Vampire:Katakan", "Monsters", 5, 1, "Close", false, Muster, "Muster");
        new Card("Werewolf", "Monsters", 5, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Wyvern", "Monsters", 2, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("EsteradThyssen", "NorthernRealms", 10, 1, "Close", false, NoAbility, "NoAbility");
        new Card("JohnNatalis", "NorthernRealms", 10, 1, "Close", false, NoAbility, "NoAbility");
        new Card("PhilippaEilhart", "NorthernRealms", 10, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("VernonRoche", "NorthernRealms", 10, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Ballista", "NorthernRealms", 6, 2, "Siege", false, NoAbility, "NoAbility");
        new Card("BlueStripesCommando", "NorthernRealms", 4, 3, "Close", false, TightBond, "TightBond");
        new Card("Catapult", "NorthernRealms", 8, 2, "Siege", false, TightBond, "TightBond");
        new Card("Dethmold", "NorthernRealms", 6, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("DragonHunter", "NorthernRealms", 5, 3, "Ranged", false, TightBond, "TightBond");
        new Card("DunBannerMedic", "NorthernRealms", 5, 1, "Siege", true, Medic, "Medic");
        new Card("KaedweniSiegeExpert", "NorthernRealms", 1, 3, "Siege", false, MoralBoost, "MoralBoost");
        new Card("KeiraMetz", "NorthernRealms", 5, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("PoorFuckingInfantry", "NorthernRealms", 1, 4, "Close", false, TightBond, "TightBond");
        new Card("PrinceStennis", "NorthernRealms", 5, 1, "Close", true, Spy, "Spy");
        new Card("RedanianFootSoldier", "NorthernRealms", 1, 2, "Close", false, NoAbility, "NoAbility");
        new Card("SabrinaGlevissing", "NorthernRealms", 4, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("SheldonSkaggs", "NorthernRealms", 4, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("SiegeTower", "NorthernRealms", 6, 1, "Siege", false, NoAbility, "NoAbility");
        new Card("SiegfriedOfDenesle", "NorthernRealms", 5, 1, "Close", false, NoAbility, "NoAbility");
        new Card("SigismundDijkstra", "NorthernRealms", 4, 1, "Close", true, Spy, "Spy");
        new Card("SíleDeTansarville", "NorthernRealms", 5, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("Thaler", "NorthernRealms", 1, 1, "Siege", true, Spy, "Spy");
        new Card("Trebuchet", "NorthernRealms", 6, 2, "Siege", false, NoAbility, "NoAbility");
        new Card("Ves", "NorthernRealms", 5, 1, "Close", false, NoAbility, "NoAbility");
        new Card("YarpenZirgrin", "NorthernRealms", 2, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Eithne", "Scoia'tael", 10, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("Iorveth", "Scoia'tael", 10, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("Seasenthessis", "Scoia'tael", 10, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("BarclayEls", "Scoia'tael", 6, 1, "Agile", false, NoAbility, "NoAbility");
        new Card("CiaranAep", "Scoia'tael", 3, 1, "Agile", false, NoAbility, "NoAbility");
        new Card("DennisCranmer", "Scoia'tael", 6, 1, "Close", false, NoAbility, "NoAbility");
        new Card("DolBlathannaArcher", "Scoia'tael", 4, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("DolBlathannaScout", "Scoia'tael", 6, 3, "Agile", false, NoAbility, "NoAbility");
        new Card("DwarvenSkirmisher", "Scoia'tael", 3, 3, "Close", false, Muster, "Muster");
        new Card("ElvenSkirmisher", "Scoia'tael", 2, 3, "Ranged", false, Muster, "Muster");
        new Card("Filavandrel", "Scoia'tael", 6, 1, "Agile", false, NoAbility, "NoAbility");
        new Card("HavekarHealer", "Scoia'tael", 0, 3, "Ranged", true, Medic, "Medic");
        new Card("HavekarSmuggler", "Scoia'tael", 5, 3, "Close", false, Muster, "Muster");
        new Card("IdaEmeanAep", "Scoia'tael", 6, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("IsengrimFaoiltiarna", "Scoia'tael", 10, 1, "Close", false, MoralBoost, "MoralBoost");
        new Card("MahakamanDefender", "Scoia'tael", 5, 5, "Close", false, NoAbility, "NoAbility");
        new Card("Milva", "Scoia'tael", 10, 1, "Ranged", false, MoralBoost, "MoralBoost");
        new Card("Riordain", "Scoia'tael", 1, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("Schirru", "Scoia'tael", 8, 1, "Siege", false, Scorch, "Scorch");
        new Card("Toruviel", "Scoia'tael", 2, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("VriheddBrigadeRecruit", "Scoia'tael", 4, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("VriheddBrigadeVeteran", "Scoia'tael", 5, 2, "Agile", false, NoAbility, "NoAbility");
        new Card("Yaevinn", "Scoia'tael", 6, 1, "Agile", false, NoAbility, "NoAbility");
        new Card("Cerys", "Skellige", 10, 1, "Close", false, Muster, "Muster");
        new Card("Ermion", "Skellige", 8, 1, "Ranged", false, Mardroeme, "Mardroeme");
        new Card("Hjalmar", "Skellige", 10, 1, "Ranged", false, NoAbility, "NoAbility");
        new Card("Kambi", "Skellige", 11, 1, "Close", false, Transformers, "Transformers");
        new Card("Berserker", "Skellige", 4, 1, "Close", false, Berserker, "Berserker");
        new Card("BirnaBran", "Skellige", 2, 1, "Close", false, Medic, "Medic");
        new Card("BlueboyLugos", "Skellige", 6, 1, "Close", false, NoAbility, "NoAbility");
        new Card("ClanAnCraite", "Skellige", 6, 3, "Close", false, TightBond, "TightBond");
        new Card("ClanBrokvarArcher", "Skellige", 6, 3, "Ranged", false, NoAbility, "NoAbility");
        new Card("ClanDimunPirate", "Skellige", 6, 1, "Ranged", false, Scorch, "Scorch");
        new Card("ClanDrummondShieldmaiden", "Skellige", 4, 3, "Close", false, TightBond, "TightBond");
        new Card("ClanTordarrochArmorsmith", "Skellige", 4, 1, "Close", false, NoAbility, "NoAbility");
        new Card("DonarAnHindar", "Skellige", 4, 1, "Close", false, NoAbility, "NoAbility");
        new Card("DraigBon-Dhu", "Skellige", 2, 1, "Siege", false, CommandersHorn, "CommandersHorn");
        new Card("HolgerBlackhand", "Skellige", 4, 1, "Siege", false, NoAbility, "NoAbility");
        new Card("LightLongship", "Skellige", 4, 3, "Ranged", false, Muster, "Muster");
        new Card("MadmanLugos", "Skellige", 6, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Olaf", "Skellige", 12, 1, "Agile", false, MoralBoost, "MoralBoost");
        new Card("Svanrige", "Skellige", 4, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Udalryk", "Skellige", 4, 1, "Close", false, NoAbility, "NoAbility");
        new Card("Vidkaarl", "Skellige", 14, 0, "Close", false, MoralBoost, "MoralBoost");
        new Card("WarLongship", "Skellige", 6, 3, "Siege", false, TightBond, "TightBond");
        new Card("YoungBerserker", "Skellige", 2, 3, "Ranged", true, Berserker, "Berserker");
        new Card("YoungVidkaarl", "Skellige", 8, 0, "Ranged", false, TightBond, "TightBond");
        new Card("Mardroeme", "Skellige", 0, 3, "Horn", false, NoAbility, "Mardroeme");
        new Card("Scorch", "All", 0, 3, "Spell", false, NoAbility, "Scorch");
        new Card("CommandersHorn", "All", 0, 3, "Horn", false, NoAbility, "CommandersHorn");
        new Card("Decoy", "All", 0, 3, "Spell", false, NoAbility, "Decoy");
        new Card("BitingFrost", "All", 0, 3, "Spell", false, NoAbility, "BitingFrost");
        new Card("ImpenetrableFog", "All", 0, 3, "Spell", false, NoAbility, "ImpenetrableFog");
        new Card("TorrentialRain", "All", 0, 3, "Spell", false, NoAbility, "TorrentialRain");
        new Card("SkelligeStorm", "All", 0, 3, "Spell", false, NoAbility, "SkelligeStorm");
        new Card("ClearWeather", "All", 0, 3, "Spell", false, NoAbility, "ClearWeather");
    }

    public static String getFaction(Card card) {
        return card.faction;
    }

    // Method to call the passed method
    public void performAction(GameEnvironment gameEnvironment) {
        action.execute(gameEnvironment);
    }

    public static Card getCardByName(String name) {
        for (Card i : allCards) {
            if (i.name.equals(name)) {
                return i;
            }
        }
        return null;
    }
}




