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
        CommandersOfNorthernRealms.add("TheSiegemaster");
        CommandersOfNorthernRealms.add("TheSteel-Forged");
        CommandersOfNorthernRealms.add("KingOfTemperia");
        CommandersOfNorthernRealms.add("LordCommanderOfTheNorth");
        CommandersOfNorthernRealms.add("SunOfMedell");
        CommandersOfNilfgaardianEmpire.add("TheWhiteFlame");
        CommandersOfNilfgaardianEmpire.add("HisImperialMajesty");
        CommandersOfNilfgaardianEmpire.add("EmperorOfNilfgaard");
        CommandersOfNilfgaardianEmpire.add("TheRelentless");
        CommandersOfNilfgaardianEmpire.add("InvaderOfNorth");
        CommandersOfMonsters.add("BringerOfDeath");
        CommandersOfMonsters.add("KingOfTheWildHunt");
        CommandersOfMonsters.add("DestroyerOfWorlds");
        CommandersOfMonsters.add("CommanderOfRedRiders");
        CommandersOfMonsters.add("TheTreacherous");
        CommandersOfScoiaTaell.add("QueenOfDolBlathanna");
        CommandersOfScoiaTaell.add("TheBeautiful");
        CommandersOfScoiaTaell.add("DaisyOfTheValley");
        CommandersOfScoiaTaell.add("PureBloodElf");
        CommandersOfScoiaTaell.add("HopeOfTheAenSeidhe");
        CommandersOfSkellige.add("CrachAnCraite");
        CommandersOfSkellige.add("KingBran");
        faction.put("NorthernRealms" , CommandersOfNorthernRealms);
        faction.put("NilfgaardianEmpire" , CommandersOfNilfgaardianEmpire);
        faction.put("Monsters" , CommandersOfMonsters);
        faction.put("ScoiaTaell" , CommandersOfScoiaTaell);
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
            case "ScoiaTaell":
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
