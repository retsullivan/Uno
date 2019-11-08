import java.util.*;

public class Deck {

    private  ArrayList<Card> drawpile = new ArrayList<>();
    private  ArrayList<Card> discardPile = new ArrayList<>();
    private final Random random = new Random();

    public Deck(){
        for (Colors color: Colors.values()){
            for (Faces face : Faces.values()){
                if (color.toString().equalsIgnoreCase("wild")) {
                    if (face.getValue() == 50) {
                        drawpile.add(new Card(face, color));
                        drawpile.add(new Card(face, color));
                        drawpile.add(new Card(face, color));
                        drawpile.add(new Card(face, color));
                    }
                }else if (!face.toString().equalsIgnoreCase("wild") &&
                        !face.toString().equalsIgnoreCase("draw4")){
                    drawpile.add(new Card(face, color));
                    drawpile.add(new Card(face, color));
                }
            }
        }
    }

    public Card draw(){
//        var randomIndex = random.nextInt(cards.size()); //old shuffle method
        if (drawpile.size()==0){
            drawpile = discardPile;
            discardPile.clear();
            Collections.shuffle(drawpile);
        }
        var card = drawpile.get(0);
        drawpile.remove(0);
        return card;
    }



    public void shuffle(ArrayList<Card> cards){
        this.drawpile =cards;
        Collections.shuffle(cards);
    }

    public ArrayList<Card> getDrawPile(){
        return drawpile;
    }
    public ArrayList<Card> getDisCards(){
        return discardPile;
    }

}
