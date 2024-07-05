package model;

import model.Card;
import model.Deck;

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
    public String enemySiegeHorn;
    public String siegeHorn;
    public String enemyCloseHorn;
    public String closeHorn;
    public String enemyRangedHorn;
    public String rangedHorn;
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
        enemySiegeHorn = "";
        siegeHorn = "";
        enemyCloseHorn = "";
        closeHorn = "";
        enemyRangedHorn = "";
        rangedHorn = "";
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

//            TODO swap spell cards

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

        tempString = this.siegeHorn;
        this.siegeHorn = this.enemySiegeHorn;
        this.enemySiegeHorn = tempString;

        tempString = this.closeHorn;
        this.closeHorn = this.enemyCloseHorn;
        this.enemyCloseHorn = tempString;

        tempString = this.rangedHorn;
        this.rangedHorn = this.enemyRangedHorn;
        this.enemyRangedHorn = tempString;


        // Assuming turnNumber is a shared resource and does not need swapping
        //TODO
        // Discard piles are also assumed to be specific to each player and not swapped
    }


}