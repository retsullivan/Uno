import java.util.ArrayList;

public class Hand {

    private Deck deck;
    private ArrayList<Card> hand = new ArrayList<Card>();

    public Hand(Deck deck) {
        this.deck = deck;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
    public void setHand() {
        this.hand = hand;
    }


    public void drawCard() {
        Card card = deck.draw();
        hand.add(card);
    }

    public void addCard(Card card){
        hand.add(card);
    }

    public void playCard(Card card){
        deck.getDiscardPile().add(card);
        hand.remove(card);
    }

    public ArrayList<Card> getStartingHand() {
        deck.shuffle(deck.getDrawPile());
        for (int i = 0; i < 7; i++) {
            hand.add(deck.draw());
        }
        return hand;
    }


}
