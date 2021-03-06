import com.improving.Card;
import com.improving.Colors;
import com.improving.Deck;
import com.improving.Faces;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    Deck deck = new Deck();

    @Test
    public void get_Cards_returns_correct_number_of_cards() {
        //arrange
        this.deck = deck;
        System.out.println(deck.allCardsInDeck.size());
        //act

        //assert

        assertTrue(deck.allCardsInDeck.size()==112);
    }

//    @Test
//    public void draw_returns_card_on_top_of_deck_and_reduces_deck_size_by_One(){
//        //arrange
//        this.deck = deck;
//        var beforeSize = deck.getDrawPile().size();
//        ArrayList<com.improving.Card> cards =  deck.getDrawPile();
//        //act
//        //assert
//        assertTrue(cards.get(0)==deck.draw());
//        assertTrue(deck.getDrawPile().size()==beforeSize-1);
//    } can't do this anymore since draw pile is should be private, but it worked when last I ran it

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
