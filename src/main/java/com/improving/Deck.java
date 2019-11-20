package com.improving;

import java.util.*;

public class Deck implements IDeck{

    private  ArrayList<Card> drawPile = new ArrayList<>();
    private  ArrayList<Card> discardPile = new ArrayList<>();
    private final Random random = new Random();

    public ArrayList<Card> allCardsInDeck = new ArrayList<>();

    public Deck(){
        for (Colors color: Colors.values()){
            for (Faces face : Faces.values()){
                if (color.toString().equalsIgnoreCase("wild")) {
                    if (face.getValue() == 50) {
                        drawPile.add(new Card(face, color));
                        drawPile.add(new Card(face, color));
                        drawPile.add(new Card(face, color));
                        drawPile.add(new Card(face, color));
                    }
                }else if (!face.toString().equalsIgnoreCase("wild") &&
                        !face.toString().equalsIgnoreCase("draw4")){
                    drawPile.add(new Card(face, color));
                    drawPile.add(new Card(face, color));
                }
            }
        }
        for (Card card:drawPile){
            allCardsInDeck.add(card);
        }
    }

    public Card draw(){
        if (drawPile.size()==0){
            Card topCard = discardPile.get(0);
            discardPile.remove(0);
            drawPile.addAll(discardPile); //this puts all the cards in the discard pile into the draw pile
            discardPile.clear();
            Collections.shuffle(drawPile);
            discardPile.add(topCard);
        }
        var card = drawPile.get(0);
        drawPile.remove(0);
        return card;
    }

    public ArrayList<Card> shuffle(ArrayList<Card> cards){
        this.drawPile =cards;
        Collections.shuffle(cards);
        return cards;
    }

    public ArrayList<Card> getDiscardPile(ArrayList<Card> cards){
        return discardPile;
    }



    public void addCardToDiscardPile (Card card){
        discardPile.add(card);
    }

//    public Boolean isMember (Card card){
//        boolean cardInDeck = false;
//        if (allCardsInDeck.contains(card)){
//            cardInDeck = true;
//        }
//        return cardInDeck;
//    }

    //I had to make this private so people can't cheat
    private ArrayList<Card> getDrawPile(){
        return drawPile;
    }

    @Override
    public int getDrawPileSize(){
        return drawPile.size();
    }

    @Override
    public ArrayList<Card> getDiscardPile(){
        return discardPile;
    }


    public void setDrawPile(ArrayList<Card> drawpile) {
        this.drawPile = drawpile;
    }

    public void setDiscardPile(ArrayList<Card> discardPile) {
        this.discardPile = discardPile;
    }

    public void initilizeGame() {
     setDrawPile(this.shuffle(this.getDrawPile()));
    }
}
