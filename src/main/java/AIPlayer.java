import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class AIPlayer implements IPlayer {

    private ArrayList<Card> hand = new ArrayList<>();
    //private Card card = new Card();
    public AIPlayer() {    }

    public AIPlayer(ArrayList<Card> hand) {
        this.hand = hand;
    }

    @Override
    public int handSize() {
        return hand.size();
    }

    @Override
    public void takeTurn(Game game) {

        boolean cardPlayed = false;

        //analyzeDeck(game);
//        analyzeOpponentHands(game);
//        optimalColor(game);
//        Card optimalCard = optimalCardFromHand(game);

        for (Card card : hand) {
            if (cardPlayed == false) {
                if (game.isPlayable(card)) {
                    playCard(card, game);
                    cardPlayed = true;
                    break;
                }
            }
        }
        //if no cards were playable, draw a card and play if it you can
        if (cardPlayed == false) {
            Card card = draw(game);
            if (game.isPlayable(card)) {
                playCard(card, game);
            }
        }
        if (hand.size() == 1) {
            yellUno();
        }
    }


    @Override
    public Card draw(Game game) {
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

        if (card.getColor()==Colors.Wild){
            while (declaredColorinHand==false) {
                Collections.shuffle(randomColors);

                //this checks to make sure that we don't declare a color if it's not in our hand
                for (Card c:hand){
                    if (card.getColor() == randomColors.get(0)){
                        declaredColorinHand = true;
                        declaredColor = card.getColor();
                        break;
                    }
                    if(card.getColor()==Colors.Wild){
                        numWildColorCardsInHand++;
                    }
                }
                if (numWildColorCardsInHand==hand.size()){
                    Collections.shuffle(randomColors);
                    declaredColorinHand = true;
                    declaredColor = randomColors.get(0);
                }
            }
        }
        return declaredColor;
    }

    private int drawPileSize(Game game) {
        return game.getDeck().getDrawPileSize();
    }

        //This method counts how many of each color are in the discard pile

    private Map<Colors, Long> discardColorCount(Game game) {
        return game.getDeck().getDiscardPile().stream()
                .map(card -> card.getColor())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public Map<Colors, Long> getDiscardColorCount(Game game){
        return discardColorCount(game);
    }


    //This method sorts the number of card of each color in the deck from highest to lowest
    //You can't easily do it in reverse order, so remember to expect it to be backwards
    private Map<Colors, Long> rankedColors(Game game){
        Map<Colors, Long> colorTally = discardColorCount(game);
        Map<Colors, Long> rankedColors= colorTally.entrySet().stream()
                                        .sorted(Map.Entry.comparingByValue())
                                        .collect(toMap(Map.Entry::getKey,Map.Entry::getValue,
                                                (e1,e2)->e1, LinkedHashMap::new));
        return rankedColors;
    }

    public Map<Colors, Long> getRankedColors(Game game){
        return rankedColors(game);
    }

    //This method sorts the number of cards of each Face in the deck from highest to lowest
    //You can't easily do it in reverse order, so remember to expect it to be backwards
    private Map<Faces, Long> discardFaceCount(Game game) {
        return game.getDeck().getDiscardPile().stream()
                .map(card -> card.getFace())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public Map<Faces, Long> getDiscardFaceCount(Game game){
        return  discardFaceCount(game);
    }


    private Map<Faces, Long> rankedFaces(Game game){
        Map<Faces, Long> faceTally = discardFaceCount(game);
        Map<Faces, Long> rankedFaces= faceTally.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(toMap(Map.Entry::getKey,Map.Entry::getValue,
                        (e1,e2)->e1, LinkedHashMap::new));
        return rankedFaces;
    }
    public Map<Faces, Long> getRankedFaces(Game game){
        return  rankedFaces(game);
    }

    private Map<String, Long> discardCardCount(Game game) {
        List<String> discardCards = game.getDeck().getDiscardPile().stream()
                .map(card ->card.toString())
                .collect(Collectors.toList());
        Map<String, Long> discardCardCount = discardCards.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        ;
        return discardCardCount;
    }

    public Map<String, Long> getDiscardCardCount(Game game){return  discardCardCount(game);}

    private Map<String, Long> rankedCards(Game game){
        Map<String, Long> cardTally = discardCardCount(game);
        Map<String, Long> rankedCards= cardTally.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(toMap(Map.Entry::getKey,Map.Entry::getValue,
                        (e1,e2)->e1, LinkedHashMap::new));
        return rankedCards;
    }

    public Map<String, Long> getRankedCards(Game game){return  rankedCards(game);}

    private ArrayList<Card> playableCards(ArrayList<Card> hand, Game game){
        this.hand = hand;
        ArrayList<Card> playableCards = new ArrayList<>();
        for (Card card:hand){
            if(game.isPlayable(card)){
                playableCards.add(card);
            }
        }
        return playableCards;
    }

    public ArrayList<Card> getPlayableCards(Game game){
        return playableCards(hand,game);
    }






//        public Colors optimalColor(Game game) {
//
//            Map<Colors, Long> optimalColors = rankedColors(game);
//            Colors optimizedColor;
//            //this checks to see if you have a card in your hand that is the same color as the one
//            //that has been played most often in the game
//
//
//            return optimizedColor;
//        }

//    private void analyzeOpponentHands(Game game) {
//        for (Player player: game.);
//    }

    private Card optimalCardFromHand(Game game) {
        Card optimalCard = new Card();
        return optimalCard;
    }

    public void yellUno(){
        System.out.println();
        System.out.println("Player " + this+ " yelled");;
        System.out.println( "db    db d8b   db  .d88b. \n" +
                "88    88 888o  88 .8P  Y8.\n" +
                "88    88 88V8o 88 88    88\n" +
                "88    88 88 V8o88 88    88\n" +
                "88b  d88 88  V888 `8b  d8'\n" +
                "~Y8888P' VP   V8P  `Y88P' ");

        System.out.println();
    }

}

