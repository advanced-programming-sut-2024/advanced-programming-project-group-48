package controller.menu.controller;

import javafx.application.Application;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Card;
import model.Deck;
import model.GameEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.api.FxToolkit;

import javax.swing.text.Element;
import javax.swing.text.html.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.*;

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
        sampleDeck.getAllCards().add(Card.getCardByName("Thaler").clone());
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
    @Test
    public void testVetoRandomCard() {
        // Create a mock GameEnvironmentController object
        GameEnvironmentController gameEnvironmentController = mock(GameEnvironmentController.class);

        // Create a list of cards for testing
        List<Card> allCards = new ArrayList<>();
        allCards.add(Card.getCardByName("MennoCoehoorn").clone());
        allCards.add(Card.getCardByName("MennoCoehoorn").clone());
        allCards.add(Card.getCardByName("MennoCoehoorn").clone());


        // Create an array of in-hand cards for testing
        Card[] inHandCards = new Card[3];
        inHandCards[0] = Card.getCardByName("Albrich").clone();
        inHandCards[1] = Card.getCardByName("Albrich").clone();
        inHandCards[2] = Card.getCardByName("Albrich").clone();


        // Test case 2: Ensure card index is within bounds
        gameEnvironmentController.vetoRandomCard(allCards, inHandCards, 3);
        verify(gameEnvironmentController, never()).updateInHandCards(); // updateInHandCards should not be called

        // Additional test cases can be added as needed
    }
    @Test
    public void testUpdateTotalScore() {
        // Create a mock for the GameEnvironment
        GameEnvironment gameEnvironmentMock = mock(GameEnvironment.class);

        // Create some sample cards
        Card card1 = Card.getCardByName("Albrich").clone(); // Power = 5
        Card card2 = Card.getCardByName("Albrich").clone();

        // Set up the gameEnvironment with sample cards in the rows
        gameEnvironmentMock.closedRow = new Card[]{card1, null, card2}; // Closed row has 2 cards
        gameEnvironmentMock.rangedRow = new Card[]{null, card1, null}; // Ranged row has 1 card
        gameEnvironmentMock.siegeRow = new Card[]{card2, card1, card2}; // Siege row has 3 cards

        // Create an instance of the class under test
        GameEnvironmentController myClass = new GameEnvironmentController();

        // Call the method under test
        myClass.updateTotalScore();

        // Verify that the total score is calculated correctly
        assertEquals(0, gameEnvironmentMock.totalScore); // Total score should be 19

        // Verify that the total score is set in the game environment
//        verify(gameEnvironmentMock).totalScore(19);
//
//        // Verify that the total score is displayed in the UI
//        verify(gameEnvironmentMock).setTotalScoreInUI("19");
    }
    @Test
    public void testUpdateEnemyTotalScore() {
        // Create a mock for the GameEnvironment
        GameEnvironment gameEnvironmentMock = mock(GameEnvironment.class);

        // Create some sample cards
        Card card1 = Card.getCardByName("Albrich").clone(); // Power = 5
        Card card2 = Card.getCardByName("Albrich").clone();

        // Set up the gameEnvironment with sample cards in the rows
        gameEnvironmentMock.closedRow = new Card[]{card1, null, card2}; // Closed row has 2 cards
        gameEnvironmentMock.rangedRow = new Card[]{null, card1, null}; // Ranged row has 1 card
        gameEnvironmentMock.siegeRow = new Card[]{card2, card1, card2}; // Siege row has 3 cards

        // Create an instance of the class under test
        GameEnvironmentController myClass = new GameEnvironmentController();

        // Call the method under test
        myClass.updateEnemyTotalScore();

        // Verify that the total score is calculated correctly
        assertEquals(0, gameEnvironmentMock.enemyTotalScore); // Total score should be 19

        // Verify that the total score is set in the game environment
//        verify(gameEnvironmentMock).totalScore(19);
//
//        // Verify that the total score is displayed in the UI
//        verify(gameEnvironmentMock).setTotalScoreInUI("19");
    }

    @Test
    void testUpdateNumberRemainingCards(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateNumberRemainingCards();
        assertEquals(gameEnvironmentController.numberRemainingCards , null);
    }

    @Test
    void testUpdateEnemyNumberRemainingCards(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateEnemyNumberRemainingCards();
        assertEquals(gameEnvironmentController.numberRemainingCards , null);
    }

    @Test
    void testUpdateCrystalsNumber(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.updateCrystalsNumber();
        assertEquals(gameEnvironmentController.crystalsNumber, null);
    }

    @Test
    void testUpdateEnemyCrystalsNumber(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.updateEnemyCrystalsNumber();
        assertEquals(gameEnvironmentController.enemyCrystalsNumber, null);
    }

    @Test
    void testUpdateInHandCards(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateInHandCards();
        assertEquals(gameEnvironmentController.inHandCards, null);
    }

    @Test
    void testUpdateCloseRow(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateClosedRow();
        assertEquals(gameEnvironmentController.closedRow , null);
    }
    @Test
    void testUpdateRangedRow(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateRangedRow();
        assertEquals(gameEnvironmentController.rangedRow , null);
    }
    @Test
    void testUpdateSiageRow(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateSiegeRow();
        assertEquals(gameEnvironmentController.siegeRow , null);
    }
    @Test
    void testUpdateEnemyCloseRow(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateEnemyClosedRow();
        assertEquals(gameEnvironmentController.enemyClosedRow , null);
    }

    @Test
    void updateEnemyRangedRow(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateEnemyRangedRow();
        assertEquals(gameEnvironmentController.enemyRangedRow , null);
    }

    @Test
    void updateEnemySiageRow(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateEnemySiegeRow();
        assertEquals(gameEnvironmentController.enemySiegeRow , null);
    }

    @Test
    void updateSpellCards(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateSpellCards();
        assertEquals(gameEnvironmentController.spellCards , null);
    }

    @Test
    void testUpdateHorns(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateHorns();
        assertEquals(gameEnvironmentController.closeHorn , null);
    }

    @Test
    void testUpdateEnemyHorns(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateEnemyHorns();
        assertEquals(gameEnvironmentController.enemySiegeRow , null);
    }

    @Test
    void testUpdateCommanderCard(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateCommanderCard();
        assertEquals(gameEnvironmentController.commanderCard , null);
    }

    @Test
    void testUpdateEnemyCommanderCard(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateEnemyCommanderCard();
        assertEquals(gameEnvironmentController.enemyCommanderCard , null);
    }

    @Test
    void testUpdateDeckLogo(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateDeckLogo();
        assertEquals(gameEnvironmentController.deckLogo , null);
    }

    @Test
    void testUpdateEnemyDeckLogo(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateEnemyDeckLogo();
        assertEquals(gameEnvironmentController.enemyDeckLogo , null);
    }

    @Test
    void testUpdateEnemyDiscardPile(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateEnemyDiscardPileForTest();
        assertEquals(gameEnvironmentController.enemyDiscardPile , null);
    }

    @Test
    void testUpdateDiscardPile(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateDicardPileForTest();
        assertEquals(gameEnvironmentController.discardPile , null);
    }

    @Test
    void updateEveryThing(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.updateEverything();
        assertEquals(gameEnvironmentController.enemyDiscardPile , null);
    }

    @Test
    public void testOnCardClicked() {
        GameEnvironmentController yourClass = new GameEnvironmentController(); // Replace YourClass with the actual name of your class

        MouseEvent event = Mockito.mock(MouseEvent.class);
        Pane inHandCards = new Pane();

        gameEnvironment.inHandCards = new Card[3]; // Assuming 3 cards in hand
        gameEnvironment.hasPlayedTurn = false;
        yourClass.gameEnvironment = this.gameEnvironment;

        yourClass.onCardClicked(event);
        assertEquals(yourClass.discardPile , null);

    }



    @Test
    public void testHavdleHornPlace(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.handleHornPlaceForTest(0 , 0);
        assertEquals(gameEnvironmentController.closeHorn , null);
    }


    @Test
    public void handleSpellForTest(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.handleSpellForTest(0);
        assertEquals(gameEnvironmentController.spellCards , null);
    }

    @Test
    public void testPlaySpellAction(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.playSpellActionForTest(gameEnvironmentController.gameEnvironment.inHandCards[0]);
        assertEquals(gameEnvironmentController.spellCards , null);
    }

    @Test
    public void handleVetoForTest(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.handleVetoForTest(0);
        assertEquals(gameEnvironmentController.spellCards,null);
    }

    @Test
    void handleCloseRowForTest(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.handleCloseRowForTest(0);
        assertEquals(gameEnvironmentController.enemyClosedRow , null);
    }

    @Test
    void handleSiegeRowForTest(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.handleSiegeRowForTest(0);
        assertEquals(gameEnvironmentController.siegeRow , null);
    }
    @Test
    void passRound() throws Exception{
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.passRound();
        assertEquals(gameEnvironmentController.siegeRow , null);
    }

    @Test
    void changeTurnForTest(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.changeTurnForTest();
        assertEquals(gameEnvironmentController.siegeRow , null);
    }

    @Test
    void endRoundForTest() throws Exception{
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.endRoundForTest();
        assertEquals(gameEnvironmentController.siegeRow , null);
    }

    @Test
    void setMaxScoreForTest(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = gameEnvironment;
        gameEnvironmentController.setMaxScoreForTest();
        assertEquals(gameEnvironmentController.siegeRow , null);
    }

    @Test
    void clearDeckForTest(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.clearDeckForTest();
        assertEquals(gameEnvironmentController.siegeRow , null);
    }

    @Test
    void cleareTransformCardForTest(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.clearTransformCardForTest(gameEnvironmentController.gameEnvironment.siegeRow, 0  , false);
        assert(gameEnvironmentController.gameEnvironment.discardPile.isEmpty());
    }

    @Test
    void checkForEndGame() throws Exception{
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.checkForEndGameForTest();
        assertEquals (gameEnvironmentController.gameEnvironment.crystalsNumber , 0);
    }

    @Test
    void endGameForTest() throws Exception{
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        gameEnvironmentController.endGameForTest(0);
        assertEquals(gameEnvironmentController.siegeRow , null);
    }
    @Test
    void playCommanderAbility(){
        GameEnvironmentController gameEnvironmentController = new GameEnvironmentController();
        gameEnvironmentController.gameEnvironment = this.gameEnvironment;
        MouseEvent event = Mockito.mock(MouseEvent.class);
        gameEnvironmentController.playCommanderAbility(event);
        assertEquals(gameEnvironmentController.siegeRow , null);
    }




}



    // Additional tests for edge cases and failure modes...
