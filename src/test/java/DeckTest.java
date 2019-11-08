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
    public void draw_returns_card_on_top_of_deck(){
        //arrange
        this.deck = deck;
        ArrayList<Card> cards =  deck.getDrawPile();
        //act
        //assert
        assertTrue(cards.get(0)==deck.draw());
    }











}
