import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

//        Deck deck = new Deck();
//        System.out.println(deck.getDrawPile().size());
//        Hand hand = new Hand(deck);
//        ArrayList<Card> cards = hand.getStartingHand();
//
//        for (Card card: cards){
//            System.out.println(card.toString());
//        }
//
//        System.out.println(deck.getDrawPile().size());
//        System.out.println(deck.getDiscardPile().size());

        Game g = new Game();
        g.Play(g);


    }
}
