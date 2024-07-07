package controller.menu.controller;

import model.Card;
import model.Deck;
import model.GameEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static controller.menu.controller.GameEnvironmentController.drawRandomCards;
import static org.junit.jupiter.api.Assertions.*;

class GameEnvironmentControllerTest {

    public Deck sampleDeck = new Deck();
    public Deck enemySampleDeck = new Deck();
    public GameEnvironment gameEnvironment;



    @BeforeEach
    void setUp() {
        sampleDeck.setCommander("KingOfTemperia");
        sampleDeck.setFaction("NorthernRealms");
        sampleDeck.getAllCards().add(Card.getCardByName("PoorFuckingInfantry").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("PoorFuckingInfantry").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("PoorFuckingInfantry").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("Thaler").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("Thaler").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("Thaler").clone());
        sampleDeck.getAllCards().add(Card.getCardByName("BitingFrost").clone());
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
        enemySampleDeck.getAllCards().add(Card.getCardByName("BitingFrost").clone());
        enemySampleDeck.getAllCards().add(Card.getCardByName("Trebuchet").clone());
        enemySampleDeck.getAllCards().add(Card.getCardByName("Trebuchet").clone());
        enemySampleDeck.getAllCards().add(Card.getCardByName("Trebuchet").clone());
        gameEnvironment= new GameEnvironment(sampleDeck, enemySampleDeck);

    }

    @Test
    void testCardNoneNull() {
        for(Card card:sampleDeck.getAllCards()){
            assert card!=null;
        }
        for(Card card:enemySampleDeck.getAllCards()){
            assert card!=null;
        }

    }

    @Test
    void testDrawRandomCard() {
        // Number of cards to draw
        int cardsToDraw = 5;

        // Call the method under test
        drawRandomCards(sampleDeck.getAllCards(), gameEnvironment.inHandCards, cardsToDraw);
        int countDrawnCards = 0;
        for (Card card : gameEnvironment.inHandCards) {
            if (card != null) {
                countDrawnCards++;
            }
        }
        assert countDrawnCards == cardsToDraw;
        drawRandomCards(sampleDeck.getAllCards(), gameEnvironment.inHandCards, 5);
        for (Card card : gameEnvironment.inHandCards) {
            assert card != null;
        }
        assert gameEnvironment.deckUser.getAllCards().isEmpty();
    }



    // Additional tests for edge cases and failure modes...
}