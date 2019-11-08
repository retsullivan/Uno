public class Card {

    private Faces face;
    private Colors color;

    public Card(Faces face, Colors color){
        this.face = face;
        this.color = color;
    }
    public Card(){}

    @Override
    public String toString(){
        return color.toString() + " " + face.toString();
    }

    public Faces getFace() {
        return face;
    }
    public void setFace(Faces face) {
        this.face = face;
    }

    public Colors getColor() {
        return color;
    }
    public void setSuit(Colors color) {
        this.color = color;
    }
    public void setCard(Faces face, Colors color){
        this.face = face;
        this.color = color;
    }

    public Boolean equals(Card card1, Card card2){
        Boolean isEqual = false;
        if(card1.getFace().toString().equalsIgnoreCase(card2.getFace().toString()) &&
            card1.getColor().toString().equalsIgnoreCase(card2.getColor().toString())){
            isEqual = true;
        }
        return isEqual;
    }




}
