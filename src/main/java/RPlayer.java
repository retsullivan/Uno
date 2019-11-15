import java.util.ArrayList;
import java.util.Collections;

public class RPlayer implements Player{

    private ArrayList<Card> hand = new ArrayList<>();

    public RPlayer() {
    }

    public RPlayer(ArrayList<Card> hand) {
        this.hand = hand;
    }

    @Override
    public int getHandSize() {
        return hand.size();
    }

    @Override
    public void takeTurn(Game game) {

            boolean cardPlayed = false;
            for (Card card : hand) {
                if (cardPlayed == false) {
                    if (game.isPlayable(card, game.getTopCard())) {
                        playCard(card, game);
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
                }
            }
            if(hand.size()==1){
                game.yellUno();
            }
        }

        @Override
        public Card drawCard(Game game) {
            Card card = game.getDeck().draw();
            hand.add(card);
            return card;
        }

        public void playCard(Card card, Game game) {
            Colors declaredcolor = declareColor(card, game);
            hand.remove(card);
            game.playCard(card, java.util.Optional.ofNullable(declaredcolor));
        }

        public Colors declareColor(Card card, Game game) {
            //keeping game in here because I'm going to add code to choose the ideal
            //color to declare based on the state of the game
            var declaredColor = card.getColor();
            ArrayList<Colors> randomColors = new ArrayList<>();
            randomColors.add(Colors.Red);
            randomColors.add(Colors.Blue);
            randomColors.add(Colors.Green);
            randomColors.add(Colors.Yellow);

            boolean declaredColorinHand = false;
            int numWildColorCardsInHand = 0;

            if (card.getColor().toString().equalsIgnoreCase("wild")){
                while (declaredColorinHand==false) {
                    Collections.shuffle(randomColors);

                    //this checks to make sure that we don't declare a color if it's not in our hand
                    for (Card c:hand){
                        if (card.getColor().ordinal() == randomColors.get(0).ordinal()){
                            declaredColorinHand = true;
                            declaredColor = card.getColor();
                            break;
                        }
                        if(card.getColor().ordinal()==5){
                            numWildColorCardsInHand++;
                        }
                    }
                    if (numWildColorCardsInHand==hand.size()){
                        Collections.shuffle(randomColors);
                        declaredColorinHand = true;
                        declaredColor = randomColors.get(0);
                        break;
                    }
                }
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
