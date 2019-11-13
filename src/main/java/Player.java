import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {

    private ArrayList<Card> hand = new ArrayList<>();
    boolean skipThisTurn = false;
    boolean turnTaken = false;


    public boolean isSkipThisTurn() {
        return skipThisTurn;
    }

    public void setSkipThisTurn(boolean skipThisTurn) {
        this.skipThisTurn = skipThisTurn;
    }


    public Player() {
    }

    public Player(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public int getHandSize() {
        return hand.size();
    }


    public Card takeTurn(Game game) { //for now we just need the top card Card topCard
            Card playedCard = new Card();
            //inspect hand for things that match the top card
            //extract this to a card playable method?
            boolean cardPlayed = false;
            for (Card card : hand) {
                if (cardPlayed == false) {
                    if (game.isPlayable(card, game.getTopCard())) {
                        playCard(card, game);
                        cardPlayed = true;
                        playedCard=card;
                        break;
                    }
                }
            }
            //if no cards were playable, draw a card and play if it you can
            if (cardPlayed == false) {
                Card card = drawCard(game);
                if (game.isPlayable(card, game.getTopCard())) {
                    playCard(card, game);
                    System.out.println(card.toString() + "played on" + game.getTopCard().getCard().toString());
                    cardPlayed = true;
                    playedCard=card;
                } else {
                    hand.add(card);
                }
            }
            //play first playable card, or else draw a card until you can play
            //if hand.size ==1, sout "uno"
//            if (hand.size() == 1) {
//                System.out.println("UNO!");
//            }
            //if hand.size ==0
            return playedCard;
        }



        public Card drawCard(Game game) {
            Card card = game.getDeck().draw();
            return card;
        }

        public Card playCard(Card card, Game game) {
            Colors color = declareColor(card, game);
            game.playCard(card, color);
            hand.remove(card);
            return card;
        }


        public Colors declareColor(Card card, Game game) {
//            HashMap<Colors, Integer> discardColorCount = new HashMap<>();
//            for (Card discard : game.getDeck().getDiscardPile()) {
//                if (discard.getColor().toString().equalsIgnoreCase("wild") == false) {
//                    if (discardColorCount.containsKey(discard.getColor())) {
//                        discardColorCount.put(discard.getColor(), discardColorCount.get(discard.getColor()) + 1);
//                    } else {
//                        discardColorCount.put(card.getColor(), 1);
//                    }
//                }
//            }
            var declaredColor = card.getColor();
            if (card.getColor().toString().equalsIgnoreCase("wild")){
                declaredColor = Colors.Red;
            }

//            for (Colors color : discardColorCount.keySet()) {
//                if (discardColorCount.get(color) > discardColorCount.get(declaredColor)) {
//                    declaredColor = color;
//                }
//            }

            return declaredColor;
        }
}




