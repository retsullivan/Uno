import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private Deck deck = new Deck();
    private ArrayList<Player> players = new ArrayList<>();
    private Player player = new Player();
    private GameOverException GameOverException;
    private Boolean previousTurnSkipped = false;
    boolean gameInProgress = true;
    private TopCard topCard = new TopCard();
    private int numPlayers = 1;
    private int nextPlayer=0;
    private int turnDirection = 1;

    public Game(){
    }

    public Player getPlayer(int playerNum) {return players.get(playerNum+1);}
    public Boolean getPreviousTurnSkipped() {
        return previousTurnSkipped;
    }
    public void setPreviousTurnSkipped(Boolean previousTurnSkipped) {
        this.previousTurnSkipped = previousTurnSkipped;
    }
    public Deck getDeck() {
        return deck;
    }
    public void setDeck (Deck deck){
        this.deck = deck;
    }
    public TopCard getTopCard (){return topCard;}



    public void Play(Game game) throws GameOverException {

        this.deck=game.getDeck();
        arrangeStartingDeck(deck);                  //getting deck ready
        for (int i = 0; i <numPlayers ; i++) {      //creating players with starting hands
            ArrayList<Card> hand = getStartingHand(deck);
            players.add(new Player(hand));
        }

        //placing top card on discardpile
        arrangeStartingDeck(deck);
        gameInProgress = true;

        int currentTurn = 1;
        int turnDirection = 1;

        while (gameInProgress){
            nextPlayer = currentTurn%players.size();
            try{
                this.player = players.get(nextPlayer);
                Card playedCard = player.takeTurn(game);
                if(player.getHandSize()==0 ){
                     throw GameOverException;
                } else{
                    if (playedCard!=null) {
                        if (hasAction(playedCard)) {
                            executeCardAction(playedCard, game);
                        }
                    }
                    //do this after every turn
                    //turn direction will go back and forth
                    currentTurn = currentTurn+turnDirection;
                }
            }catch (GameOverException goe){
                System.out.println("Game Over");
                gameInProgress = false;
            }
        }
    }



    public void arrangeStartingDeck(Deck deck){
        this.deck=deck;
        deck.setDrawPile(deck.shuffle(deck.getDrawPile()));
        Card firstCard = deck.draw();
        setTopCard(firstCard, firstCard.getColor());
        deck.addCardToDiscardPile(topCard.getCard());
    }

    public void setTopCard(Card card, Colors declaredColor){
        topCard.setCard(card);
        if (declaredColor.toString().equalsIgnoreCase("wild")){
            Random random = new Random();
            int number = random.nextInt(100000)%4;
            if (number==0){
                declaredColor=Colors.Red;
            }else if (number==1){
                declaredColor=Colors.Green;
            }else if (number==2){
                declaredColor=Colors.Yellow;
            }else if (number==3){
                declaredColor=Colors.Blue;
            }
        }
        topCard.setDeclaredColor(declaredColor);
    }

    public ArrayList<Card> getStartingHand(Deck deck){
            this.deck=deck;
            ArrayList<Card> hand = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                hand.add(deck.draw());
            }
            return hand;
    }

    public Boolean isPlayable (Card fromHand, TopCard topCard) {
        boolean playable = false;

        if (fromHand.getFace().toString().equalsIgnoreCase(topCard.getCard().getFace().toString()) ||
                fromHand.getColor().toString().equals(topCard.getCard().getColor().toString()) ||
                fromHand.getColor().toString().equalsIgnoreCase("wild") ||
                topCard.getCard().getColor().toString().equalsIgnoreCase("wild")) {
            playable = true;
        }
        return playable;
    }

    public void playCard(Card card, Colors declaredColor){
        getDeck().getDiscardPile().add(card);
        topCard.setCard(card);
        topCard.setDeclaredColor(declaredColor);
    }

    public Boolean hasAction(Card card) {

        if (card.getFace().toString().equalsIgnoreCase("draw4")) {
            return true;
        } else if (card.getFace().toString().equalsIgnoreCase("draw2")) {
            return true;
        } else {
            return false;
        }
        //||card.getFace().toString().equalsIgnoreCase("skip")
        //||card.getFace().toString().equalsIgnoreCase("reverse")

    }

    public void executeCardAction(Card card, Game game){
            if (card.getFace().toString().equalsIgnoreCase("Draw2")) {
                players.get(nextPlayer+1).drawCard(game);
                players.get(nextPlayer+1).drawCard(game);

                game.nextPlayer= nextPlayer + game.turnDirection;
            } else if (card.getFace().toString().equalsIgnoreCase("Draw4")) {
                players.get(nextPlayer+1).drawCard(game);
                players.get(nextPlayer+1).drawCard(game);
                players.get(nextPlayer+1).drawCard(game);
                players.get(nextPlayer+1).drawCard(game);

                game.nextPlayer= nextPlayer + game.turnDirection;

            }
            //if (topCard.getFace().toString().equalsIgnoreCase("Skip")){
            //game.nextPlayer= nextPlayer + game.turnDirection;
            //}
            //if (topCard.getFace().toString().equalsIgnoreCase("Reverse")){
            //game.turnDirection = turnDirection*(-1);
            //game.nextPlayer= nextPlayer + game.turnDirection; }

        }


}
