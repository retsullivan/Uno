import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HandTest {

    Deck deck = new Deck();
    Hand hand = new Hand(deck);

    @Test
    public void getStartingHand_returns_7_cards(){
        //arrange
        this.deck = deck;
        this.hand = hand;

        //act
        ArrayList<Card> startingHand = hand.getStartingHand();
        //assert
        assertTrue(startingHand.size()==7);
    }

    @Test
    public void drawCard_returns_top_Card(){
        //arrange
        this.deck = deck;
        this.hand = hand;
        //act
        Card top = deck.getDrawPile().get(0);
        hand.drawCard();
        //assert
        assertTrue(hand.getHand().get(0).equals(top));
    }

    @Test
    public void drawCard_removes_and_adds_Correctly(){
        //arrange
        this.deck = deck;
        var drawPileSize = deck.getDrawPile().size();
        this.hand = hand;
        var handsize = hand.getHand().size();
        //act

        hand.drawCard();
        var newHandSize = hand.getHand().size();
        var newDrawPileSize = deck.getDrawPile().size();
        //assert
        assertTrue(drawPileSize-1==newDrawPileSize
                && handsize+1==newHandSize);
    }


}
