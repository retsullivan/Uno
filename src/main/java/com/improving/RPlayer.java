package com.improving;

import java.util.ArrayList;
import java.util.Collections;

public class RPlayer implements IPlayer {

    private ArrayList<Card> hand = new ArrayList<>();

    public RPlayer() {
    }

    public RPlayer(ArrayList<Card> hand) {
        this.hand = hand;
    }

    @Override
    public int handSize() {
        return hand.size();
    }

    @Override
    public void takeTurn(IGame game) {
            boolean cardPlayed = false;
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
            if(hand.size()==1){
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
            Colors declaredcolor = declareColor(card, game);
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



