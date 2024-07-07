package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardTest {
    Card card;
    @BeforeEach
    void setUp() {
        card = Card.getCardByName("Villentretenmerth");
    }

    @Test
    void testCardNoneNull() {
        assert card!=null;
        assert card.clone()!=null;
    }

}
