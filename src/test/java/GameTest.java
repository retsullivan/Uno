import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    Deck deck = new Deck();
    RPlayer player = new RPlayer();
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
    public void when_card_played_handSize_goes_down_ONE_and_discardPile_size_goes_up_One(){
        //arrange
        this.deck=deck;
        this.game=game;


        deck.addCardToDiscardPile(new Card(Faces.Five, Colors.Blue));
        game.setDeck(deck);

        playerHand.add(new Card(Faces.Draw4, Colors.Wild));
        playerHand.add(new Card(Faces.Wild, Colors.Wild));

        player = new RPlayer(playerHand);
        game.addPlayer(player);
        game.setNumPlayers(1);

        Card card = playerHand.get(0);

        //act
        player.playCard(card, game);

        //assert

        assertEquals(2,game.getDeck().getDiscardPile().size());
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

    @Test
    public void draw4_makes_next_Player_draw_4(){ //not currentPLayer

        //arrange
        this.deck = deck;
        this.game = game;

        RPlayer RPlayer1 = new RPlayer(game.getStartingHand(deck));
        RPlayer RPlayer2 = new RPlayer(game.getStartingHand(deck));
        RPlayer RPlayer3 = new RPlayer(game.getStartingHand(deck));

        deck.addCardToDiscardPile(new Card(Faces.Draw4, Colors.Wild));

        game.addPlayer(RPlayer1);
        game.addPlayer(RPlayer2);
        game.addPlayer(RPlayer3);

        game.setDeck(deck);
        game.setNumPlayers(3);

        game.currentTurn=0;
        game.turnDirection=1;

        game.executeCardAction(new Card(Faces.Draw4, Colors.Wild), game);

        //Act

        //Assert
        assertEquals(7, RPlayer1.getHandSize());
        assertEquals(11, RPlayer2.getHandSize());
        assertEquals(7, RPlayer3.getHandSize());
    }

    @Test
    public void draw2_makes_next_Player_draw2(){ //not currentPLayer

        //arrange
        this.deck = deck;
        this.game = game;

        RPlayer RPlayer1 = new RPlayer(game.getStartingHand(deck));
        RPlayer RPlayer2 = new RPlayer(game.getStartingHand(deck));
        RPlayer RPlayer3 = new RPlayer(game.getStartingHand(deck));

        deck.addCardToDiscardPile(new Card(Faces.Draw2, Colors.Blue));

        game.addPlayer(RPlayer1);
        game.addPlayer(RPlayer2);
        game.addPlayer(RPlayer3);

        game.setDeck(deck);
        game.setNumPlayers(3);

        game.currentTurn=1;
        game.turnDirection=1;

        game.executeCardAction(new Card(Faces.Draw2, Colors.Blue), game);

        //Act

        //Assert
        assertEquals(7, RPlayer1.getHandSize());
        assertEquals(7, RPlayer2.getHandSize());
        assertEquals(9, RPlayer3.getHandSize());
    }

    @Test
    public void skip_increments_current_turn_by_ONE(){
        RPlayer RPlayer1 = new RPlayer(game.getStartingHand(deck));
        RPlayer RPlayer2 = new RPlayer(game.getStartingHand(deck));
        RPlayer RPlayer3 = new RPlayer(game.getStartingHand(deck));
        //arrange
        game.addPlayer(RPlayer1);
        game.addPlayer(RPlayer2);
        game.addPlayer(RPlayer3);

        game.setDeck(deck);
        game.setNumPlayers(3);
        this.game = game;
        game.currentTurn=1;
        game.turnDirection=1;
        //Act
        game.executeCardAction(new Card(Faces.Skip, Colors.Blue), game);
        //Assert
        assertEquals(2,game.currentTurn);
    }

    @Test
    public void reverse_changes_turn_direction(){

        //arrange
        this.game = game;

        game.currentTurn=1;
        game.turnDirection=1;
        //Act
        game.executeCardAction(new Card(Faces.Reverse, Colors.Blue), game);
        //Assert
        assertEquals(-1,game.turnDirection);
    }

}
