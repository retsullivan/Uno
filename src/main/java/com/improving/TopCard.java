package com.improving;

public class TopCard {

    private Card card;
    private Colors declaredColor;

    public TopCard (){}

    public TopCard(Card card, Colors declaredColor){
        this.card=card;
        this.declaredColor=declaredColor;
    }


    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Colors getDeclaredColor() {
        return declaredColor;
    }

    public void setDeclaredColor(Colors declaredColor) {
        this.declaredColor = declaredColor;
    }

    @Override
    public String toString(){
        //com.improving.Faces.Draw4, com.improving.Colors.Blue is the fake card that "playedCard" is automatically
        //set to at the beginning of a turn if that is the card that is returned,
        // that means that the player was not able to play any card at all
//        if (color.toString().equalsIgnoreCase("Blue")&&face.toString().equalsIgnoreCase("Draw4")){
//            return "No card ";
//        }else {

        return this.getCard().toString() + ". The declared color is " + declaredColor.toString();
        //}
    }

}
