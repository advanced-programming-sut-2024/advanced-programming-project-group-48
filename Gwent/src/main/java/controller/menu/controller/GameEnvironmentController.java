package controller.menu.controller;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    public Deck enemySampleDeck = new Deck();

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
    public GameEnvironment gameEnvironment = new GameEnvironment(sampleDeck, enemySampleDeck);

    public void initialize() {
        commanderCard.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Leaders/" + gameEnvironment.commanderCard + ".jpg"))));
        enemyCommanderCard.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Leaders/" + gameEnvironment.enemyCommanderCard + ".jpg"))));
        deckLogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icons/deck_back_" + gameEnvironment.deckLogo + ".jpg"))));
        enemyDeckLogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icons/deck_back_" + gameEnvironment.enemyDeckLogo + ".jpg"))));
        drawRandomCards(sampleDeck.getAllCards(), gameEnvironment.inHandCards, 10);
        drawRandomCards(enemySampleDeck.getAllCards(), gameEnvironment.enemyInHandCards, 10);
        updateEverything();


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

    public void vetoRandomCard(List<Card> allCards, Card[] inHandCards, int cardIndex) {
        if (allCards.isEmpty()) {
            System.out.println("The deck is empty. Cannot replace card.");
            return;
        }

        Collections.shuffle(allCards); // Shuffle the deck to ensure randomness

        if (cardIndex >= 0 && cardIndex < inHandCards.length) {
            Card cardToReplace = inHandCards[cardIndex]; // Store the card to be replaced
            inHandCards[cardIndex] = allCards.get(0); // Replace the card at cardIndex with the top card from the deck
            allCards.add(cardToReplace); // Add the replaced card back to the deck
            Collections.shuffle(allCards); // Optional: Shuffle the deck again
            allCards.remove(0); // Remove the top card from the deck to prevent it from being drawn again
            updateInHandCards(); // Update the GUI to reflect the changes
        } else {
            System.out.println("Card index is out of bounds.");
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

    public void updateNumberRemainingCards() {
        int numberRemainingCards = 0;
        for (Card card : gameEnvironment.inHandCards) {
            if (card != null) {
                numberRemainingCards++;
            }
        }
        this.numberRemainingCards.setText(String.valueOf(numberRemainingCards));
    }

    public void updateEnemyNumberRemainingCards() {
        int numberRemainingCards = 0;
        for (Card card : gameEnvironment.enemyInHandCards) {
            if (card != null) {
                numberRemainingCards++;
            }
        }
        this.enemyNumberRemainingCards.setText(String.valueOf(numberRemainingCards));
    }

    public void updateCrystalsNumber() {
        this.crystalsNumber.setText(String.valueOf(gameEnvironment.crystalsNumber));
    }

    public void updateEnemyCrystalsNumber() {
        this.enemyCrystalsNumber.setText(String.valueOf(gameEnvironment.enemyCrystalsNumber));
    }

    public void updateInHandCards() {
        for (int i = 0; i < gameEnvironment.inHandCards.length; i++) {
            if (gameEnvironment.inHandCards[i] != null) {
                // todo replace the faction of below to card faction
                ((ImageView) inHandCards.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.deckLogo + "/" + gameEnvironment.inHandCards[i].name + ".jpg"))));
            } else {
                ((ImageView) inHandCards.getChildren().get(i)).setImage(null);
            }
        }
    }

    public void updateClosedRow() {
        for (int i = 0; i < gameEnvironment.closedRow.length; i++) {
            if (gameEnvironment.closedRow[i] != null) {
                ((ImageView) closedRow.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.deckLogo + "/" + gameEnvironment.closedRow[i].name + ".jpg"))));
            } else {
                ((ImageView) closedRow.getChildren().get(i)).setImage(null);
            }
        }
    }

    public void updateRangedRow() {
        for (int i = 0; i < gameEnvironment.rangedRow.length; i++) {
            if (gameEnvironment.rangedRow[i] != null) {
                ((ImageView) rangedRow.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.deckLogo + "/" + gameEnvironment.rangedRow[i].name + ".jpg"))));
            } else {
                ((ImageView) rangedRow.getChildren().get(i)).setImage(null);
            }
        }
    }

    public void updateSiegeRow() {
        for (int i = 0; i < gameEnvironment.siegeRow.length; i++) {
            if (gameEnvironment.siegeRow[i] != null) {
                ((ImageView) siegeRow.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.deckLogo + "/" + gameEnvironment.siegeRow[i].name + ".jpg"))));
            } else {
                ((ImageView) siegeRow.getChildren().get(i)).setImage(null);
            }
        }
    }

    public void updateEnemyClosedRow() {
        for (int i = 0; i < gameEnvironment.enemyClosedRow.length; i++) {
            if (gameEnvironment.enemyClosedRow[i] != null) {
                ((ImageView) enemyClosedRow.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.enemyDeckLogo + "/" + gameEnvironment.enemyClosedRow[i].name + ".jpg"))));
            } else {
                ((ImageView) enemyClosedRow.getChildren().get(i)).setImage(null);
            }
        }
    }

    public void updateEnemyRangedRow() {
        for (int i = 0; i < gameEnvironment.enemyRangedRow.length; i++) {
            if (gameEnvironment.enemyRangedRow[i] != null) {
                ((ImageView) enemyRangedRow.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.enemyDeckLogo + "/" + gameEnvironment.enemyRangedRow[i].name + ".jpg"))));
            } else {
                ((ImageView) enemyRangedRow.getChildren().get(i)).setImage(null);
            }
        }
    }

    public void updateEnemySiegeRow() {
        for (int i = 0; i < gameEnvironment.enemySiegeRow.length; i++) {
            if (gameEnvironment.enemySiegeRow[i] != null) {
                ((ImageView) enemySiegeRow.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.enemyDeckLogo + "/" + gameEnvironment.enemySiegeRow[i].name + ".jpg"))));
            } else {
                ((ImageView) enemySiegeRow.getChildren().get(i)).setImage(null);
            }
        }
    }

    public void updateCommanderCard() {
        commanderCard.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Leaders/" + gameEnvironment.commanderCard + ".jpg"))));
    }

    public void updateEnemyCommanderCard() {
        enemyCommanderCard.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Leaders/" + gameEnvironment.enemyCommanderCard + ".jpg"))));
    }

    public void updateDeckLogo() {
        deckLogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icons/deck_back_" + gameEnvironment.deckLogo + ".jpg"))));
    }

    public void updateEnemyDeckLogo() {
        enemyDeckLogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icons/deck_back_" + gameEnvironment.enemyDeckLogo + ".jpg"))));
    }

    public void updateEverything() {
        updateInHandCards();
        updateClosedRow();
        updateRangedRow();
        updateSiegeRow();
        updateEnemyClosedRow();
        updateEnemyRangedRow();
        updateEnemySiegeRow();
        updateTotalScore();
        updateEnemyTotalScore();
        updateNumberRemainingCards();
        updateEnemyNumberRemainingCards();
        updateCrystalsNumber();
        updateEnemyCrystalsNumber();
        updateCommanderCard();
        updateEnemyCommanderCard();
        updateDeckLogo();
        updateEnemyDeckLogo();
    }

    public void onCardClicked(MouseEvent event) {
        ImageView clickedCard = (ImageView) event.getSource();
        int cardIndex = inHandCards.getChildren().indexOf(clickedCard);
        if (gameEnvironment.inHandCards[cardIndex] == null) return;
        if (gameEnvironment.hasPlayedTurn) {
            System.err.println("You have played your turn please pass the round");
            return;
        }
        if (event.getButton() == MouseButton.PRIMARY) {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem option1 = new MenuItem("Closed Row");
            option1.setOnAction(e -> handleClosedRow(cardIndex));

            MenuItem option2 = new MenuItem("Ranged Row");
            option2.setOnAction(e -> handleRangedRow(cardIndex));

            MenuItem option3 = new MenuItem("Siege Row");
            option3.setOnAction(e -> handleSiegeRow(cardIndex));

            MenuItem option4 = new MenuItem("Veto");
            option4.setOnAction(e -> handleVeto(cardIndex));


            contextMenu.getItems().addAll(option1, option2, option3, option4);

            contextMenu.show((ImageView) event.getSource(), event.getScreenX(), event.getScreenY());
        }
    }

    private void handleVeto(int cardIndex) {
        if (gameEnvironment.numberOfVeto >= 2) {
            //Todo replace with alert
            System.err.println("You can't veto more than 2 times");
            return;
        }
        gameEnvironment.numberOfVeto++;
        vetoRandomCard(sampleDeck.getAllCards(), gameEnvironment.inHandCards, cardIndex);
        gameEnvironment.hasPlayedTurn = true;
    }


    private void handleClosedRow(int cardIndex) {
        ImageView cardNode = (ImageView) inHandCards.getChildren().get(cardIndex);
        Card card = gameEnvironment.inHandCards[cardIndex];
        if (card == null) {
            System.err.println("Card is null!");
            return;
        }
        if ((!card.type.equals("Close") && !card.type.equals("Agile"))) {
            System.err.println("Card is not of type Close or Agile");
            return;
        }
        int emptyIndex = findEmptyIndex(gameEnvironment.closedRow);
        if (emptyIndex == -1) {
            System.err.println("Closed row is full");
            return;
        }
        gameEnvironment.closedRow[emptyIndex] = card;
        ((ImageView) closedRow.getChildren().get(emptyIndex)).setImage(cardNode.getImage());
        card.performAction();
        cardNode.setImage(null);
        gameEnvironment.inHandCards[cardIndex] = null;
        updateTotalScore();
        updateNumberRemainingCards();
        gameEnvironment.hasPlayedTurn = true;
    }

    private int findEmptyIndex(Card[] row) {
        for (int i = 0; i < row.length; i++) {
            if (row[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private void handleRangedRow(int cardIndex) {
        ImageView cardNode = (ImageView) inHandCards.getChildren().get(cardIndex);
        Card card = gameEnvironment.inHandCards[cardIndex];
        if (card == null) {
            System.err.println("Card is null!");
            return;
        }
        if ((!card.type.equals("Ranged") && !card.type.equals("Agile"))) {
            System.err.println("Card is not of type Ranged or Agile");
            return;
        }
        int emptyIndex = findEmptyIndex(gameEnvironment.rangedRow);
        if (emptyIndex == -1) {
            System.err.println("Ranged row is full");
            return;
        }
        gameEnvironment.rangedRow[emptyIndex] = card;
        ((ImageView) rangedRow.getChildren().get(emptyIndex)).setImage(cardNode.getImage());
        cardNode.setImage(null);
        gameEnvironment.inHandCards[cardIndex] = null;
        updateTotalScore();
        updateNumberRemainingCards();
        gameEnvironment.hasPlayedTurn = true;
    }

    private void handleSiegeRow(int cardIndex) {
        ImageView cardNode = (ImageView) inHandCards.getChildren().get(cardIndex);
        Card card = gameEnvironment.inHandCards[cardIndex];
        if (card == null) {
            System.err.println("Card is null!");
            return;
        }
        if (!card.type.equals("Siege")) {
            System.err.println("Card is not of type Siege");
            return;
        }
        int emptyIndex = findEmptyIndex(gameEnvironment.siegeRow);
        if (emptyIndex == -1) {
            System.err.println("Siege row is full");
            return;
        }
        gameEnvironment.siegeRow[emptyIndex] = card;
        ((ImageView) siegeRow.getChildren().get(emptyIndex)).setImage(cardNode.getImage());
        cardNode.setImage(null);
        gameEnvironment.inHandCards[cardIndex] = null;
        updateTotalScore();
        updateNumberRemainingCards();
        gameEnvironment.hasPlayedTurn = true;
    }

    public void passRound() {
        if (!gameEnvironment.hasPlayedTurn) {
            if (gameEnvironment.endRound) {
                endRound();
            }
            gameEnvironment.endRound = true;
        }
        if (gameEnvironment.hasPlayedTurn) gameEnvironment.endRound = false;
        gameEnvironment.swapGameEnvironmentFields();
        updateEverything();
        gameEnvironment.hasPlayedTurn = false;
        gameEnvironment.turnNumber++;
    }

    private void endRound() {
//        if(gameEnvironment.totalScore>gameEnvironment.enemyTotalScore){
//            gameEnvironment.crystalsNumber++;
//        }else {
//            gameEnvironment.enemyCrystalsNumber++;
//        }
//        if(gameEnvironment.turnNumber%2==1){
//            if(gameEnvironment.totalScore>gameEnvironment.enemyTotalScore){
//                System.out.println("Logged in user Won the round");
//            }else if(gameEnvironment.totalScore<gameEnvironment.enemyTotalScore){
//                System.out.println("Opponents Won the Round");
//            }else{
//                System.out.println("The round is a draw");
//            }
//        }else {
//            if(gameEnvironment.totalScore<gameEnvironment.enemyTotalScore){
//                System.out.println("Logged in user Won the round");
//            }else if(gameEnvironment.totalScore>gameEnvironment.enemyTotalScore){
//                System.out.println("Opponents Won the Round");
//            }else{
//                System.out.println("The round is a draw");
//            }
//        }
        String roundResult;
        if (gameEnvironment.totalScore > gameEnvironment.enemyTotalScore) {
            gameEnvironment.crystalsNumber++;
             roundResult = gameEnvironment.turnNumber%2==1 ? "Logged in user Won the round" : "Opponents Won the Round";
        } else if (gameEnvironment.totalScore < gameEnvironment.enemyTotalScore) {
            gameEnvironment.enemyCrystalsNumber++;
             roundResult = gameEnvironment.turnNumber%2==0 ? "Logged in user Won the round" : "Opponents Won the Round";
        } else {
            roundResult="The round is a draw";
            gameEnvironment.crystalsNumber++;
            gameEnvironment.enemyCrystalsNumber++;
        }
        System.out.println(roundResult);
        checkForEndGame();
        clearDeck();
    }

    private void clearDeck() {
    }

    private void checkForEndGame() {
    }


    public static class GameEnvironment {
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
}
