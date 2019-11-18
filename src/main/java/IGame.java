import java.util.Optional;

public interface IGame {

    public void playCard(Card card, Optional<Colors> color);
    Boolean isPlayable (Card card);
    Card draw();

}
