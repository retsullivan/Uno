import java.util.ArrayList;

public class Hand {

    //private Deck deck;
    private ArrayList<Card> hand = new ArrayList<Card>();

//    public Hand(Deck deck) {
//        this.deck = deck;
//    }

    public ArrayList<Card> getHand() {
        return hand;
    }
    public void setHand(ArrayList<Card> hand ) {
        this.hand = hand;
    }


    public void drawCard(Deck deck) {
        Card card = deck.draw();
        hand.add(card);
    }

    public void addCard(Card card){
        hand.add(card);
    }

    public void playCard(Card card, Deck deck){
        deck.getDiscardPile().add(card);
        hand.remove(card);
    }

    

}
