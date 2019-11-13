import java.util.*;

public class Deck {

    private  ArrayList<Card> drawPile = new ArrayList<>();
    private  ArrayList<Card> discardPile = new ArrayList<>();
    private final Random random = new Random();

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
    }

    public Card draw(){
//        var randomIndex = random.nextInt(cards.size()); //old shuffle method
        if (drawPile.size()==0){
            Card topCard = discardPile.get(0);
            discardPile.remove(0);
            drawPile = discardPile;
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



    public void addCardToDiscardPile (Card card){
        discardPile.add(card);
    }


    public ArrayList<Card> getDrawPile(){
        return drawPile;
    }
    public ArrayList<Card> getDiscardPile(){
        return discardPile;
    }


    public void setDrawPile(ArrayList<Card> drawpile) {
        this.drawPile = drawpile;
    }

    public void setDiscardPile(ArrayList<Card> discardPile) {
        this.discardPile = discardPile;
    }

}
