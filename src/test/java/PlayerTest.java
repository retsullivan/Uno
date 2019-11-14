import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Deck deck = new Deck();

    ArrayList<Card> playerHand = new ArrayList<>();
    RPlayer player = new RPlayer();
    Game game = new Game();




    @Test
    public void getHandSize_returns_correct_HandSize(){
        //arrange
        playerHand.add(new Card(Faces.Six,Colors.Blue));
        playerHand.add(new Card(Faces.Six,Colors.Red));
        //act
        this.player = new RPlayer(playerHand);

        assertTrue(playerHand.size()== player.getHandSize());

    }

    @Test
    public void draw_Card_Removes_1_card_from_drawPile(){
        //arrange
        this.deck=deck;
        int startingSize = game.getDeck().getDrawPile().size();

        //act
        player.drawCard(game);

        //assert
        assertTrue(startingSize-1 == game.getDeck().getDrawPile().size());
    }

    @Test
    public void draw_Card_returns_1_card() {
        //arrange
        this.deck = deck;
        this.player = new RPlayer(playerHand);
        //Act

        player.drawCard(game);

        //assert
        assertEquals(1,playerHand.size());
    }

    @Test
    public void draw_Card_adds_1_card_to_Hand() {
        //arrange
        this.deck = deck;
        Card card1 = new Card(Faces.Draw4, Colors.Wild);
        Card card2 = new Card(Faces.Five, Colors.Red);
        playerHand.add(card1);
        playerHand.add(card2);
        this.player = new RPlayer(playerHand);
        game.addPlayer(player);
        //Act

        Card card3 = player.drawCard(game);

        //assert
        assertEquals(3,playerHand.size());
    }

    @Test
    public void drawCard_returns_top_Card_of_drawPile(){
        //arrange
        this.game=game;
        this.player = player;
        this.deck = deck;
        game.arrangeStartingDeck(deck);
        //act
        Card topOfDrawPile = game.getDeck().getDrawPile().get(0);
        Card drawnCard = player.drawCard(game);

        //assert
        assertTrue(drawnCard.toString().equalsIgnoreCase(topOfDrawPile.toString()));
    }

    @Test
    public void playCard_Removes_One_card_from_hand(){
        //arrange
        this.deck = deck;
        this.game = game;

        Card card1 = new Card(Faces.Wild, Colors.Wild);
        Card card2 = new Card(Faces.Five, Colors.Red);
        playerHand.add(card1);
        playerHand.add(card2);
        this.player = new RPlayer(playerHand);
        game.addPlayer(player);
        //Act

        player.playCard(card1, game);

        //Assert
        assertEquals(1, player.getHandSize());
    }

    @Test
    public void playCard_passes_color_declaration(){
        //arrange
        this.deck = deck;
        this.game = game;
        this.player = player;

        Card card = new Card(Faces.Five, Colors.Red);
        playerHand.add(card);
        //Act
        player.playCard(card, game);
        var color = player.declareColor(card,game);
        //Assert
        assertTrue(game.getTopCard().getDeclaredColor().toString().equalsIgnoreCase(color.toString()));
    }

    //need unit test for declare color






//    @Test
//    public void takeTurn_adds_2Cards_if_topCard_is_Draw2(){
//       // arrange
//        this.hand = hand.getStartingHand(deck);
//        player.getHand.gets(deck);
//        deck.addCardToDiscardPile(new Card(Faces.Draw2, Colors.Blue));
//        game.setDeck(deck);
//        var startingSize = player.getHand().getHand().size();
//        //act
//
//        player.takeTurn(game);
//        var endingsize = player.getHand().getHand().size();
//        //assert
//
//        assertTrue(startingSize+2 == endingsize);
//
//    }
//    @Test
//    public void takeTurn_adds_4_Cards_if_topCard_is_Draw4(){
//        // arrange
//        player.getStartingHand(deck);
//        deck.addCardToDiscardPile(new Card(Faces.Draw4, Colors.Wild));
//        game.setDeck(deck);
//        var startingSize = player.getHand().getHand().size();
//        //act
//
//        player.takeTurn(game);
//        var endingsize = player.getHand().getHand().size();
//        //assert
//
//        assertTrue(startingSize+4 == endingsize);
//    }
//
//    @Test
//    public void if_Draw_4_rest_of_turn_skipped(){
//        // arrange
//        player.getStartingHand(deck);
//        deck.addCardToDiscardPile(new Card(Faces.Draw4, Colors.Wild));
//        game.setDeck(deck);
//
//        //act
//        player.takeTurn(game);
//        //assert
//
//        assertTrue(player.skipThisTurn);
//    }
//
//
//    @Test
//    public void if_skipThisTurn_true_PreviousTurnSkippedToggled_to_True(){
//        player.getStartingHand(deck);
//        deck.addCardToDiscardPile(new Card(Faces.Draw4, Colors.Wild));
//        game.setDeck(deck);
//        //act
//        player.takeTurn(game);
//
//        assertTrue(game.getPreviousTurnSkipped());
//    }
//
//    @Test
//    public void if_PreviousTurnSkippedToggled_is_True_skip_turn_false(){
//
//        deck.addCardToDiscardPile(new Card(Faces.Draw4, Colors.Wild));
//        game.setDeck(deck);
//        game.setPreviousTurnSkipped(true);
//        player.getStartingHand(deck);
//        //act
//        player.takeTurn(game);
//
//        assertFalse(player.skipThisTurn);
//    }










}
