import java.util.ArrayList;
import java.util.Collections;

public class Player {

    private ArrayList<Card> hand = new ArrayList<>();

    public Player() {
    }

    public Player(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public int getHandSize() {
        return hand.size();
    }


    public Card takeTurn(Game game) {
            Card playedCard = new Card(Faces.Draw4, Colors.Blue);
            //Faces.Draw4, Colors.Blue is the fake card that "playedCard" is automatically
            //set to at the beginning of a turn if that is the card that is returned,
            // that means that the player was not able to play any card at all

            boolean cardPlayed = false;
            for (Card card : hand) {
                if (cardPlayed == false) {
                    if (game.isPlayable(card, game.getTopCard())) {
                        playCard(card, game);
                        playedCard = card;
                        cardPlayed = true;
                        break;
                    }
                }
            }
            //if no cards were playable, draw a card and play if it you can
            if (cardPlayed == false) {
                Card card = drawCard(game);
                if (game.isPlayable(card, game.getTopCard())) {
                    playCard(card, game);
                    playedCard = card;
                }
            }
            return playedCard;
        }

        public Card drawCard(Game game) {
            Card card = game.getDeck().draw();
            hand.add(card);
            return card;
        }

        public void playCard(Card card, Game game) {
            Colors color = declareColor(card, game);
            game.playCard(card, color);
            hand.remove(card);
        }

        public Colors declareColor(Card card, Game game) {
            //keeping game in here because I'm going to add code to choose the ideal
            //color to declare based on the state of the game
            var declaredColor = card.getColor();
            if (card.getColor().toString().equalsIgnoreCase("wild")){
                ArrayList<Colors> randomColors = new ArrayList<>();
                randomColors.add(Colors.Red);
                randomColors.add(Colors.Blue);
                randomColors.add(Colors.Green);
                randomColors.add(Colors.Yellow);
                Collections.shuffle(randomColors);
                declaredColor = randomColors.get(0);
            }
            return declaredColor;
        }
}


//ideas for picking the best color to declare
//pic the one that the opponents are most likely to not have?
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




//            for (Colors color : discardColorCount.keySet()) {
//                if (discardColorCount.get(color) > discardColorCount.get(declaredColor)) {
//                    declaredColor = color;
//                }
//            }
