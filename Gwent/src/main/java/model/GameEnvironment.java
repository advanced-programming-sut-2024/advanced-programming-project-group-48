package model;

import com.google.gson.*;
import controller.menu.controller.GameEnvironmentController;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GameEnvironment {
    public Card[] inHandCards = new Card[10];
    public Card[] enemyInHandCards = new Card[10];
    public Card[] enemyRangedRow = new Card[10];
    public Card[] enemySiegeRow = new Card[10];
    public Card[] enemyClosedRow = new Card[10];
    public Card[] siegeRow = new Card[10];
    public Card[] closedRow = new Card[10];
    public Card[] rangedRow = new Card[10];
    public Card[] spellCards = new Card[3];
    public String enemyCommanderCard;
    public String commanderCard;
    public String enemyDeckLogo;
    public String deckLogo;
    public int enemyCrystalsNumber;
    public int crystalsNumber;
    public int enemyTotalScore;
    public int totalScore;
    public int numberOfVeto;
    public int enemyNumberOfVeto;
    public ArrayList<Card> enemyDiscardPile = new ArrayList<>();
    public ArrayList<Card> discardPile = new ArrayList<>();
    public Card enemySiegeHorn;
    public Card siegeHorn;
    public Card enemyCloseHorn;
    public Card closeHorn;
    public Card enemyRangedHorn;
    public Card rangedHorn;
    public boolean hasPlayedTurn;
    public boolean endRound;
    public int turnNumber;
    public Deck deckUser;
    public Deck deckEnemy;
    public int round1Score;
    public int round2Score;
    public int round3Score;
    public int enemyRound1Score;
    public int enemyRound2Score;
    public int enemyRound3Score;
    public int recentPlaceCardRow;
    public boolean hasPlayedCommander;
    public boolean hasPlayedEnemyCommander;

    public String toJson() {
        Gson gson = buildGson(); // Use the custom Gson instance
        return gson.toJson(this);
    }
    public static GameEnvironment fromJson(String json) {
        Gson gson = buildGson(); // Use the custom Gson instance for deserialization
        return gson.fromJson(json, GameEnvironment.class);
    }

    public GameEnvironment(){
        // Default constructor
    }
    static class CardActionSerializer implements JsonSerializer<CardAction> {
        @Override
        public JsonElement serialize(CardAction src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            result.addProperty("type", src.getClass().getName());
            result.add("properties", context.serialize(src, src.getClass()));
            return result;
        }
    }
    static class CardActionDeserializer implements JsonDeserializer<CardAction> {
        @Override
        public CardAction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String type = jsonObject.get("type").getAsString();
            try {
                return context.deserialize(jsonObject.get("properties"), Class.forName(type));
            } catch (ClassNotFoundException e) {
                throw new JsonParseException("Unknown element type: " + type, e);
            }
        }
    }
    public static Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapter(CardAction.class, new CardActionSerializer())
                .registerTypeAdapter(CardAction.class, new CardActionDeserializer())
                .create();
    }

    public GameEnvironment(Deck deckUser, Deck deckEnemy) {
        this.commanderCard = deckUser.getCommander();
        this.enemyCommanderCard = deckEnemy.getCommander();
        this.deckLogo = deckUser.getFaction();
        this.enemyDeckLogo = deckEnemy.getFaction();
        crystalsNumber = 0;
        enemyCrystalsNumber = 0;
        totalScore = 0;
        enemyTotalScore = 0;
        numberOfVeto = 0;
        enemyNumberOfVeto = 0;
        enemySiegeHorn = null;
        siegeHorn = null;
        enemyCloseHorn = null;
        closeHorn = null;
        enemyRangedHorn = null;
        rangedHorn = null;
        hasPlayedTurn = false;
        endRound = false;
        turnNumber = 1;
        this.deckUser = deckUser;
        this.deckEnemy = deckEnemy;
        round1Score = -1;
        round2Score = -1;
        round3Score = -1;
        enemyRound1Score = -1;
        enemyRound2Score = -1;
        enemyRound3Score = -1;
        recentPlaceCardRow = 0;
        hasPlayedCommander=false;
        hasPlayedEnemyCommander=false;
        GameEnvironmentController.drawRandomCards(deckUser.getAllCards(), inHandCards, 10);
        GameEnvironmentController.drawRandomCards(deckEnemy.getAllCards(), enemyInHandCards, 10);
    }

    public void swapGameEnvironmentFields() {
        // Swapping Card arrays
        Card[] tempCards;
        tempCards = this.inHandCards;
        this.inHandCards = this.enemyInHandCards;
        this.enemyInHandCards = tempCards;

        tempCards = this.enemyRangedRow;
        this.enemyRangedRow = this.rangedRow;
        this.rangedRow = tempCards;

        tempCards = this.enemySiegeRow;
        this.enemySiegeRow = this.siegeRow;
        this.siegeRow = tempCards;

        tempCards = this.enemyClosedRow;
        this.enemyClosedRow = this.closedRow;
        this.closedRow = tempCards;


        // Swapping scores and numbers
        int tempInt;
        tempInt = this.totalScore;
        this.totalScore = this.enemyTotalScore;
        this.enemyTotalScore = tempInt;

        tempInt = this.crystalsNumber;
        this.crystalsNumber = this.enemyCrystalsNumber;
        this.enemyCrystalsNumber = tempInt;

        tempInt = this.numberOfVeto;
        this.numberOfVeto = this.enemyNumberOfVeto;
        this.enemyNumberOfVeto = tempInt;

        // Swapping Strings
        String tempString;
        tempString = this.commanderCard;
        this.commanderCard = this.enemyCommanderCard;
        this.enemyCommanderCard = tempString;

        tempString = this.deckLogo;
        this.deckLogo = this.enemyDeckLogo;
        this.enemyDeckLogo = tempString;

        // Swapping Card
        Card tempCard;
        tempCard = this.siegeHorn;
        this.siegeHorn = this.enemySiegeHorn;
        this.enemySiegeHorn = tempCard;

        tempCard = this.closeHorn;
        this.closeHorn = this.enemyCloseHorn;
        this.enemyCloseHorn = tempCard;

        tempCard = this.rangedHorn;
        this.rangedHorn = this.enemyRangedHorn;
        this.enemyRangedHorn = tempCard;

        // Swapping hasPlayed
        boolean tempState;
        tempState=hasPlayedCommander;
        hasPlayedCommander=hasPlayedEnemyCommander;
        hasPlayedEnemyCommander=tempState;

        // Assuming turnNumber is a shared resource and does not need swapping
        ArrayList<Card> tempArrayList;
        tempArrayList = this.discardPile;
        this.discardPile = this.enemyDiscardPile;
        this.enemyDiscardPile = tempArrayList;
    }

    public Card[] getInHandCards() {
        return inHandCards;
    }

    public Card[] getEnemyInHandCards() {
        return enemyInHandCards;
    }

    public Card[] getEnemyRangedRow() {
        return enemyRangedRow;
    }

    public Card[] getEnemySiegeRow() {
        return enemySiegeRow;
    }

    public Card[] getEnemyClosedRow() {
        return enemyClosedRow;
    }

    public Card[] getSiegeRow() {
        return siegeRow;
    }

    public Card[] getClosedRow() {
        return closedRow;
    }

    public Card[] getRangedRow() {
        return rangedRow;
    }

    public Card[] getSpellCards() {
        return spellCards;
    }

    public String getEnemyCommanderCard() {
        return enemyCommanderCard;
    }

    public String getCommanderCard() {
        return commanderCard;
    }

    public String getEnemyDeckLogo() {
        return enemyDeckLogo;
    }

    public String getDeckLogo() {
        return deckLogo;
    }

    public int getEnemyCrystalsNumber() {
        return enemyCrystalsNumber;
    }

    public int getCrystalsNumber() {
        return crystalsNumber;
    }

    public int getEnemyTotalScore() {
        return enemyTotalScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getNumberOfVeto() {
        return numberOfVeto;
    }

    public int getEnemyNumberOfVeto() {
        return enemyNumberOfVeto;
    }

    public ArrayList<Card> getEnemyDiscardPile() {
        return enemyDiscardPile;
    }

    public ArrayList<Card> getDiscardPile() {
        return discardPile;
    }

    public Card getEnemySiegeHorn() {
        return enemySiegeHorn;
    }

    public Card getSiegeHorn() {
        return siegeHorn;
    }

    public Card getEnemyCloseHorn() {
        return enemyCloseHorn;
    }

    public Card getCloseHorn() {
        return closeHorn;
    }

    public Card getEnemyRangedHorn() {
        return enemyRangedHorn;
    }

    public Card getRangedHorn() {
        return rangedHorn;
    }

    public boolean isHasPlayedTurn() {
        return hasPlayedTurn;
    }

    public boolean isEndRound() {
        return endRound;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public Deck getDeckUser() {
        return deckUser;
    }

    public Deck getDeckEnemy() {
        return deckEnemy;
    }

    public int getRound1Score() {
        return round1Score;
    }

    public int getRound2Score() {
        return round2Score;
    }

    public int getRound3Score() {
        return round3Score;
    }

    public int getEnemyRound1Score() {
        return enemyRound1Score;
    }

    public int getEnemyRound2Score() {
        return enemyRound2Score;
    }

    public int getEnemyRound3Score() {
        return enemyRound3Score;
    }

    public int getRecentPlaceCardRow() {
        return recentPlaceCardRow;
    }

    public boolean isHasPlayedCommander() {
        return hasPlayedCommander;
    }

    public boolean isHasPlayedEnemyCommander() {
        return hasPlayedEnemyCommander;
    }

    public void setInHandCards(Card[] inHandCards) {
        this.inHandCards = inHandCards;
    }

    public void setEnemyInHandCards(Card[] enemyInHandCards) {
        this.enemyInHandCards = enemyInHandCards;
    }

    public void setEnemyRangedRow(Card[] enemyRangedRow) {
        this.enemyRangedRow = enemyRangedRow;
    }

    public void setEnemySiegeRow(Card[] enemySiegeRow) {
        this.enemySiegeRow = enemySiegeRow;
    }

    public void setEnemyClosedRow(Card[] enemyClosedRow) {
        this.enemyClosedRow = enemyClosedRow;
    }

    public void setSiegeRow(Card[] siegeRow) {
        this.siegeRow = siegeRow;
    }

    public void setClosedRow(Card[] closedRow) {
        this.closedRow = closedRow;
    }

    public void setRangedRow(Card[] rangedRow) {
        this.rangedRow = rangedRow;
    }

    public void setSpellCards(Card[] spellCards) {
        this.spellCards = spellCards;
    }

    public void setEnemyCommanderCard(String enemyCommanderCard) {
        this.enemyCommanderCard = enemyCommanderCard;
    }

    public void setCommanderCard(String commanderCard) {
        this.commanderCard = commanderCard;
    }

    public void setEnemyDeckLogo(String enemyDeckLogo) {
        this.enemyDeckLogo = enemyDeckLogo;
    }

    public void setDeckLogo(String deckLogo) {
        this.deckLogo = deckLogo;
    }

    public void setEnemyCrystalsNumber(int enemyCrystalsNumber) {
        this.enemyCrystalsNumber = enemyCrystalsNumber;
    }

    public void setCrystalsNumber(int crystalsNumber) {
        this.crystalsNumber = crystalsNumber;
    }

    public void setEnemyTotalScore(int enemyTotalScore) {
        this.enemyTotalScore = enemyTotalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setNumberOfVeto(int numberOfVeto) {
        this.numberOfVeto = numberOfVeto;
    }

    public void setEnemyNumberOfVeto(int enemyNumberOfVeto) {
        this.enemyNumberOfVeto = enemyNumberOfVeto;
    }

    public void setEnemyDiscardPile(ArrayList<Card> enemyDiscardPile) {
        this.enemyDiscardPile = enemyDiscardPile;
    }

    public void setDiscardPile(ArrayList<Card> discardPile) {
        this.discardPile = discardPile;
    }

    public void setEnemySiegeHorn(Card enemySiegeHorn) {
        this.enemySiegeHorn = enemySiegeHorn;
    }

    public void setSiegeHorn(Card siegeHorn) {
        this.siegeHorn = siegeHorn;
    }

    public void setEnemyCloseHorn(Card enemyCloseHorn) {
        this.enemyCloseHorn = enemyCloseHorn;
    }

    public void setCloseHorn(Card closeHorn) {
        this.closeHorn = closeHorn;
    }

    public void setEnemyRangedHorn(Card enemyRangedHorn) {
        this.enemyRangedHorn = enemyRangedHorn;
    }

    public void setRangedHorn(Card rangedHorn) {
        this.rangedHorn = rangedHorn;
    }

    public void setHasPlayedTurn(boolean hasPlayedTurn) {
        this.hasPlayedTurn = hasPlayedTurn;
    }

    public void setEndRound(boolean endRound) {
        this.endRound = endRound;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public void setDeckUser(Deck deckUser) {
        this.deckUser = deckUser;
    }

    public void setDeckEnemy(Deck deckEnemy) {
        this.deckEnemy = deckEnemy;
    }

    public void setRound1Score(int round1Score) {
        this.round1Score = round1Score;
    }

    public void setRound2Score(int round2Score) {
        this.round2Score = round2Score;
    }

    public void setRound3Score(int round3Score) {
        this.round3Score = round3Score;
    }

    public void setEnemyRound1Score(int enemyRound1Score) {
        this.enemyRound1Score = enemyRound1Score;
    }

    public void setEnemyRound2Score(int enemyRound2Score) {
        this.enemyRound2Score = enemyRound2Score;
    }

    public void setEnemyRound3Score(int enemyRound3Score) {
        this.enemyRound3Score = enemyRound3Score;
    }

    public void setRecentPlaceCardRow(int recentPlaceCardRow) {
        this.recentPlaceCardRow = recentPlaceCardRow;
    }

    public void setHasPlayedCommander(boolean hasPlayedCommander) {
        this.hasPlayedCommander = hasPlayedCommander;
    }

    public void setHasPlayedEnemyCommander(boolean hasPlayedEnemyCommander) {
        this.hasPlayedEnemyCommander = hasPlayedEnemyCommander;
    }

}