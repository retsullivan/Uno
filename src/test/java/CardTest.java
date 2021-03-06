import com.improving.Card;
import com.improving.Colors;
import com.improving.Faces;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    Faces face;
    Colors color;
    Card card = new Card(Faces.Reverse, Colors.Green);

    @Test
    public void get_face_returns_correct_face() {
        //arrange
        this.card = card;

        //act
        Boolean match = false;
        match = card.getFace().toString().equalsIgnoreCase("Reverse");
        //assert

        assertTrue(match);
    }

    @Test
    public void get_value_returns_correct_value() {
        //arrange
        this.card = card;
        //act
        Boolean match = false;
        if (card.getFace().getValue() == 20) {
            match = true;
        }
        //assert
        assertTrue(match);
    }
    @Test
    public void toString_returns_correct_info(){
        //arrange
        Card name = new Card(Faces.Seven, Colors.Green);

        //act
        Boolean match = false;
        match = name.toString().equalsIgnoreCase("Green Seven");

        //assert
        assertTrue(match);
    }

//    @Test
//    public void equals_returns_true_when_same_card(){
//        com.improving.Card card1 = new com.improving.Card(com.improving.Faces.Reverse, com.improving.Colors.Blue);
//        com.improving.Card card2 = new com.improving.Card(com.improving.Faces.Reverse, com.improving.Colors.Blue);
//
//        assertTrue(card.equals(card1,card2));
//    }



}
