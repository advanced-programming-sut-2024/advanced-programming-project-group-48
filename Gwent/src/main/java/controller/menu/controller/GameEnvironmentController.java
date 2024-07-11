package controller.menu.controller;

import client.Client;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.*;
import view.menus.GameEnvironmentMenu;
import view.menus.MainMenu;

import java.util.*;

import static model.Card.*;


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

    public GameEnvironment gameEnvironment;

    public void initialize() {
        String json = Client.currentClient.receiveResponse();
        gameEnvironment = GameEnvironment.fromJson(json);
        updateEverything();
    }

    public static void drawRandomCards(List<Card> allCards, Card[] inHandCards, int numberOfCards) {
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
            inHandCards[findEmptyIndex(inHandCards)] = allCards.get(i); // Add card to the next available slot in the array
        }

        // Remove the added cards from the original deck
        for (int i = 0; i < cardsToAdd; i++) {
            allCards.remove(0);
        }
    }

    public void vetoRandomCard(List<Card> allCards, Card[] inHandCards, int cardIndex) {
        if (allCards.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Deck is empty");
            alert.setContentText("Cannot replace card.");
            alert.show();
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
//            updateInHandCards(); // Update the GUI to reflect the changes
            gameEnvironment.hasPlayedTurn = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Card index is out of bounds.");
            alert.setContentText("Card index is out of bounds.");
            alert.show();
            System.out.println("Card index is out of bounds.");
        }
    }

    public void updateEverything() {
        // Update in-hand cards for the player
        for (int i = 0; i < gameEnvironment.inHandCards.length; i++) {
            if (gameEnvironment.inHandCards[i] != null) {
                ((ImageView) inHandCards.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.deckLogo + "/" + gameEnvironment.inHandCards[i].name + ".jpg"))));
            } else {
                ((ImageView) inHandCards.getChildren().get(i)).setImage(null);
            }
        }

        // Update closed row for the player
        for (int i = 0; i < gameEnvironment.closedRow.length; i++) {
            if (gameEnvironment.closedRow[i] != null) {
                ((ImageView) closedRow.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.deckLogo + "/" + gameEnvironment.closedRow[i].name + ".jpg"))));
            } else {
                ((ImageView) closedRow.getChildren().get(i)).setImage(null);
            }
        }

        // Update ranged row for the player
        for (int i = 0; i < gameEnvironment.rangedRow.length; i++) {
            if (gameEnvironment.rangedRow[i] != null) {
                ((ImageView) rangedRow.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.deckLogo + "/" + gameEnvironment.rangedRow[i].name + ".jpg"))));
            } else {
                ((ImageView) rangedRow.getChildren().get(i)).setImage(null);
            }
        }

        // Update siege row for the player
        for (int i = 0; i < gameEnvironment.siegeRow.length; i++) {
            if (gameEnvironment.siegeRow[i] != null) {
                ((ImageView) siegeRow.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.deckLogo + "/" + gameEnvironment.siegeRow[i].name + ".jpg"))));
            } else {
                ((ImageView) siegeRow.getChildren().get(i)).setImage(null);
            }
        }

        // Update enemy closed row
        for (int i = 0; i < gameEnvironment.enemyClosedRow.length; i++) {
            if (gameEnvironment.enemyClosedRow[i] != null) {
                ((ImageView) enemyClosedRow.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.enemyDeckLogo + "/" + gameEnvironment.enemyClosedRow[i].name + ".jpg"))));
            } else {
                ((ImageView) enemyClosedRow.getChildren().get(i)).setImage(null);
            }
        }

        // Update enemy ranged row
        for (int i = 0; i < gameEnvironment.enemyRangedRow.length; i++) {
            if (gameEnvironment.enemyRangedRow[i] != null) {
                ((ImageView) enemyRangedRow.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.enemyDeckLogo + "/" + gameEnvironment.enemyRangedRow[i].name + ".jpg"))));
            } else {
                ((ImageView) enemyRangedRow.getChildren().get(i)).setImage(null);
            }
        }

        // Update enemy siege row
        for (int i = 0; i < gameEnvironment.enemySiegeRow.length; i++) {
            if (gameEnvironment.enemySiegeRow[i] != null) {
                ((ImageView) enemySiegeRow.getChildren().get(i)).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.enemyDeckLogo + "/" + gameEnvironment.enemySiegeRow[i].name + ".jpg"))));
            } else {
                ((ImageView) enemySiegeRow.getChildren().get(i)).setImage(null);
            }
        }

        // Update total score for the player
        int totalScoreValue = 0;
        for (Card card : gameEnvironment.closedRow) {
            if (card != null) {
                totalScoreValue += card.power;
            }
        }
        for (Card card : gameEnvironment.rangedRow) {
            if (card != null) {
                totalScoreValue += card.power;
            }
        }
        for (Card card : gameEnvironment.siegeRow) {
            if (card != null) {
                totalScoreValue += card.power;
            }
        }
        gameEnvironment.totalScore = totalScoreValue;
        this.totalScore.setText(String.valueOf(totalScoreValue));

        // Update enemy total score
        int enemyTotalScoreValue = 0;
        for (Card card : gameEnvironment.enemyClosedRow) {
            if (card != null) {
                enemyTotalScoreValue += card.power;
            }
        }
        for (Card card : gameEnvironment.enemyRangedRow) {
            if (card != null) {
                enemyTotalScoreValue += card.power;
            }
        }
        for (Card card : gameEnvironment.enemySiegeRow) {
            if (card != null) {
                enemyTotalScoreValue += card.power;
            }
        }
        gameEnvironment.enemyTotalScore = enemyTotalScoreValue;
        this.enemyTotalScore.setText(String.valueOf(enemyTotalScoreValue));

        // Update number of remaining cards for the player
        int numberRemainingCardsValue = 0;
        for (Card card : gameEnvironment.inHandCards) {
            if (card != null) {
                numberRemainingCardsValue++;
            }
        }
        this.numberRemainingCards.setText(String.valueOf(numberRemainingCardsValue));

        // Update number of remaining cards for the enemy
        int enemyNumberRemainingCardsValue = 0;
        for (Card card : gameEnvironment.enemyInHandCards) {
            if (card != null) {
                enemyNumberRemainingCardsValue++;
            }
        }
        this.enemyNumberRemainingCards.setText(String.valueOf(enemyNumberRemainingCardsValue));

        // Update crystals number for the player
        this.crystalsNumber.setText(String.valueOf(gameEnvironment.crystalsNumber));

        // Update enemy crystals number
        this.enemyCrystalsNumber.setText(String.valueOf(gameEnvironment.enemyCrystalsNumber));

        // Update commander card for the player
        commanderCard.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Leaders/" + gameEnvironment.commanderCard + ".jpg"))));

        // Update enemy commander card
        enemyCommanderCard.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Leaders/" + gameEnvironment.enemyCommanderCard + ".jpg"))));

        // Update deck logo for the player
        deckLogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icons/deck_back_" + gameEnvironment.deckLogo + ".jpg"))));

        // Update enemy deck logo
        enemyDeckLogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icons/deck_back_" + gameEnvironment.enemyDeckLogo + ".jpg"))));

        // Update discard pile for the player
        if (!gameEnvironment.discardPile.isEmpty()) {
            discardPile.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.deckLogo + "/" + gameEnvironment.discardPile.get(0).name + ".jpg"))));
        }

        // Update enemy discard pile
        if (!gameEnvironment.enemyDiscardPile.isEmpty()) {
            enemyDiscardPile.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.enemyDeckLogo + "/" + gameEnvironment.enemyDiscardPile.get(0).name + ".jpg"))));
        }

        // Update horns for the player
        if (gameEnvironment.closeHorn != null) {
            closeHorn.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.deckLogo + "/" + gameEnvironment.closeHorn.name + ".jpg"))));
        } else {
            closeHorn.setImage(null);
        }
        if (gameEnvironment.rangedHorn != null) {
            rangedHorn.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.deckLogo + "/" + gameEnvironment.rangedHorn.name + ".jpg"))));
        } else {
            rangedHorn.setImage(null);
        }
        if (gameEnvironment.siegeHorn != null) {
            siegeHorn.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.deckLogo + "/" + gameEnvironment.siegeHorn.name + ".jpg"))));
        } else {
            siegeHorn.setImage(null);
        }

        // Update horns for the enemy
        if (gameEnvironment.enemyCloseHorn != null) {
            enemyCloseHorn.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.enemyDeckLogo + "/" + gameEnvironment.enemyCloseHorn.name + ".jpg"))));
        } else {
            enemyCloseHorn.setImage(null);
        }
        if (gameEnvironment.enemyRangedHorn != null) {
            enemyRangedHorn.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.enemyDeckLogo + "/" + gameEnvironment.enemyRangedHorn.name + ".jpg"))));
        } else {
            enemyRangedHorn.setImage(null);
        }
        if (gameEnvironment.enemySiegeHorn != null) {
            enemySiegeHorn.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cards/" + gameEnvironment.enemyDeckLogo + "/" + gameEnvironment.enemySiegeHorn.name + ".jpg"))));
        } else {
            enemySiegeHorn.setImage(null);
        }
    }


    public void onCardClicked(MouseEvent event) {
        ImageView clickedCard = (ImageView) event.getSource();
        int cardIndex = inHandCards.getChildren().indexOf(clickedCard);
        if (gameEnvironment.inHandCards[cardIndex] == null) return;
        if (gameEnvironment.hasPlayedTurn) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Your turn is finished");
            alert.setContentText("You have played your turn please pass the round");
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

            MenuItem option5 = new MenuItem("Spell");
            option5.setOnAction(e -> handleSpell(cardIndex));

            MenuItem option6 = new MenuItem("Horn");
            option6.setOnAction(e -> handleHorn(cardIndex, event));


            contextMenu.getItems().addAll(option1, option2, option3, option4, option5, option6);

            contextMenu.show((ImageView) event.getSource(), event.getScreenX(), event.getScreenY());
        }
    }

    private void handleHorn(int cardIndex, MouseEvent event) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem option1 = new MenuItem("Closed Row");
        option1.setOnAction(e -> handleHornPlace(1, cardIndex));

        MenuItem option2 = new MenuItem("Ranged Row");
        option2.setOnAction(e -> handleHornPlace(2, cardIndex));

        MenuItem option3 = new MenuItem("Siege Row");
        option3.setOnAction(e -> handleHornPlace(3, cardIndex));


        contextMenu.getItems().addAll(option1, option2, option3);

        contextMenu.show((ImageView) event.getSource(), event.getScreenX(), event.getScreenY());
    }

    private void handleHornPlace(int row, int cardIndex) {
        gameEnvironment.recentPlaceCardRow = row;
        if (row == 1) {
            if (closeHorn != null) {
                System.err.println("closeHorn is not empty");
                return;
            }
            ImageView cardNode = (ImageView) inHandCards.getChildren().get(cardIndex);
            Card card = gameEnvironment.inHandCards[cardIndex];
            closeHorn.setImage(cardNode.getImage());
            gameEnvironment.closeHorn = card;
            playHorn(cardIndex, cardNode, card);
        }
        if (row == 2) {
            if (rangedHorn != null) {
                System.err.println("rangedHorn is not empty");
                return;
            }
            ImageView cardNode = (ImageView) inHandCards.getChildren().get(cardIndex);
            Card card = gameEnvironment.inHandCards[cardIndex];
            rangedHorn.setImage(cardNode.getImage());
            gameEnvironment.rangedHorn = card;
            playHorn(cardIndex, cardNode, card);

        }
        if (row == 3) {
            if (siegeHorn != null) {
                System.err.println("siegeHorn is not empty");
                return;
            }
            ImageView cardNode = (ImageView) inHandCards.getChildren().get(cardIndex);
            Card card = gameEnvironment.inHandCards[cardIndex];
            siegeHorn.setImage(cardNode.getImage());
            gameEnvironment.siegeHorn = card;
            playHorn(cardIndex, cardNode, card);
        }


    }

    private void playHorn(int cardIndex, ImageView cardNode, Card card) {
        cardNode.setImage(null);
        gameEnvironment.inHandCards[cardIndex] = null;
        gameEnvironment.hasPlayedTurn = true;
        if (card.name.equals("Mardroeme")) {
            Mardroeme.execute(gameEnvironment);
        }
        if (card.name.equals("CommandersHorn")) {
            CommandersHorn.execute(gameEnvironment);
        }
    }

    private void handleSpell(int cardIndex) {
        ImageView cardNode = (ImageView) inHandCards.getChildren().get(cardIndex);
        Card card = gameEnvironment.inHandCards[cardIndex];
        if (card == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Card is null");
            alert.setContentText("Card is null");
            alert.show();
            System.err.println("Card is null!");
            return;
        }
        if (!card.type.equals("Spell")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Card is not of type Spell");
            alert.setContentText("Card is not of type Spell");
            alert.show();
            System.err.println("Card is not of type Spell");
            return;
        }
        int emptyIndex = findEmptyIndex(gameEnvironment.spellCards);
        if (emptyIndex == -1) {
            System.err.println("spell is full");
            return;
        }
        playSpellAction(card);
        gameEnvironment.spellCards[emptyIndex] = card;
        ((ImageView) spellCards.getChildren().get(emptyIndex)).setImage(cardNode.getImage());
        cardNode.setImage(null);
        gameEnvironment.inHandCards[cardIndex] = null;
        updateEverything();
        gameEnvironment.hasPlayedTurn = true;

    }

    private void playSpellAction(Card card) {
        if (card.name.equals("ClearWeather")) {
            // Reset power for all rows
            resetPower();
        }
        if (card.name.equals("SkelligeStorm")) {
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.siegeRow[i] != null && !gameEnvironment.siegeRow[i].isHero &&
                        gameEnvironment.rangedRow[i] != null && !gameEnvironment.rangedRow[i].isHero) {
                    gameEnvironment.siegeRow[i].power = 1;
                    gameEnvironment.rangedRow[i].power = 1;
                }
                if (gameEnvironment.enemySiegeRow[i] != null && !gameEnvironment.enemySiegeRow[i].isHero &&
                        gameEnvironment.enemyRangedRow[i] != null && !gameEnvironment.enemyRangedRow[i].isHero) {
                    gameEnvironment.enemySiegeRow[i].power = 1;
                    gameEnvironment.enemyRangedRow[i].power = 1;
                }
            }
        }
        if (card.name.equals("TorrentialRain")) {
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.siegeRow[i] != null && !gameEnvironment.siegeRow[i].isHero) {
                    gameEnvironment.siegeRow[i].power = 1;
                }
                if (gameEnvironment.enemySiegeRow[i] != null && !gameEnvironment.enemySiegeRow[i].isHero) {
                    gameEnvironment.enemySiegeRow[i].power = 1;
                }
            }
        }
        if (card.name.equals("ImpenetrableFog")) {
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.rangedRow[i] != null && !gameEnvironment.rangedRow[i].isHero) {
                    gameEnvironment.rangedRow[i].power = 1;
                }
                if (gameEnvironment.enemyRangedRow[i] != null && !gameEnvironment.enemyRangedRow[i].isHero) {
                    gameEnvironment.enemyRangedRow[i].power = 1;
                }
            }
        }
        if (card.name.equals("BitingFrost")) {
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.closedRow[i] != null && !gameEnvironment.closedRow[i].isHero) {
                    gameEnvironment.closedRow[i].power = 1;
                }
                if (gameEnvironment.enemyClosedRow[i] != null && !gameEnvironment.enemyClosedRow[i].isHero) {
                    gameEnvironment.enemyClosedRow[i].power = 1;
                }
            }
        }
        if (card.name.equals("Scorch")) {

            int maxPowerOfAll = -1;
            int indexOfMaxPower = -1;
            Card[] row = null;
            for (int i = 0; i < 10; i++) {

                if (gameEnvironment.siegeRow[i] != null && !gameEnvironment.siegeRow[i].isHero) {
                    if (maxPowerOfAll < gameEnvironment.siegeRow[i].power) {
                        maxPowerOfAll = gameEnvironment.siegeRow[i].power;
                        indexOfMaxPower = i;
                        row = gameEnvironment.siegeRow;
                    }
                }
                if (gameEnvironment.closedRow[i] != null && !gameEnvironment.closedRow[i].isHero) {
                    if (maxPowerOfAll < gameEnvironment.closedRow[i].power) {
                        maxPowerOfAll = gameEnvironment.closedRow[i].power;
                        indexOfMaxPower = i;
                        row = gameEnvironment.closedRow;
                    }
                }
                if (gameEnvironment.rangedRow[i] != null && !gameEnvironment.rangedRow[i].isHero) {
                    if (maxPowerOfAll < gameEnvironment.rangedRow[i].power) {
                        maxPowerOfAll = gameEnvironment.rangedRow[i].power;
                        indexOfMaxPower = i;
                        row = gameEnvironment.rangedRow;
                    }
                }
                if (gameEnvironment.enemySiegeRow[i] != null && !gameEnvironment.enemySiegeRow[i].isHero) {
                    if (maxPowerOfAll < gameEnvironment.enemySiegeRow[i].power) {
                        maxPowerOfAll = gameEnvironment.enemySiegeRow[i].power;
                        indexOfMaxPower = i;
                        row = gameEnvironment.enemySiegeRow;
                    }
                }
                if (gameEnvironment.enemyRangedRow[i] != null && !gameEnvironment.enemyRangedRow[i].isHero) {
                    if (maxPowerOfAll < gameEnvironment.enemyRangedRow[i].power) {
                        maxPowerOfAll = gameEnvironment.enemyRangedRow[i].power;
                        indexOfMaxPower = i;
                        row = gameEnvironment.enemyRangedRow;
                    }
                }
                if (gameEnvironment.enemyClosedRow[i] != null && !gameEnvironment.enemyClosedRow[i].isHero) {
                    if (maxPowerOfAll < gameEnvironment.enemyClosedRow[i].power) {
                        maxPowerOfAll = gameEnvironment.enemyClosedRow[i].power;
                        indexOfMaxPower = i;
                        row = gameEnvironment.enemyClosedRow;
                    }
                }
            }
            if (indexOfMaxPower != -1 && maxPowerOfAll != -1 && row != null) {
                row[indexOfMaxPower] = null;
            }

        }
    }

    private void resetPower() {
        for (int i = 0; i < 10; i++) {
            if (gameEnvironment.siegeRow[i] != null && !gameEnvironment.siegeRow[i].isHero) {
                gameEnvironment.siegeRow[i].resetPower();
            }
            if (gameEnvironment.rangedRow[i] != null && !gameEnvironment.rangedRow[i].isHero) {
                gameEnvironment.rangedRow[i].resetPower();
            }
            if (gameEnvironment.closedRow[i] != null && !gameEnvironment.closedRow[i].isHero) {
                gameEnvironment.closedRow[i].resetPower();
            }
            if (gameEnvironment.enemySiegeRow[i] != null && !gameEnvironment.enemySiegeRow[i].isHero) {
                gameEnvironment.enemySiegeRow[i].resetPower();
            }
            if (gameEnvironment.enemyRangedRow[i] != null && !gameEnvironment.enemyRangedRow[i].isHero) {
                gameEnvironment.enemyRangedRow[i].resetPower();
            }
            if (gameEnvironment.enemyClosedRow[i] != null && !gameEnvironment.enemyClosedRow[i].isHero) {
                gameEnvironment.enemyClosedRow[i].resetPower();
            }
        }
    }

    private void handleVeto(int cardIndex) {
        if (gameEnvironment.numberOfVeto >= 2) {
            //Todo replace with alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Check veto");
            alert.setContentText("You can't veto more than 2 times");
            alert.show();
            System.err.println("You can't veto more than 2 times");
            return;
        }
        gameEnvironment.numberOfVeto++;
        vetoRandomCard(sampleDeck.getAllCards(), gameEnvironment.inHandCards, cardIndex);
    }


    private void handleClosedRow(int cardIndex) {
        ImageView cardNode = (ImageView) inHandCards.getChildren().get(cardIndex);
        Card card = gameEnvironment.inHandCards[cardIndex];
        if (card == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Card is null");
            alert.setContentText("Card is null");
            alert.show();
            System.err.println("Card is null!");
            return;
        }
        if ((!card.type.equals("Close") && !card.type.equals("Agile"))) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Card is not of type Close or Agile");
            alert.setContentText("Card is not of type Close or Agile");
            alert.show();
            System.err.println("Card is not of type Close or Agile");
            return;
        }
        if (card.ability.equals("Spy")) {
            int emptyIndex = findEmptyIndex(gameEnvironment.enemyClosedRow);
            if (emptyIndex == -1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Closed row is full");
                alert.setContentText("Closed row is full");
                alert.show();
                System.err.println("Closed row is full");
                return;
            }
            gameEnvironment.enemyClosedRow[emptyIndex] = card;
            ((ImageView) enemyClosedRow.getChildren().get(emptyIndex)).setImage(cardNode.getImage());
            cardNode.setImage(null);
            gameEnvironment.inHandCards[cardIndex] = null;
            card.performAction(gameEnvironment);
            gameEnvironment.recentPlaceCardRow = 1;
            drawRandomCards(gameEnvironment.deckUser.getAllCards(), gameEnvironment.inHandCards, 2);
            updateEverything();
            gameEnvironment.hasPlayedTurn = true;
            return;
        }
        int emptyIndex = findEmptyIndex(gameEnvironment.closedRow);
        if (emptyIndex == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Closed row is full");
            alert.setContentText("Closed row is full");
            alert.show();
            System.err.println("Closed row is full");
            return;
        }
        gameEnvironment.closedRow[emptyIndex] = card;
        ((ImageView) closedRow.getChildren().get(emptyIndex)).setImage(cardNode.getImage());
        cardNode.setImage(null);
        gameEnvironment.inHandCards[cardIndex] = null;
        card.performAction(gameEnvironment);
        gameEnvironment.recentPlaceCardRow = 1;
        updateEverything();
        gameEnvironment.hasPlayedTurn = true;
    }

    private static int findEmptyIndex(Card[] row) {
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Card is null");
            alert.setContentText("Card is null");
            alert.show();
            System.err.println("Card is null!");
            return;
        }
        if ((!card.type.equals("Ranged") && !card.type.equals("Agile"))) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Card is not of type Ranged or Agile");
            alert.setContentText("Card is not of type Ranged or Agile");
            alert.show();
            System.err.println("Card is not of type Ranged or Agile");
            return;
        }
        if (card.ability.equals("Spy")) {
            int emptyIndex = findEmptyIndex(gameEnvironment.enemyRangedRow);
            if (emptyIndex == -1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Closed row is full");
                alert.setContentText("Closed row is full");
                alert.show();
                System.err.println("Closed row is full");
                return;
            }
            gameEnvironment.enemyRangedRow[emptyIndex] = card;
            ((ImageView) enemyRangedRow.getChildren().get(emptyIndex)).setImage(cardNode.getImage());
            cardNode.setImage(null);
            gameEnvironment.inHandCards[cardIndex] = null;
            card.performAction(gameEnvironment);
            gameEnvironment.recentPlaceCardRow = 2;
            drawRandomCards(gameEnvironment.deckUser.getAllCards(), gameEnvironment.inHandCards, 2);
            updateEverything();
            gameEnvironment.hasPlayedTurn = true;
            return;
        }
        int emptyIndex = findEmptyIndex(gameEnvironment.rangedRow);
        if (emptyIndex == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Ranged row is full");
            alert.setContentText("Ranged row is full");
            alert.show();
            System.err.println("Ranged row is full");
            return;
        }
        gameEnvironment.rangedRow[emptyIndex] = card;
        ((ImageView) rangedRow.getChildren().get(emptyIndex)).setImage(cardNode.getImage());
        cardNode.setImage(null);
        gameEnvironment.inHandCards[cardIndex] = null;
        card.performAction(gameEnvironment);
        gameEnvironment.recentPlaceCardRow = 2;
        updateEverything();
        gameEnvironment.hasPlayedTurn = true;
    }

    private void handleSiegeRow(int cardIndex) {
        ImageView cardNode = (ImageView) inHandCards.getChildren().get(cardIndex);
        Card card = gameEnvironment.inHandCards[cardIndex];
        if (card == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Card is null!");
            alert.setContentText("Card is null!");
            alert.show();
            System.err.println("Card is null!");
            return;
        }
        if (!card.type.equals("Siege")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Card is not of type Siege");
            alert.setContentText("Card is not of type Siege");
            alert.show();
            System.err.println("Card is not of type Siege");
            return;
        }
        if (card.ability.equals("Spy")) {
            int emptyIndex = findEmptyIndex(gameEnvironment.enemySiegeRow);
            if (emptyIndex == -1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Closed row is full");
                alert.setContentText("Closed row is full");
                alert.show();
                System.err.println("Closed row is full");
                return;
            }
            gameEnvironment.enemySiegeRow[emptyIndex] = card;
            ((ImageView) enemySiegeRow.getChildren().get(emptyIndex)).setImage(cardNode.getImage());
            cardNode.setImage(null);
            gameEnvironment.inHandCards[cardIndex] = null;
            card.performAction(gameEnvironment);
            gameEnvironment.recentPlaceCardRow = 3;
            drawRandomCards(gameEnvironment.deckUser.getAllCards(), gameEnvironment.inHandCards, 2);
            updateEverything();
            gameEnvironment.hasPlayedTurn = true;
            return;
        }
        int emptyIndex = findEmptyIndex(gameEnvironment.siegeRow);
        if (emptyIndex == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Siege row is full");
            alert.setContentText("Siege row is full");
            alert.show();
            System.err.println("Siege row is full");
            return;
        }
        gameEnvironment.siegeRow[emptyIndex] = card;
        ((ImageView) siegeRow.getChildren().get(emptyIndex)).setImage(cardNode.getImage());
        cardNode.setImage(null);
        gameEnvironment.inHandCards[cardIndex] = null;
        card.performAction(gameEnvironment);
        gameEnvironment.recentPlaceCardRow = 3;
        updateEverything();
        gameEnvironment.hasPlayedTurn = true;
    }

    public void passRound() throws Exception {
        if (!gameEnvironment.hasPlayedTurn) {
            if (gameEnvironment.endRound) {
                endRound();
                return;
            }
            gameEnvironment.endRound = true;
        }
        if (gameEnvironment.hasPlayedTurn) gameEnvironment.endRound = false;
        updateEverything();
        gameEnvironment.hasPlayedTurn = false;
        gameEnvironment.turnNumber++;
        Client.currentClient.sendMessage(gameEnvironment.toJson());
        String response = Client.currentClient.receiveResponse();
        if (!response.equals("end")) {
            gameEnvironment = GameEnvironment.fromJson(response);
            updateEverything();
        } else {
            endRound();
        }
    }

    private void changeTurn() {
        gameEnvironment.swapGameEnvironmentFields();
        updateEverything();
        gameEnvironment.hasPlayedTurn = false;
        gameEnvironment.turnNumber++;
    }

    private void endRound() throws Exception {
        int temp = gameEnvironment.turnNumber % 2;
        for (int i = 0; i < temp + 1; i++) {
            changeTurn();
        }
        String roundResult;
        if (gameEnvironment.totalScore > gameEnvironment.enemyTotalScore) {
            gameEnvironment.crystalsNumber++;
            roundResult = "Logged in user Won the round";
        } else if (gameEnvironment.totalScore < gameEnvironment.enemyTotalScore) {
            gameEnvironment.enemyCrystalsNumber++;
            roundResult = "Opponents Won the Round";
        } else {
            roundResult = "The round is a draw";
            gameEnvironment.crystalsNumber++;
            gameEnvironment.enemyCrystalsNumber++;
        }

        if (gameEnvironment.round1Score == -1) {
            gameEnvironment.round1Score = gameEnvironment.totalScore;
            gameEnvironment.enemyRound1Score = gameEnvironment.enemyTotalScore;
        } else if (gameEnvironment.round2Score == -1) {
            gameEnvironment.round2Score = gameEnvironment.totalScore;
            gameEnvironment.enemyRound2Score = gameEnvironment.enemyTotalScore;
        } else {
            gameEnvironment.round3Score = gameEnvironment.totalScore;
            gameEnvironment.enemyRound3Score = gameEnvironment.enemyTotalScore;
        }

        System.out.println(roundResult);
        setMaxScores();
        checkForEndGame();
        clearDeck();
    }

    private void setMaxScores() {
        User.loggedInUser.setMaxScore(gameEnvironment.totalScore);
        GameEnvironmentMenu.currentGame.opponentUser.setMaxScore(gameEnvironment.enemyTotalScore);
    }

    private void clearDeck() {
        for (int i = 0; i < gameEnvironment.closedRow.length; i++) {

            clearOrTransformCard(gameEnvironment.closedRow, i, false);
            clearOrTransformCard(gameEnvironment.enemyClosedRow, i, true);
            clearOrTransformCard(gameEnvironment.rangedRow, i, false);
            clearOrTransformCard(gameEnvironment.enemyRangedRow, i, true);
            clearOrTransformCard(gameEnvironment.siegeRow, i, false);
            clearOrTransformCard(gameEnvironment.enemySiegeRow, i, true);
            clearOrTransformCard(gameEnvironment.inHandCards, i, false);
            clearOrTransformCard(gameEnvironment.enemyInHandCards, i, true);

        }
        for (int i = 0; i < 3; i++) {
            if (gameEnvironment.spellCards[i] != null) {
                gameEnvironment.discardPile.add(gameEnvironment.spellCards[i]);
                gameEnvironment.spellCards[i] = null;
            }
        }
        gameEnvironment.totalScore = 0;
        gameEnvironment.enemyTotalScore = 0;
        gameEnvironment.numberOfVeto = 0;
        gameEnvironment.enemyNumberOfVeto = 0;
        gameEnvironment.enemySiegeHorn = null;
        gameEnvironment.siegeHorn = null;
        gameEnvironment.enemyCloseHorn = null;
        gameEnvironment.closeHorn = null;
        gameEnvironment.enemyRangedHorn = null;
        gameEnvironment.rangedHorn = null;
        gameEnvironment.hasPlayedTurn = false;
        gameEnvironment.endRound = false;
        gameEnvironment.hasPlayedCommander = false;
        gameEnvironment.hasPlayedEnemyCommander = false;
        gameEnvironment.turnNumber = 1;
        drawRandomCards(sampleDeck.getAllCards(), gameEnvironment.inHandCards, 10);
        drawRandomCards(enemySampleDeck.getAllCards(), gameEnvironment.enemyInHandCards, 10);
        updateEverything();
    }

    private void clearOrTransformCard(Card[] row, int index, boolean isEnemy) {
        if (row[index] != null) {
            if ("Transformers".equals(row[index].ability) && row[index].power != 8) {
                row[index].power = 8;
            } else {
                if (isEnemy) {
                    gameEnvironment.enemyDiscardPile.add(row[index]);
                } else {
                    gameEnvironment.discardPile.add(row[index]);
                }
                row[index] = null;
            }
        }
    }

    private void checkForEndGame() throws Exception {
        if (gameEnvironment.crystalsNumber == gameEnvironment.enemyCrystalsNumber && gameEnvironment.crystalsNumber >= 2) {
            System.out.println("The game is a draw");
            endGame(2);
        } else if ((gameEnvironment.crystalsNumber == 2 && gameEnvironment.turnNumber % 2 == 1) ||
                (gameEnvironment.enemyCrystalsNumber == 2 && gameEnvironment.turnNumber % 2 == 0)) {
            System.out.println("Logged in user Won the game");
            endGame(0);
        } else if ((gameEnvironment.enemyCrystalsNumber == 2 && gameEnvironment.turnNumber % 2 == 1) ||
                (gameEnvironment.crystalsNumber == 2 && gameEnvironment.turnNumber % 2 == 0)) {
            System.out.println("Opponents Won the game");
            endGame(1);
        }

    }

    private void endGame(int status) throws Exception {
        GameHistory gameHistory = new GameHistory(gameEnvironment.round1Score, gameEnvironment.round2Score, gameEnvironment.round3Score,
                gameEnvironment.enemyRound1Score, gameEnvironment.enemyRound2Score, gameEnvironment.enemyRound3Score, new Date(), GameEnvironmentMenu.currentGame.opponentUser.getUsername());
        User.loggedInUser.addGameHistory(gameHistory);
        GameHistory gameHistoryOpponent = new GameHistory(gameEnvironment.enemyRound1Score, gameEnvironment.enemyRound2Score, gameEnvironment.enemyRound3Score,
                gameEnvironment.round1Score, gameEnvironment.round2Score, gameEnvironment.round3Score, new Date(), User.loggedInUser.getUsername());
        GameEnvironmentMenu.currentGame.opponentUser.addGameHistory(gameHistoryOpponent);
        if (status == 0) {
            User.loggedInUser.increaseNumWins();
            GameEnvironmentMenu.currentGame.opponentUser.increaseNumLoses();
        }
        if (status == 1) {
            User.loggedInUser.increaseNumLoses();
            GameEnvironmentMenu.currentGame.opponentUser.increaseNumWins();
        }
        if (status == 2) {
            User.loggedInUser.increaseNumDraws();
            GameEnvironmentMenu.currentGame.opponentUser.increaseNumDraws();
        }
        User.loggedInUser.increaseNumTotalGames();
        GameEnvironmentMenu.currentGame.opponentUser.increaseNumTotalGames();
        GameEnvironmentMenu.appStage.close();
        GameEnvironmentMenu.appStage = null;
        new MainMenu().start(new Stage());

    }


    public void playCommanderAbility() {
        if (Faction.getCommandersOfNorthernRealms().equals("TheSiegemaster")) {
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.inHandCards[i].name.equals("ImpenetrableFog")) {
                    playSpellAction(Card.getCardByName("ImpenetrableFog"));
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("You don't have ImpenetrableFog card");
                    alert.setContentText("ImpenetrableFog doesn't exist in your inHand cards. So you can't use your commander ability");
                    alert.showAndWait();
                    System.err.println("You don't have ImpenetrableFog card");
                }
            }
        }
        if (Faction.getCommandersOfNorthernRealms().equals("KingOfTemeria")) {
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.siegeHorn.name.equals("CommandersHorn")) {
                    gameEnvironment.siegeRow[i].power += 0;
                } else {
                    gameEnvironment.siegeRow[i].power *= 2;
                }
            }
        }
        if (Faction.getCommandersOfNorthernRealms().equals("LordCommanderOfTheNorth")) {
            int sumOfPowerOfEnemySiegeRowCards = 0;
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.enemySiegeRow[i] != null && !gameEnvironment.enemySiegeRow[i].isHero) {
                    sumOfPowerOfEnemySiegeRowCards += gameEnvironment.enemySiegeRow[i].power;
                }
            }
            if (sumOfPowerOfEnemySiegeRowCards >= 10) {
                int indexStrongestCard = -1;
                int maxPower = 0;
                for (int i = 0; i < 10; i++) {
                    if (gameEnvironment.enemySiegeRow[i] != null && !gameEnvironment.enemySiegeRow[i].isHero) {
                        if (gameEnvironment.enemySiegeRow[i].power > maxPower) {
                            maxPower = gameEnvironment.enemySiegeRow[i].power;
                            indexStrongestCard = i;
                        }
                    }
                }
                gameEnvironment.enemySiegeRow[indexStrongestCard] = null;
            }
        }
        if (Faction.getCommandersOfNorthernRealms().equals("SonOfMedell")) {
            int sumOfPowerOfEnemySiegeRowCards = 0;
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.enemyRangedRow[i] != null && !gameEnvironment.enemyRangedRow[i].isHero) {
                    sumOfPowerOfEnemySiegeRowCards += gameEnvironment.enemyRangedRow[i].power;
                }
            }
            if (sumOfPowerOfEnemySiegeRowCards >= 10) {
                int indexStrongestCard = -1;
                int maxPower = 0;
                for (int i = 0; i < 10; i++) {
                    if (gameEnvironment.enemyRangedRow[i] != null && !gameEnvironment.enemyRangedRow[i].isHero) {
                        if (gameEnvironment.enemyRangedRow[i].power > maxPower) {
                            maxPower = gameEnvironment.enemyRangedRow[i].power;
                            indexStrongestCard = i;
                        }
                    }
                }
                gameEnvironment.enemySiegeRow[indexStrongestCard] = null;
            }
        }
        if (Faction.getCommandersOfNilfgaardianEmpire().equals("TheWhiteFlame")) {
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.inHandCards[i] != null && gameEnvironment.inHandCards[i].name.equals("Torrential Rain")) {
                    playSpellAction(Card.getCardByName("TorrentialRain"));
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("You don't have TorrentialRain card");
                    alert.setContentText("TorrentialRain doesn't exist in your inHand cards. So you can't use your commander ability");
                    alert.showAndWait();
                    System.err.println("You don't have TorrentialRain card");
                }
            }
        }

        if (Faction.getCommandersOfNilfgaardianEmpire().equals("EmperorOfNilfgaard")) {
            if (gameEnvironment.commanderCard.equals("EmperorOfNilfgaard")) {
                gameEnvironment.hasPlayedEnemyCommander = true;
            }
            if (gameEnvironment.enemyCommanderCard.equals("EmperorOfNilfgaard")) {
                gameEnvironment.hasPlayedCommander = true;
            }
        }
        if (Faction.getCommandersOfNilfgaardianEmpire().equals("InvaderOfTheNorth")) {
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.inHandCards[i] == null) {
                    if (gameEnvironment.discardPile.get(0) != null) {
                        gameEnvironment.inHandCards[i] = gameEnvironment.discardPile.get(0);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("You don't have discardPile card");
                        alert.setContentText("discardPile doesn't exist. So you can't use your commander ability");
                        alert.showAndWait();
                        System.err.println("You don't have discardPile card");
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Your inHand cards are full");
                    alert.setContentText("Your inHand cards are full. So you can't use your commander ability");
                    alert.showAndWait();
                    System.err.println("Your inHand cards are full");
                }
            }
        }
        if (Faction.getCommandersOfMonsters().equals("BringerOfDeath")) {
            if (gameEnvironment.closeHorn == null) {
                for (int i = 0; i < 10; i++) {
                    if (gameEnvironment.closedRow[i] != null) {
                        gameEnvironment.closedRow[i].power *= 2;
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Commader's Horn is in closed horn");
                alert.setContentText("Commader's Horn is in closed horn. So you can't use your commander ability");
                alert.showAndWait();
                System.err.println("Commader's Horn is in closed horn");
            }
        }
        if (Faction.getCommandersOfMonsters().equals("KingOfTheWildHunt")) {
            // it's not completed.
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.inHandCards[i] == null) {
                    for (int j = 0; j < Card.discardPile.size(); j++) {
                        if (!gameEnvironment.discardPile.get(j).isHero && gameEnvironment.discardPile.get(j) != null) {
                            gameEnvironment.inHandCards[i] = gameEnvironment.discardPile.get(j);
                        }
                        break;
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Your inHand cards are full");
                    alert.setContentText("Your inHand cards are full. So you can't use your commander ability");
                    alert.showAndWait();
                    System.err.println("Your inHand cards are full");
                }
                break;
            }
        }
        if (Faction.getCommandersOfScoiaTaell().equals("QueenOfDolBlathanna")) {
            int sumOfPowerOfEnemyClosedRowCards = 0;
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.enemyRangedRow[i] != null && !gameEnvironment.enemyRangedRow[i].isHero) {
                    sumOfPowerOfEnemyClosedRowCards += gameEnvironment.enemyClosedRow[i].power;
                }
            }
            if (sumOfPowerOfEnemyClosedRowCards >= 10) {
                int indexStrongestCard = -1;
                int maxPower = 0;
                for (int i = 0; i < 10; i++) {
                    if (gameEnvironment.enemyRangedRow[i] != null && !gameEnvironment.enemyRangedRow[i].isHero) {
                        if (gameEnvironment.enemyRangedRow[i].power > maxPower) {
                            maxPower = gameEnvironment.enemyRangedRow[i].power;
                            indexStrongestCard = i;
                        }
                    }
                }
                gameEnvironment.enemyRangedRow[indexStrongestCard] = null;
            }
        }
        if (Faction.getCommandersOfScoiaTaell().equals("TheBeautiful")) {
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.rangedHorn == null) {
                    gameEnvironment.rangedRow[i].power *= 2;
                } else if (!Objects.equals(gameEnvironment.rangedHorn.name, "CommandersHorn")) {
                    gameEnvironment.rangedRow[i].power *= 2;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("CommandersHorn founded.");
                    alert.setContentText("you have CommandersHorn in your horn. So you can't use your commander ability");
                    alert.showAndWait();
                    System.err.println("CommandersHorn founded");
                }
            }
        }
        if (Faction.getCommandersOfScoiaTaell().equals("PurebloodElf")) {
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.inHandCards != null && gameEnvironment.inHandCards[i].name == "BitingFrost") {
                    playSpellAction(Card.getCardByName("BitingFrost"));
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("BitingFrost not found");
                    alert.setContentText("You don't have BitingFrost card. So you can't use your commander ability");
                    alert.showAndWait();
                    System.err.println("BitingFrost not found");
                }
            }
        }
        if (Faction.getCommandersOfNorthernRealms().equals("TheSteelForged")) {
            resetPower();
        }
        if (Faction.getCommandersOfNilfgaardianEmpire().equals("The Relentless")) {
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.inHandCards[i] == null) {
                    if (gameEnvironment.enemyDiscardPile.get(0) != null)
                        gameEnvironment.inHandCards[i] = gameEnvironment.enemyDiscardPile.get(0);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("You don't have discard pile");
                    alert.setContentText("You don't have discard pile. So you can't use your commander ability");
                    alert.showAndWait();
                    System.err.println("You don't have discard pile");
                }
            }
        }
        if (Faction.getCommandersOfMonsters().equals("CommanderOfTheRedRiders")) {
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.inHandCards[i] != null) {
                    if (Objects.equals(gameEnvironment.inHandCards[i].name, "BitingFrost") &&
                            Objects.equals(gameEnvironment.inHandCards[i].name, "ImpenetrableFog") &&
                            Objects.equals(gameEnvironment.inHandCards[i].name, "TorrentialRain") &&
                            Objects.equals(gameEnvironment.inHandCards[i].name, "Skellige Storm") &&
                            Objects.equals(gameEnvironment.inHandCards[i].name, "Clear Weather")
                    ) {
                        for (int j = 0; j < 3; j++) {
                            if (gameEnvironment.spellCards[j] == null) {
                                gameEnvironment.inHandCards[i] = gameEnvironment.spellCards[j];
                            }
                        }
                    }
                }
            }
        }
        if (Faction.getCommandersOfMonsters().equals("TheTreacherous")) {
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.inHandCards != null) {
                    if (gameEnvironment.inHandCards[i].ability == "Spy") {
                        gameEnvironment.inHandCards[i].power *= 2;
                    }
                }
            }
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.siegeRow[i] != null) {
                    if (gameEnvironment.siegeRow[i].ability == "Spy") {
                        gameEnvironment.siegeRow[i].power *= 2;
                    }
                }
            }
            for (int i = 0; i < 10; i++) {
                if (gameEnvironment.closedRow != null) {
                    if (gameEnvironment.closedRow[i].ability == "Spy") {
                        gameEnvironment.closedRow[i].power *= 2;
                    }
                }
            }
        }
        if (Faction.getCommandersOfSkellige().equals("CrachAnCraite")) {
            for (int i = 0; i < gameEnvironment.discardPile.size(); i++) {
                gameEnvironment.deckUser.getAllCards().add(gameEnvironment.discardPile.get(i));
            }
        }
    }

    public void playCommanderAbility(MouseEvent mouseEvent) {
        if (gameEnvironment.hasPlayedCommander || gameEnvironment.hasPlayedTurn) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("You have already played your commander ability");
            alert.setContentText("You have already played your commander ability");
            alert.showAndWait();
            System.err.println("You have already played your commander ability");
            return;
        }
        gameEnvironment.hasPlayedCommander = true;
        playCommanderAbility();
    }
}
