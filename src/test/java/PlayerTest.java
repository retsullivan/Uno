import com.improving.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

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

        assertTrue(playerHand.size()== player.handSize());

    }

    @Test
    public void draw_Card_Removes_1_card_from_drawPile(){
        //arrange
        this.deck=deck;
        int startingSize = game.getDeck().getDrawPileSize();

        //act
        player.draw(game);

        //assert
        assertEquals(startingSize-1, game.getDeck().getDrawPileSize());
    }

    @Test
    public void draw_Card_returns_1_card() {
        //arrange
        this.deck = deck;
        this.player = new RPlayer(playerHand);
        //Act

        player.draw(game);

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

        Card card3 = player.draw(game);

        //assert
        assertEquals(3,playerHand.size());
    }

//    @Test     //this is no longer testable because the DrawPile has to be private.  but it worked when last tested
//    public void drawCard_returns_top_Card_of_drawPile(){
//        //arrange
//        this.game=game;
//        this.player = player;
//        this.deck = deck;
//        game.arrangeStartingDeck(deck);
//        //act
//        com.improving.Card topOfDrawPile = game.getDeck().getDrawPile().get(0);
//        com.improving.Card drawnCard = player.drawCard(game);
//
//        //assert
//        assertTrue(drawnCard.toString().equalsIgnoreCase(topOfDrawPile.toString()));
//    }

    @Test
    public void playCard_Removes_One_card_from_hand(){
        //arrange
        this.deck = deck;
        this.game = game;
        game.setDeck(deck);

        Card card1 = new Card(Faces.Five, Colors.Red);
        game.setTopCard(new Card(Faces.Five, Colors.Blue), Colors.Blue);
        playerHand.add(card1);
        this.player = new RPlayer(playerHand);
        var startingHandSize = player.handSize();
        game.addPlayer(player);
        game.setNumPlayers(1);
        //Act

        player.playCard(card1, game);

        //Assert
        assertEquals(startingHandSize-1, player.handSize());
    }

    @Test
    public void playCard_passes_color_declaration(){
        //arrange
        this.deck = deck;
        this.game = game;
        this.player = player;

        game.setTopCard(new Card(Faces.Three, Colors.Red), Colors.Red);
        Card card = new Card(Faces.Five, Colors.Red);
        playerHand.add(card);
        //Act
        this.player = new RPlayer(playerHand);

        Optional<Colors> color = Optional.ofNullable(player.declareColor(card, game));

        player.playCard(card, game);
        //Assert
        assertEquals(Colors.Red.ordinal(), color.get().ordinal());
    }

    //need unit test for declare color






//    @Test
//    public void takeTurn_adds_2Cards_if_topCard_is_Draw2(){
//       // arrange
//        this.hand = hand.getStartingHand(deck);
//        player.getHand.gets(deck);
//        deck.addCardToDiscardPile(new com.improving.Card(com.improving.Faces.Draw2, com.improving.Colors.Blue));
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
//        deck.addCardToDiscardPile(new com.improving.Card(com.improving.Faces.Draw4, com.improving.Colors.Wild));
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
//        deck.addCardToDiscardPile(new com.improving.Card(com.improving.Faces.Draw4, com.improving.Colors.Wild));
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
//        deck.addCardToDiscardPile(new com.improving.Card(com.improving.Faces.Draw4, com.improving.Colors.Wild));
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
//        deck.addCardToDiscardPile(new com.improving.Card(com.improving.Faces.Draw4, com.improving.Colors.Wild));
//        game.setDeck(deck);
//        game.setPreviousTurnSkipped(true);
//        player.getStartingHand(deck);
//        //act
//        player.takeTurn(game);
//
//        assertFalse(player.skipThisTurn);
//    }










}
