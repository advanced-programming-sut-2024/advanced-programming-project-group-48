package controller.menu.controller;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Card;
import model.Deck;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Collections;
import java.util.List;


public class GameEnvironmentController {
    public HBox inHandCards;
    public HBox enemyRangedRow;
    public HBox enemySiegeRow;
    public HBox enemyClosedRow;
    public HBox siegeRow;
    public HBox closedRow;
    public HBox rangedRow;
    public HBox spellCards;
    public Label enemyNumberRemainingCards;
    public Label numberRemainingCards;
    public Label enemyCrystalsNumber;
    public Label crystalsNumber;
    public Label enemyTotalScore;
    public Label totalScore;
    public ImageView enemyCommanderCard;
    public ImageView commanderCard;
    public ImageView enemyDeckLogo;
    public ImageView deckLogo;
    public ImageView enemyDiscardPile;
    public ImageView discardPile;
    public ImageView enemySiegeHorn;
    public ImageView siegeHorn;
    public ImageView enemyCloseHorn;
    public ImageView closeHorn;
    public ImageView enemyRangedHorn;
    public ImageView rangedHorn;
    public Deck sampleDeck = new Deck();
    public Deck enemySampleDeck= new Deck();
    {
        sampleDeck.setCommander("KingOfTemperia");
        sampleDeck.setFaction("NorthernRealms");
        sampleDeck.getAllCards().add(Card.getCardByName("PoorFuckingInfantry").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("PoorFuckingInfantry").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("PoorFuckingInfantry").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("Thaler").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("Thaler").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("Thaler").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("Ves").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("Ves").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("Ves").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("Trebuchet").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("Trebuchet").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("Trebuchet").clone());
        enemySampleDeck.setCommander("LordOfCommanderOfTheNorth");
        enemySampleDeck.setFaction("NorthernRealms");
        enemySampleDeck.getAllCards().add(Card.getCardByName("PoorFuckingInfantry").clone());
        enemySampleDeck.getAllCards().add(Card.getCardByName("PoorFuckingInfantry").clone());
        enemySampleDeck.getAllCards().add(Card.getCardByName("PoorFuckingInfantry").clone());
        enemySampleDeck.getAllCards().add(Card.getCardByName("Thaler").clone());
        enemySampleDeck.getAllCards().add(Card.getCardByName("Thaler").clone());
        enemySampleDeck.getAllCards().add(Card.getCardByName("Thaler").clone());
        enemySampleDeck.getAllCards().add(Card.getCardByName("Ves").clone());
        enemySampleDeck.getAllCards().add(Card.getCardByName("Ves").clone());
        enemySampleDeck.getAllCards().add(Card.getCardByName("Ves").clone());
        enemySampleDeck.getAllCards().add(Card.getCardByName("Trebuchet").clone());
        enemySampleDeck.getAllCards().add(Card.getCardByName("Trebuchet").clone());
        enemySampleDeck.getAllCards().add(Card.getCardByName("Trebuchet").clone());
    }

//    public GameEnvironment gameEnvironment=new GameEnvironment(User.loggedInUser.getDeck(), GameEnvironmentMenu.currentGame.oppenentUser.getDeck());
    public GameEnvironment gameEnvironment = new GameEnvironment(sampleDeck,enemySampleDeck);

    public void initialize() {
        commanderCard.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Leaders/" + gameEnvironment.commanderCard + ".jpg"))));
        enemyCommanderCard.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Leaders/" + gameEnvironment.enemyCommanderCard + ".jpg"))));
        deckLogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icons/deck_back_" + gameEnvironment.deckLogo + ".jpg"))));
        enemyDeckLogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icons/deck_back_" + gameEnvironment.enemyDeckLogo + ".jpg"))));
        drawRandomCards(sampleDeck.getAllCards(), gameEnvironment.inHandCards, 10);
        updateTotalScore();
        updateEnemyTotalScore();
        updateCrystalsNumber();
        updateEnemyCrystalsNumber();
        updateNumberRemainingCards();
        updateEnemyNumberRemainingCards();
        updateInHandCards();




    }

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
    public void updateTotalScore() {
        int totalScore = 0;
        for (Card card : gameEnvironment.closedRow) {
            if (card != null) {
                totalScore += card.power;
            }
        }
        for (Card card : gameEnvironment.rangedRow) {
            if (card != null) {
                totalScore += card.power;
            }
        }
        for (Card card : gameEnvironment.siegeRow) {
            if (card != null) {
                totalScore += card.power;
            }
        }
        gameEnvironment.totalScore = totalScore;
        this.totalScore.setText(String.valueOf(totalScore));
    }
    public void updateEnemyTotalScore() {
        int totalScore = 0;
        for (Card card : gameEnvironment.enemyClosedRow) {
            if (card != null) {
                totalScore += card.power;
            }
        }
        for (Card card : gameEnvironment.enemyRangedRow) {
            if (card != null) {
                totalScore += card.power;
            }
        }
        for (Card card : gameEnvironment.enemySiegeRow) {
            if (card != null) {
                totalScore += card.power;
            }
        }
        gameEnvironment.enemyTotalScore = totalScore;
        this.enemyTotalScore.setText(String.valueOf(totalScore));
    }

    public void updateNumberRemainingCards(){
        int numberRemainingCards = 0;
        for (Card card : gameEnvironment.inHandCards) {
            if (card != null) {
                numberRemainingCards++;
            }
        }
        this.numberRemainingCards.setText(String.valueOf(numberRemainingCards));
    }
    //todo implement updateEnemyNumberRemainingCards
    public void updateEnemyNumberRemainingCards(){
        this.enemyNumberRemainingCards.setText("?");
    }
    public void updateCrystalsNumber(){
        this.crystalsNumber.setText(String.valueOf(gameEnvironment.crystalsNumber));
    }
    public void updateEnemyCrystalsNumber(){
        this.enemyCrystalsNumber.setText(String.valueOf(gameEnvironment.enemyCrystalsNumber));
    }
    public void updateInHandCards(){
        for (int i = 0; i < gameEnvironment.inHandCards.length; i++) {
            if (gameEnvironment.inHandCards[i] != null) {
                // todo replace the faction of below to card faction
                ((ImageView) inHandCards.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.deckLogo+"/"+gameEnvironment.inHandCards[i].name + ".jpg"))));
            }
        }
    }


    public static class GameEnvironment {
        public Card[] inHandCards = new Card[10];
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
        public ArrayList<Card> enemyDiscardPile = new ArrayList<>();
        public ArrayList<Card> discardPile = new ArrayList<>();
        public String enemySiegeHorn;
        public String siegeHorn;
        public String enemyCloseHorn;
        public String closeHorn;
        public String enemyRangedHorn;
        public String rangedHorn;

        public GameEnvironment(Deck deckUser, Deck deckEnemy){
            this.commanderCard = deckUser.getCommander();
            this.enemyCommanderCard = deckEnemy.getCommander();
            this.deckLogo = deckUser.getFaction();
            this.enemyDeckLogo = deckEnemy.getFaction();
            crystalsNumber=0;
            enemyCrystalsNumber=0;
            totalScore=0;
            enemyTotalScore=0;
            enemySiegeHorn="";
            siegeHorn="";
            enemyCloseHorn="";
            closeHorn="";
            enemyRangedHorn="";
            rangedHorn="";
        }
    }
}
