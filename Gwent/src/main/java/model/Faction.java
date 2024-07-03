package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Faction {
    private static HashMap<String , ArrayList<String>> faction = new HashMap<>();
    private static HashMap<String , ArrayList<String>> factionCard = new HashMap<>();
    private static ArrayList<String> CommandersOfNorthenRealms = new ArrayList<>();
    private static ArrayList<String> CommandersOfNilfgaardianEmpire = new ArrayList<>();
    private static ArrayList<String> CommandersOfMonsters = new ArrayList<>();
    private static ArrayList<String> CommandersOfScoiaTaell = new ArrayList<>();
    private static ArrayList<String> CommandersOfSkellige = new ArrayList<>();
    private static ArrayList<String> cardsOfNorthenRealms = new ArrayList<>();
    private static ArrayList<String> cardsOfNilfgaardianEmpire = new ArrayList<>();
    private static ArrayList<String> cardsOfMonsters = new ArrayList<>();
    private static ArrayList<String> cardsOfScoiaTaell = new ArrayList<>();
    private static ArrayList<String> cardsOfSkellige = new ArrayList<>();
    static {
        CommandersOfNorthenRealms = new ArrayList<String>();
        CommandersOfNilfgaardianEmpire = new ArrayList<String>();
        CommandersOfMonsters = new ArrayList<String>();
        CommandersOfScoiaTaell = new ArrayList<String>();
        CommandersOfSkellige = new ArrayList<String>();
        CommandersOfNorthenRealms.add("The Siegemaster");
        CommandersOfNorthenRealms.add("The Steel-Forged");
        CommandersOfNorthenRealms.add("The King of Temeria");
        CommandersOfNorthenRealms.add("The Lord Commander of the North");
        CommandersOfNorthenRealms.add("The Son of Medell");
        CommandersOfNilfgaardianEmpire.add("The White Flame");
        CommandersOfNilfgaardianEmpire.add("His Imperial Majesty");
        CommandersOfNilfgaardianEmpire.add("Emperor of Nilfgaard");
        CommandersOfNilfgaardianEmpire.add("The Relentless");
        CommandersOfNilfgaardianEmpire.add("Invader of the North");
        CommandersOfMonsters.add("Bringer of Death");
        CommandersOfMonsters.add("King of the wild Hunt");
        CommandersOfMonsters.add("Destroyer of Worlds");
        CommandersOfMonsters.add("Commander of the Red Riders");
        CommandersOfMonsters.add("The Treacherous");
        CommandersOfScoiaTaell.add("Queen of Dol Blathanna");
        CommandersOfScoiaTaell.add("The Beautiful");
        CommandersOfScoiaTaell.add("Daisy of the Valley");
        CommandersOfScoiaTaell.add("Pureblood Elf");
        CommandersOfScoiaTaell.add("Hope of the Aen Seidhe");
        CommandersOfSkellige.add("Crach an Craite");
        CommandersOfSkellige.add("King Bran");
        faction.put("Northen Realms" , CommandersOfNorthenRealms);
        faction.put("Nilfgaardian Empire" , CommandersOfNilfgaardianEmpire);
        faction.put("Monsters" , CommandersOfMonsters);
        faction.put("Scoia’taell" , CommandersOfScoiaTaell);
        faction.put("Skellige" , CommandersOfSkellige);
        factionCard.put("Northen Realms" , cardsOfNorthenRealms);
        factionCard.put("Monsters" , cardsOfMonsters);
        factionCard.put("Nilfgaardian Empire" , cardsOfNilfgaardianEmpire);
        factionCard.put("Scoia’taell" , cardsOfScoiaTaell);
        factionCard.put("Skellige" , cardsOfSkellige);
    }

    public static HashMap<String, ArrayList<String>> getFaction() {
        return faction;
    }

    public static ArrayList<String> getCommandersOfNorthenRealms() {
        return CommandersOfNorthenRealms;
    }

    public static ArrayList<String> getCommandersOfNilfgaardianEmpire() {
        return CommandersOfNilfgaardianEmpire;
    }

    public static ArrayList<String> getCommandersOfMonsters() {
        return CommandersOfMonsters;
    }

    public static ArrayList<String> getCommandersOfScoiaTaell() {
        return CommandersOfScoiaTaell;
    }

    public static ArrayList<String> getCommandersOfSkellige() {
        return CommandersOfSkellige;
    }

    public static ArrayList<String> getCardsOfNorthenRealms() {
        return cardsOfNorthenRealms;
    }

    public static ArrayList<String> getCardsOfNilfgaardianEmpire() {
        return cardsOfNilfgaardianEmpire;
    }

    public static ArrayList<String> getCardsOfMonsters() {
        return cardsOfMonsters;
    }

    public static ArrayList<String> getCardsOfScoiaTaell() {
        return cardsOfScoiaTaell;
    }

    public static ArrayList<String> getCardsOfSkellige() {
        return cardsOfSkellige;
    }

    public static HashMap<String, ArrayList<String>> getFactionCard() {
        return factionCard;
    }
}