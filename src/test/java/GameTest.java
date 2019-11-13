import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    Deck deck = new Deck();
    Player player = new Player();
    Game game = new Game();
    ArrayList<Card> playerHand = new ArrayList<>();

    @Test
    public void isPlayable_returns_true_when_color_matches(){
        //arrange
        Card card1 = new Card(Faces.Four, Colors.Red);
        TopCard card2 = new TopCard(new Card(Faces.Six, Colors.Red), Colors.Red);

        //act
        boolean isPlayable = game.isPlayable(card1, card2);
        //assert
        assertTrue(isPlayable);
    }

    @Test
    public void isPlayable_returns_true_when_faces_match(){
        //arrange
        Card card1 = new Card(Faces.Six, Colors.Green);
        TopCard card2 = new TopCard(new Card(Faces.Six, Colors.Red), Colors.Red);
        playerHand.add(card1);

        //act
        boolean isPlayable = game.isPlayable(card1, card2);
        //assert
        assertTrue(isPlayable);
    }

    @Test
    public void isPlayable_returns_false_when_Faces_and_color_different(){
        //arrange
        Card card1 = new Card(Faces.Six, Colors.Green);
        TopCard card2 = new TopCard(new Card(Faces.Five, Colors.Red), Colors.Red);

        //act
        boolean isPlayable = game.isPlayable(card1, card2);
        //assert
        assertFalse(isPlayable);
    }

    @Test
    public void isPlayable_returns_true_when_Color_is_Wild(){
        //arrange

        Card card1 = new Card(Faces.Wild, Colors.Wild);
        TopCard card2 = new TopCard(new Card(Faces.Five, Colors.Red), Colors.Red);
        playerHand.add(card1);


        //act
        boolean isPlayable = game.isPlayable(card1, card2);
        //assert
        assertTrue(isPlayable);
    }

    @Test
    public void when_card_played_equals_true_handSize_goes_down_ONE_and_discardPile_size_goes_up_One(){
        //arrange
        this.deck=deck;
        this.game=game;
        deck.addCardToDiscardPile(new Card(Faces.Five, Colors.Blue));
        game.setDeck(deck);

        playerHand.add(new Card(Faces.Draw4, Colors.Wild));
        playerHand.add(new Card(Faces.Wild, Colors.Wild));

        this.player = new Player(playerHand);
        var handSize = player.getHandSize();
        var discardPileSize = game.getDeck().getDiscardPile().size();
        Card card = playerHand.get(0);

        //act
        player.playCard(card, game);

        //assert
        assertTrue(handSize-1 == player.getHandSize());
        assertTrue(discardPileSize + 1 == game.getDeck().getDiscardPile().size());
    }

    @Test
    public void getStartingHand_returns_7_cards(){
        //arrange
        this.deck = deck;
        this.game = game;
        game.arrangeStartingDeck(deck);
        //act
        ArrayList<Card> hand = game.getStartingHand(game.getDeck());
        //assert
        assertTrue(hand.size()==7);
    }

    @Test
    public void playCard_adds_card_to_Discard_Pile_and_sets_TopCard(){

        //arrange
        this.deck = deck;
        this.game = game;
        Card card = new Card(Faces.Five, Colors.Red);

        //Act
        var discardPileSize = game.getDeck().getDiscardPile().size();
        game.playCard(card, Colors.Red);

        var newDiscardPileSize = game.getDeck().getDiscardPile().size();
        var topCard = game.getTopCard();
        //Assert
        assertTrue(discardPileSize+1==newDiscardPileSize);
        assertTrue(card.toString().equalsIgnoreCase(topCard.getCard().toString()));
    }




    }
