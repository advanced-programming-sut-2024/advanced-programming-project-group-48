package controller.menu.controller;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Card;
import model.Deck;
import model.User;
import view.menus.GameEnvironmentMenu;

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
    public ImageView enemyCrystalsNumber;
    public ImageView crystalsNumber;
    public Label enemyTotalScore;
    public Label totalScore;
    public ImageView enemyCommanderCard;
    public ImageView commanderCard;
    public ImageView enemyRemainingCard;
    public ImageView remainingCard;
    public ImageView enemyDiscardPile;
    public ImageView discardPile;
    public ImageView enemySiegeHorn;
    public ImageView siegeHorn;
    public ImageView enemyCloseHorn;
    public ImageView closeHorn;
    public ImageView enemyRangedHorn;
    public ImageView rangedHorn;

    public GameEnvironment gameEnvironment=new GameEnvironment(User.loggedInUser.getDeck(), GameEnvironmentMenu.currentGame.oppenentUser.getDeck());

    public void initialize() {
        String userFaction = "Monsters";
        remainingCard.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/lg/faction_" + userFaction.toLowerCase() + ".jpg"))));
        drawRandomCards(User.loggedInUser.getDeck().getAllCards(), gameEnvironment.inHandCards, 10);
        int count=0;
        for (int i = 0; i < gameEnvironment.inHandCards.length; i++) {
            if(gameEnvironment.inHandCards[i]!=null){
                count++;
            }
        }
        for (int i = 0; i < count; i++) {
            ((ImageView) inHandCards.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/lg/" + gameEnvironment.inHandCards[i].name.toLowerCase()+ ".jpg"))));

        }

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
        public int enemyNumberRemainingCards;
        public int numberRemainingCards;
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
            this.numberRemainingCards = deckUser.getAllCards().size();
            this.enemyNumberRemainingCards = deckEnemy.getAllCards().size();
            crystalsNumber=0;
            enemyCrystalsNumber=0;
            totalScore=0;
            enemyTotalScore=0;
        }
    }
}
