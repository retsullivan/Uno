import com.improving.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AIPlayerTests {

    Deck deck = new Deck();

    ArrayList<Card> playerHand = new ArrayList<>();
    AIPlayer player = new AIPlayer();
    Game game = new Game();

    @Test
    public void getHandSize_returns_correct_HandSize(){
        //arrange
        playerHand.add(new Card(Faces.Six,Colors.Blue));
        playerHand.add(new Card(Faces.Six,Colors.Red));
        //act
        this.player = new AIPlayer(playerHand);

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
        this.player = new AIPlayer(playerHand);
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
        this.player = new AIPlayer(playerHand);
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
        this.player = new AIPlayer(playerHand);
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
        this.player = new AIPlayer(playerHand);

        Optional<Colors> color = Optional.ofNullable(player.declareColor(card, game));

        player.playCard(card, game);
        //Assert
        assertEquals(Colors.Red.ordinal(), color.get().ordinal());
    }

    @Test
    public void discard_color_Count_is_correct(){
        //arrange
        this.game = arrangeColorCountTestConditions(game);
        //act
        Map<Colors, Long> discardColorCount = player.getDiscardColorCount(game);
        //assert
        assertEquals( 3,discardColorCount.get(Colors.Red));
        assertEquals( 4,discardColorCount.get(Colors.Blue));
        assertEquals( 1,discardColorCount.get(Colors.Yellow));
        assertEquals( 2,discardColorCount.get(Colors.Green));
        assertEquals(5, discardColorCount.get(Colors.Wild));
    }

    @Test
    public void discard_color_Count_Ranks_are_correct(){
        //This method sorts the number of card of each color in the deck from highest to lowest
        //You can't easily do it in reverse order, so remember to expect it to be backwards
        this.game = arrangeColorCountTestConditions(game);
        //act
        Map<Colors, Long> rankedColors = player.getRankedColors(game);
        List<Colors> orderedColors = rankedColors.keySet().stream().collect(Collectors.toList());
        //assert
        assertEquals( 4,orderedColors.indexOf(Colors.Wild));
        assertEquals( 3,orderedColors.indexOf(Colors.Blue));
        assertEquals( 2,orderedColors.indexOf(Colors.Red));
        assertEquals( 1,orderedColors.indexOf(Colors.Green));
        assertEquals(0,orderedColors.indexOf(Colors.Yellow));
    }

    @Test
    public void discard_Face_Counts_are_Correct(){
        this.game = arrangFaceCountTestConditions(game);
        //act
        Map<Faces, Long> discardFaceCount = player.getDiscardFaceCount(game);
        //assert
        assertEquals( 3,discardFaceCount.get(Faces.Wild));
        assertEquals( 2,discardFaceCount.get(Faces.Draw4));
        assertEquals( 1,discardFaceCount.get(Faces.Two));
    }

    //not sure why this isn't working
    @Test
    public void discard_face_Count_Ranked_Order_correct(){
        //This method sorts the number of card of each color in the deck from highest to lowest
        //You can't easily do it in reverse order, so remember to expect it to be backwards
        this.game = arrangFaceCountTestConditions(game);
        //act
        Map<Faces, Long> rankedFaces = player.getRankedFaces(game);
        List<Faces> orderedFaces = rankedFaces.keySet().stream().collect(Collectors.toList());
        //assert
        assertEquals( 2,orderedFaces.indexOf(Faces.Wild));
        assertEquals( 1,orderedFaces.indexOf(Faces.Draw4));
        assertEquals( 0,orderedFaces.indexOf(Faces.Two));
    }

    @Test
    public void discard_count_has_correct_number_of_entries(){
        this.game = arrangeCardCountTestConditions(game);

        int numDiscardCardCountEntries = player.getDiscardCardCount(game).size();
        assertEquals( 3, numDiscardCardCountEntries);
    }

    @Test
    public void discard_count_correct(){
        this.game = arrangeCardCountTestConditions(game);

        Card card1 = new Card(Faces.Wild,Colors.Wild);
        Card card2 = new Card(Faces.Two, Colors.Blue);
        Card card3 = new Card(Faces.Five, Colors.Red);

        Map<String, Long> discardCardCount = player.getDiscardCardCount(game);

        assertEquals( 3,discardCardCount.get(card1.toString())); //need to define equals or something
        assertEquals( 2,discardCardCount.get(card3.toString()));
        assertEquals( 1,discardCardCount.get(card2.toString()));
    }

    @Test
    public void discard_pile_card_counts_ranked_correctly(){
        this.game = arrangeCardCountTestConditions(game);

        Card card1 = new Card(Faces.Wild,Colors.Wild);
        Card card2 = new Card(Faces.Two, Colors.Blue);
        Card card3 = new Card(Faces.Five, Colors.Red);

        Map<String, Long> discardCardCount = player.getRankedCards(game);
        List<String> rankedCardTally = discardCardCount.keySet().stream().collect(Collectors.toList());

        assertEquals( 2,rankedCardTally.indexOf(card1.toString())); //need to define equals or something
        assertEquals( 1,rankedCardTally.indexOf(card3.toString()));
        assertEquals( 0,rankedCardTally.indexOf(card2.toString()));
    }


    public Game arrangeColorCountTestConditions(Game game){
        this.deck = deck;
        this.game = game;
        game.setNumPlayers(1);
        playerHand.add(new Card(Faces.Wild,Colors.Wild));
        game.arrangeStartingDeck(deck);
        Card topCard = game.getTopCard().getCard();
        deck.getDiscardPile().remove(topCard);

        this.player = new AIPlayer(playerHand);
        deck.addCardToDiscardPile(new Card(Faces.Wild,Colors.Wild));
        deck.addCardToDiscardPile(new Card(Faces.Wild,Colors.Wild));
        deck.addCardToDiscardPile(new Card(Faces.Wild,Colors.Wild));
        deck.addCardToDiscardPile(new Card(Faces.Draw4,Colors.Wild));
        deck.addCardToDiscardPile(new Card(Faces.Draw4,Colors.Wild));
        deck.addCardToDiscardPile(new Card(Faces.Skip, Colors.Blue));
        deck.addCardToDiscardPile(new Card(Faces.Two, Colors.Blue));
        deck.addCardToDiscardPile(new Card(Faces.One, Colors.Blue));
        deck.addCardToDiscardPile(new Card(Faces.One, Colors.Blue));
        deck.addCardToDiscardPile(new Card(Faces.Five, Colors.Red));
        deck.addCardToDiscardPile(new Card(Faces.Skip, Colors.Red));
        deck.addCardToDiscardPile(new Card(Faces.Three, Colors.Red));
        deck.addCardToDiscardPile(new Card(Faces.Nine, Colors.Green));
        deck.addCardToDiscardPile(new Card(Faces.Four, Colors.Green));
        deck.addCardToDiscardPile(new Card(Faces.Draw2, Colors.Yellow));

        return game;

    }

    public Game arrangFaceCountTestConditions(Game game){
        this.deck = deck;
        this.game = game;
        game.setNumPlayers(1);
        playerHand.add(new Card(Faces.Wild,Colors.Wild));
        game.arrangeStartingDeck(deck);
        Card topCard = game.getTopCard().getCard();
        deck.getDiscardPile().remove(topCard);

        this.player = new AIPlayer(playerHand);
        deck.addCardToDiscardPile(new Card(Faces.Wild,Colors.Wild));
        deck.addCardToDiscardPile(new Card(Faces.Wild,Colors.Wild));
        deck.addCardToDiscardPile(new Card(Faces.Wild,Colors.Wild));
        deck.addCardToDiscardPile(new Card(Faces.Draw4,Colors.Wild));
        deck.addCardToDiscardPile(new Card(Faces.Draw4,Colors.Wild));
        deck.addCardToDiscardPile(new Card(Faces.Two, Colors.Blue));


        return game;
    }
    public Game arrangeCardCountTestConditions(Game game){
        this.deck = deck;
        this.game = game;
        game.setNumPlayers(1);
        playerHand.add(new Card(Faces.Wild,Colors.Wild));
        game.arrangeStartingDeck(deck);
        Card topCard = game.getTopCard().getCard();
        deck.getDiscardPile().remove(topCard);

        this.player = new AIPlayer(playerHand);
        deck.addCardToDiscardPile(new Card(Faces.Wild,Colors.Wild));
        deck.addCardToDiscardPile(new Card(Faces.Wild,Colors.Wild));
        deck.addCardToDiscardPile(new Card(Faces.Wild,Colors.Wild));
        deck.addCardToDiscardPile(new Card(Faces.Five,Colors.Red));
        deck.addCardToDiscardPile(new Card(Faces.Five,Colors.Red));
        deck.addCardToDiscardPile(new Card(Faces.Two, Colors.Blue));

        return game;
    }




}
