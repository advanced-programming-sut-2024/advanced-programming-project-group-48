package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Faction {
    private static HashMap<String , ArrayList<String>> faction = new HashMap<>();
    private static HashMap<String , ArrayList<String>> factionCard = new HashMap<>();
    private static ArrayList<String> CommandersOfNorthernRealms = new ArrayList<>();
    private static ArrayList<String> CommandersOfNilfgaardianEmpire = new ArrayList<>();
    private static ArrayList<String> CommandersOfMonsters = new ArrayList<>();
    private static ArrayList<String> CommandersOfScoiaTaell = new ArrayList<>();
    private static ArrayList<String> CommandersOfSkellige = new ArrayList<>();
    static {
        CommandersOfNorthernRealms = new ArrayList<String>();
        CommandersOfNilfgaardianEmpire = new ArrayList<String>();
        CommandersOfMonsters = new ArrayList<String>();
        CommandersOfScoiaTaell = new ArrayList<String>();
        CommandersOfSkellige = new ArrayList<String>();
        CommandersOfNorthernRealms.add("The Siegemaster");
        CommandersOfNorthernRealms.add("The Steel-Forged");
        CommandersOfNorthernRealms.add("The King of Temeria");
        CommandersOfNorthernRealms.add("The Lord Commander of the North");
        CommandersOfNorthernRealms.add("The Son of Medell");
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
        faction.put("Northen Realms" , CommandersOfNorthernRealms);
        faction.put("Nilfgaardian Empire" , CommandersOfNilfgaardianEmpire);
        faction.put("Monsters" , CommandersOfMonsters);
        faction.put("Scoia’taell" , CommandersOfScoiaTaell);
        faction.put("Skellige" , CommandersOfSkellige);

    }

    public static HashMap<String, ArrayList<String>> getFaction() {
        return faction;
    }

    public static ArrayList<String> getCommandersOfNorthernRealms() {
        return CommandersOfNorthernRealms;
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

    public static ArrayList<String> getCommandersByFaction(String factionName) {
        switch (factionName) {
            case "Northen Realms":
                return getCommandersOfNorthernRealms();
            case "Nilfgaardian Empire":
                return getCommandersOfNilfgaardianEmpire();
            case "Monsters":
                return getCommandersOfMonsters();
            case "Scoia’taell":
                return getCommandersOfScoiaTaell();
            case "Skellige":
                return getCommandersOfSkellige();
            default:
                return new ArrayList<>();
        }
    }

    public static HashMap<String, ArrayList<String>> getFactionCard() {
        return factionCard;
    }
}
