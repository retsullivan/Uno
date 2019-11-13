import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    Deck deck = new Deck();

    @Test
    public void get_Cards_returns_correct_number_of_cards() {
        //arrange
        this.deck = deck;
        System.out.println(deck.getDrawPile().size());
        //act

        //assert

        assertTrue(deck.getDrawPile().size()==112);
    }

    @Test
    public void draw_returns_card_on_top_of_deck_and_reduces_deck_size_by_One(){
        //arrange
        this.deck = deck;
        var beforeSize = deck.getDrawPile().size();
        ArrayList<Card> cards =  deck.getDrawPile();
        //act
        //assert
        assertTrue(cards.get(0)==deck.draw());
        assertTrue(deck.getDrawPile().size()==beforeSize-1);
    }

    @Test
    public void add_card_to_discard_pile_increases_size_by_ONE(){
        //arrange
        this.deck = deck;
        var beforeSize = deck.getDiscardPile().size();

        //act
        deck.addCardToDiscardPile(new Card(Faces.Five, Colors.Red));
        deck.addCardToDiscardPile(new Card(Faces.Five, Colors.Red));

        //assert
        assertEquals(2,deck.getDiscardPile().size());
    }












}
