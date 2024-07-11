package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Faction {
    private static final HashMap<String, ArrayList<String>> faction = new HashMap<>();
    private static final HashMap<String, ArrayList<String>> factionCard = new HashMap<>();
    private static final ArrayList<String> CommandersOfNorthernRealms = new ArrayList<>();
    private static final ArrayList<String> CommandersOfNilfgaard = new ArrayList<>();
    private static final ArrayList<String> CommandersOfMonsters = new ArrayList<>();
    private static final ArrayList<String> CommandersOfScoiaTaell = new ArrayList<>();
    private static final ArrayList<String> CommandersOfSkellige = new ArrayList<>();

    static {
        CommandersOfNorthernRealms.add("TheSiegemaster");
        CommandersOfNorthernRealms.add("TheSteal-Forged");
        CommandersOfNorthernRealms.add("KingOfTemperia");
        CommandersOfNorthernRealms.add("LordOfCommanderOfTheNorth");
        CommandersOfNorthernRealms.add("SunOfMedell");
        CommandersOfNilfgaard.add("TheWhiteFlame");
        CommandersOfNilfgaard.add("HisImperialMajesty");
        CommandersOfNilfgaard.add("EmperorOfNilfgaard");
        CommandersOfNilfgaard.add("TheRelentless");
        CommandersOfNilfgaard.add("InvaderOfNorth");
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
        faction.put("Nilfgaard" , CommandersOfNilfgaard);
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
        return CommandersOfNilfgaard;
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

    public static ArrayList<String> getLeadersByFaction(String factionName) {
        return switch (factionName) {
            case "NorthernRealms" -> getCommandersOfNorthernRealms();
            case "NilfgaardianEmpire" -> getCommandersOfNilfgaardianEmpire();
            case "Monsters" -> getCommandersOfMonsters();
            case "Scoia'tael" -> getCommandersOfScoiaTaell();
            case "Skellige" -> getCommandersOfSkellige();
            default -> new ArrayList<>();
        };
    }

    public static HashMap<String, ArrayList<String>> getFactionCard() {
        return factionCard;
    }

    public static ArrayList<String> getFactionNames() {
        return new ArrayList<>(faction.keySet());
    }

    public static ArrayList<String> getCardsByFaction(String faction) {
        ArrayList<String> cardsName = new ArrayList<>();
        for(Card card : Card.allCards){
            if(card.faction.equals("All")||card.faction.equals(faction)){
                cardsName.add(card.name);
            }
        }
        return cardsName;
    }
}
