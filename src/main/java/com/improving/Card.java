package com.improving;

public class Card {

    private Faces face;
    private Colors color;

    public Card(){}

    public Card(Faces face, Colors color){
        this.face = face;
        this.color = color;
    }

    @Override
    public String toString(){
            return color.toString() + " " + face.toString();
    }

    public Faces getFace() {
        return face;
    }


    public Colors getColor() {
        return color;
    }


    public boolean equals(Card card1, Card card2){
        Boolean isEqual = false;
        if(card1.getFace().toString().equalsIgnoreCase(card2.getFace().toString()) &&
            card1.getColor().toString().equalsIgnoreCase(card2.getColor().toString())){
            isEqual = true;
        }
        return isEqual;
    }


}
