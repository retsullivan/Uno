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
        //Faces.Draw4, Colors.Blue is the fake card that "playedCard" is automatically
        //set to at the beginning of a turn if that is the card that is returned,
        // that means that the player was not able to play any card at all
//        if (color.toString().equalsIgnoreCase("Blue")&&face.toString().equalsIgnoreCase("Draw4")){
//            return "No card ";
//        }else {
            return color.toString() + " " + face.toString();
        //}
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
