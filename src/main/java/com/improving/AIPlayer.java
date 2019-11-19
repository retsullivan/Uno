package com.improving;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class AIPlayer implements IPlayer {

    private ArrayList<Card> hand = new ArrayList<>();
    //private com.improving.Card card = new com.improving.Card();
    public AIPlayer() {    }

    public AIPlayer(ArrayList<Card> hand) {
        this.hand = hand;
    }

    @Override
    public int handSize() {
        return hand.size();
    }

    @Override
    public void takeTurn(IGame game) {

        boolean cardPlayed = false;

        if(optimalCardFromHand(game)!= null && game.isPlayable(optimalCardFromHand(game))){
            Card card = optimalCardFromHand(game);
            playCard(card, game);
            cardPlayed = true;
        } else{
            for (Card c:hand) {
                if (game.isPlayable(c)){
                    playCard(c, game);
                    cardPlayed = true;
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
    public Card draw(IGame game) {
        Card card = game.draw();
        hand.add(card);
        return card;
    }


    public void playCard(Card card, IGame game) {
        Colors declaredcolor = card.getColor();
        if(getOptimalColor(game)!=null){
            declaredcolor =getOptimalColor(game);
        }else {
            declaredcolor = declareColor(card, game);
        }
        hand.remove(card);
        game.playCard(card, java.util.Optional.ofNullable(declaredcolor));
    }

    public Colors declareColor(Card card, IGame game) {
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
                for (Card c : hand) {
                    if (card.getColor() == randomColors.get(0)) {
                        declaredColorinHand = true;
                        declaredColor = card.getColor();
                        break;
                    }
                    if (card.getColor() == Colors.Wild) {
                        numWildColorCardsInHand++;
                    }
                }
                if (numWildColorCardsInHand == hand.size()) {
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

    private Map<Colors, Long> discardColorCount(IGame game) {
        return game.getDeckInfo().getDiscardPile().stream()
                .map(card -> card.getColor())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public Map<Colors, Long> getDiscardColorCount(IGame game){
        return discardColorCount(game);
    }


    //This method sorts the number of card of each color in the deck from highest to lowest
    //You can't easily do it in reverse order, so remember to expect it to be backwards
    private Map<Colors, Long> rankedColors(IGame game){
        Map<Colors, Long> colorTally = discardColorCount(game);
        Map<Colors, Long> rankedColors= colorTally.entrySet().stream()
                                        .sorted(Map.Entry.comparingByValue())
                                        .collect(toMap(Map.Entry::getKey,Map.Entry::getValue,
                                                (e1,e2)->e1, LinkedHashMap::new));
        return rankedColors;
    }

    public Map<Colors, Long> getRankedColors(IGame game){
        return rankedColors(game);
    }

    //This method sorts the number of cards of each Face in the deck from highest to lowest
    //You can't easily do it in reverse order, so remember to expect it to be backwards
    private Map<Faces, Long> discardFaceCount(IGame game) {
        return game.getDeckInfo().getDiscardPile().stream()
                .map(card -> card.getFace())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public Map<Faces, Long> getDiscardFaceCount(IGame game){
        return  discardFaceCount(game);
    }


    private Map<Faces, Long> rankedFaces(IGame game){
        Map<Faces, Long> faceTally = discardFaceCount(game);
        Map<Faces, Long> rankedFaces= faceTally.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(toMap(Map.Entry::getKey,Map.Entry::getValue,
                        (e1,e2)->e1, LinkedHashMap::new));
        return rankedFaces;
    }
    public Map<Faces, Long> getRankedFaces(IGame game){
        return  rankedFaces(game);
    }

    private Map<String, Long> discardCardCount(IGame game) {
        List<String> discardCards = game.getDeckInfo().getDiscardPile().stream()
                .map(card ->card.toString())
                .collect(Collectors.toList());
        Map<String, Long> discardCardCount = discardCards.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        ;
        return discardCardCount;
    }

    public Map<String, Long> getDiscardCardCount(Game game){return  discardCardCount(game);}

    private Map<String, Long> rankedCards(IGame game){
        Map<String, Long> cardTally = discardCardCount(game);
        Map<String, Long> rankedCards= cardTally.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(toMap(Map.Entry::getKey,Map.Entry::getValue,
                        (e1,e2)->e1, LinkedHashMap::new));
        return rankedCards;
    }



    public Map<String, Long> getRankedCards(IGame game){return  rankedCards(game);}

    private ArrayList<Card> playableCards(ArrayList<Card> hand, IGame game){
        this.hand = hand;
        ArrayList<Card> playableCards = new ArrayList<>();
        for (Card card:hand){
            if(game.isPlayable(card)){
                playableCards.add(card);
            }
        }
        return playableCards;
    }

    public ArrayList<Card> getPlayableCards(IGame game){
        return playableCards(hand,game);
    }

    public boolean useActionCard(IGame game){
        boolean useActionCard = false;
        for(IPlayerInfo player: game.getPlayerInfo()){
            if(player.handSize() ==1){
                useActionCard=true;
            }
        }
        return useActionCard;
    }

    public Card getOptimalActionCard(IGame game){
        Card actionCard = new Card();
        List<IPlayerInfo> players= game.getPlayerInfo();

        if (game.getNextPlayer().handSize() == 1){
            for (Card card:this.hand){
                if(card.getFace()==(Faces.Draw4)) {
                        return card;
                }else if ((card.getFace()==(Faces.Draw2))&&
                            game.isPlayable(card)){
                    return card;
                }else if (card.getFace()==Faces.Skip&&
                        game.getNextNextPlayer().handSize() != 1 &&
                        game.isPlayable(card)){
                    return card;
                } else if ((card.getFace()==Faces.Reverse)&&
                        game.getPreviousPlayer().handSize() != 1 &&
                        game.isPlayable(card)){
                    return card;
                }
            }
        }
        return actionCard;
    }


    public Colors getOptimalColor(IGame game) {
        //this checks to see if you have a card in your hand that is the same color as the one
        //that has been played most often in the game
        Map<Colors, Long> rankedColors = getRankedColors(game);
        rankedColors.remove(Colors.Wild);
        List<Colors> colors = new ArrayList<>(rankedColors.keySet());
        Colors optimizedColor = null;


        for (int i=colors.size()-1;i>=0;i--){  //since the comparator spit it out in ascending order
            for (Card card:this.hand) {
                if(!hasAction(card)&&card.getColor() == colors.get(i)){ //this may need to be less strict
                    optimizedColor = card.getColor();
                    return optimizedColor;
                }
            }
        }
    return optimizedColor;
    }


    public Faces getOptimalFace(IGame game) {
        //this checks to see if you have a card in your hand that is the same color as the one
        //that has been played most often in the game
        Map<Faces, Long> rankedFaces = getRankedFaces(game);
        List<Faces> faces = new ArrayList<>(rankedFaces.keySet());
        Faces optimizedFace = null;

        for (int i=faces.size()-1;i>=0;i--){ //since the comparator spit it out in ascending order
            for (Card card:this.hand) {
                if(!hasAction(card)&&card.getFace() == faces.get(i)){ //this may need to be less strict
                    optimizedFace = card.getFace();
                    return optimizedFace;
                }
            }
        }
        return optimizedFace;
    }

//    public com.improving.Card getMostCommonCardInDiscardPile(com.improving.IGame game) {
//        //this checks to see if you have a card in your hand that is the same color as the one
//        //that has been played most often in the game
//        Map<String, Long> rankedCards = getRankedCards(game);
//        List<String> cardNames = new ArrayList<>(rankedCards.keySet());
//        com.improving.Card optimizedCard = new com.improving.Card();
//
//        for (int i =cardNames.size()-1;i>=0;i--){
//            for (com.improving.Card card:this.hand) {
//                if(!hasAction(card)&& card.toString().equalsIgnoreCase(cardNames.get(i))){ //this may need to be less strict
//                    optimizedCard = card;
//                    return optimizedCard;
//                }
//            }
//        }
//        return optimizedCard;
//    }


    private Card optimalCardFromHand(IGame game) {

        if(useActionCard(game)){
            Card optimalCard = getOptimalActionCard(game);
            if (optimalCard!=null&&game.isPlayable(optimalCard)){
                return optimalCard;
            }
        }
        //need to figure out how to stream the cards and collect correctly
//        optimalCard = getMostCommonCardInDiscardPile(game);
//        if (optimalCard!=null){
//            return optimalCard;
//        }
        Faces optimalFace = getOptimalFace(game);
        Colors optimalColor = getOptimalColor(game);
        for(Card card:this.hand){
            if(card.getFace()==optimalFace
                    && card.getColor()==optimalColor
                    &&game.isPlayable(card)){
                return card;
            }
        }
        for(Card card:this.hand){
            if(card.getFace()==optimalFace && game.isPlayable(card)){
                return card;
            }
        }
        for(Card card:this.hand){
            if(card.getColor()==optimalColor && game.isPlayable(card)){
                return card;
            }
        }
        for(Card card:this.hand){
            if (game.isPlayable(card)){
                return card;
            }
        }
        return null;
    }

    private Boolean hasAction(Card card) {

        if (card.getFace().toString().equalsIgnoreCase("draw4")) {
            return true;
        } else if (card.getFace().toString().equalsIgnoreCase("draw2")) {
            return true;
        } else if (card.getFace().toString().equalsIgnoreCase("skip")) {
            return true;
        }else if (card.getFace().toString().equalsIgnoreCase("reverse")) {
            return true;
        }else {
            return false;
        }

    }
    public boolean equalCards(Card card1, Card card2){
        Boolean isEqual = false;
        if(card1.getFace()==card2.getFace() &&
                card1.getColor()==card2.getColor()){
            isEqual = true;
        }
        return isEqual;
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

