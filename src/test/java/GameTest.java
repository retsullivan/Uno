import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    Deck deck = new Deck();
    RPlayer player = new RPlayer();
    Game game = new Game();
    ArrayList<Card> playerHand = new ArrayList<>();
    Card card = new Card(Faces.One,Colors.Blue);

    @Test
    public void isPlayable_returns_true_when_color_matches(){
        //arrange
        Card card1 = new Card(Faces.Four, Colors.Red);
        game.setTopCard(new Card(Faces.Six, Colors.Red), Colors.Red);
        //act
        boolean isPlayable = game.isPlayable(card1);
        //assert
        assertTrue(isPlayable);
    }

    @Test
    public void isPlayable_returns_true_when_faces_match(){
        //arrange
        Card card1 = new Card(Faces.Six, Colors.Green);
        game.setTopCard(new Card(Faces.Six, Colors.Red), Colors.Red);
        playerHand.add(card1);

        //act
        boolean isPlayable = game.isPlayable(card1);
        //assert
        assertTrue(isPlayable);
    }

    @Test
    public void isPlayable_returns_false_when_Faces_and_color_different(){
        //arrange
        Card card1 = new Card(Faces.Six, Colors.Green);
        game.setTopCard(new Card(Faces.Five, Colors.Red), Colors.Red);
        //act
        boolean isPlayable = game.isPlayable(card1);
        //assert
        assertFalse(isPlayable);
    }

    @Test
    public void isPlayable_returns_true_when_Color_is_Wild(){
        //arrange

        Card card1 = new Card(Faces.Wild, Colors.Wild);
        game.setTopCard(new Card(Faces.Five, Colors.Red), Colors.Red);
        playerHand.add(card1);


        //act
        boolean isPlayable = game.isPlayable(card1);
        //assert
        assertTrue(isPlayable);
    }

    @Test
    public void when_card_played_discardPile_size_goes_up_One(){
        //arrange
        this.deck=deck;
        this.game=game;

        game.setDeck(deck);
        game.setTopCard(new Card(Faces.Five, Colors.Blue), Colors.Blue);
        playerHand.add(new Card (Faces.Five, Colors.Red));
        player = new RPlayer(playerHand);
        game.addPlayer(player);
        game.setNumPlayers(1);
        var discardPileSize = game.getDeck().getDiscardPile().size();

        //act
        player.takeTurn(game);

        //assert
        assertEquals(discardPileSize+1,game.getDeck().getDiscardPile().size());
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
        this.card = card;


        game.setDeck(deck);
        game.setTopCard(new Card(Faces.Six, Colors.Red), Colors.Red);
        playerHand.add(new Card(Faces.Five, Colors.Red));
        player = new RPlayer(playerHand);
        var discardPileSize = game.getDeck().getDiscardPile().size();
        //Act
        player.takeTurn(game);

        var newDiscardPileSize = game.getDeck().getDiscardPile().size();
        var topCard = game.getTopCard();
        //Assert
        assertEquals(discardPileSize+1,newDiscardPileSize);
        assertTrue(card.equals(new Card(Faces.Five, Colors.Red), topCard.getCard()));
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

    @Test
    public void color_declaration_reset_correctly(){

        this.game = game;
        this.deck = deck;

        game.setTopCard(new Card(Faces.Wild, Colors.Wild), Colors.Red);
        game.currentTurn=1;
        game.turnDirection=1;
        game.setNumPlayers(2);

        ArrayList<Card> playerHand1 = new ArrayList<>();
        ArrayList<Card> playerHand2 = new ArrayList<>();

        playerHand1.add(new Card(Faces.Two,Colors.Red));
        playerHand2.add(new Card(Faces.Two,Colors.Blue));

        RPlayer RPlayer1 = new RPlayer(playerHand1);
        RPlayer RPlayer2 = new RPlayer(playerHand2);

        RPlayer1.playCard(new Card(Faces.Two,Colors.Red), game);

        assertTrue(game.isPlayable(new Card(Faces.Two,Colors.Blue)));

    }

    @Test
    public void deck_count_remains_the_same_when_Cards_have_been_played (){
        this.game = game;
        this.deck = deck;

        game.setTopCard(new Card(Faces.Wild, Colors.Wild), Colors.Red);
        game.currentTurn=1;
        game.turnDirection=1;
        game.setNumPlayers(1);



        ArrayList<Card> playerHand1 = new ArrayList<>();
        ArrayList<Card> playerHand2 = new ArrayList<>();

        game.setTopCard(new Card(Faces.Two,Colors.Blue), Colors.Blue);


        RPlayer RPlayer1 = new RPlayer(game.getStartingHand(deck));
        RPlayer RPlayer2 = new RPlayer(game.getStartingHand(deck));

        RPlayer1.playCard(new Card(Faces.Two,Colors.Red), game);
        RPlayer2.playCard(new Card(Faces.Two,Colors.Blue), game);

        assertEquals(deck.allCardsInDeck.size()+2, deck.getDrawPileSize() +deck.getDiscardPile().size()+ RPlayer1.getHandSize() + RPlayer2.getHandSize());
    }

    @Test
    public void play_card_protects_against_passing_invalid_color(){
        this.deck=deck;
        this.game=game;

        game.setDeck(deck);
        game.setTopCard(new Card(Faces.Five, Colors.Blue), Colors.Blue);
        playerHand.add(new Card (Faces.Five, Colors.Red));
        player = new RPlayer(playerHand);
        game.addPlayer(player);
        game.setNumPlayers(1);
        boolean invalidColorFixed = false;

        //act
        game.playCard(new Card(Faces.Wild, Colors.Wild), java.util.Optional.of(Colors.Wild));

        if(game.getTopCard().getDeclaredColor()==Colors.Red ||
            game.getTopCard().getDeclaredColor()==Colors.Blue ||
            game.getTopCard().getDeclaredColor()==Colors.Green ||
            game.getTopCard().getDeclaredColor()==Colors.Yellow){
            invalidColorFixed = true;
        }
        //assert
        assertTrue(invalidColorFixed);
    }

    @Test
    public void is_Valid_Declared_Color_Recognizes_Valid_color() {
        this.deck = deck;
        this.game = game;

        game.setDeck(deck);
        game.setTopCard(new Card(Faces.Five, Colors.Blue), Colors.Blue);
        playerHand.add(new Card(Faces.Five, Colors.Red));
        player = new RPlayer(playerHand);
        game.addPlayer(player);
        game.setNumPlayers(1);
        game.currentTurn=0;
        game.turnDirection=1;

        //act
        //game.playCard(new Card(Faces.Five, Colors.Red), java.util.Optional.of(Colors.Red));
        boolean validColorRecognized = game.isValidDeclaredColor(java.util.Optional.of(Colors.Red));

        //assert
        assertTrue(validColorRecognized);
    }



}
