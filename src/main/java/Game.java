import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private Deck deck = new Deck();
    private ArrayList<Player> players = new ArrayList<>();
    private Player player = new Player();
    boolean gameInProgress = true;
    private TopCard topCard = new TopCard();




    private int numPlayers;
    private int currentPlayer=0;
    public int turnDirection = 1;
    public int currentTurn = 0;
    Scanner scanner = new Scanner(System.in);

    public Game(){
    }

    public Deck getDeck() {
        return deck;
    }
    public void setDeck (Deck deck){
        this.deck = deck;
    }
    public TopCard getTopCard (){return topCard;}
    public void setNumPlayers(int numPlayers) {this.numPlayers = numPlayers;}
    public ArrayList<Player> getPlayers() {return players;}
    public void addPlayer(Player player) {players.add(player);}



    public void Play(Game game) {
        System.out.println("How many players?");
        numPlayers=scanner.nextInt();
        gameInProgress = true;
        currentTurn = 0;
        turnDirection = 1;

        this.deck=game.getDeck();
        arrangeStartingDeck(deck);                  //getting deck ready
        for (int i = 0; i <numPlayers ; i++) {      //creating players with starting hands
            ArrayList<Card> hand = getStartingHand(deck);
            players.add(new Player(hand));
        }

        if (hasAction(topCard.getCard())){
            executeCardAction(topCard.getCard(),game);
        }

        while (gameInProgress){
            //this is making sure that we don't % a negative number
            if(currentTurn<0){
                currentTurn = currentTurn+players.size();
            }
            currentPlayer = currentTurn%(players.size());
            System.out.println("Current Player is Player Number" +currentPlayer);
            this.player = players.get(currentPlayer);
            Card faceUp = topCard.getCard();
            System.out.println("The top card is " + topCard.getCard().toString()+".");
            Card playedCard = player.takeTurn(game);
            if(player.getHandSize()==0 ){
                System.out.println("Player " + players.get(currentPlayer) +"has won the game!");
                gameInProgress=false;
            } else{
                if (!playedCard.toString().equalsIgnoreCase("No card ")) {
                    System.out.println("Player " + players.get(currentPlayer) +" played " + playedCard.toString() + " on " +faceUp.toString()+".");
                    if (hasAction(playedCard)) {
                        executeCardAction(playedCard, game);
                    }
                }
                //do this after every turn
                //turn direction will go back and forth
                currentTurn = currentTurn + turnDirection;
            }
        }
        System.out.println("Game Over");

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
                fromHand.getColor().toString().equals(topCard.getDeclaredColor().toString()) ||
                fromHand.getColor().toString().equalsIgnoreCase("wild")) {
            playable = true;
        }
        return playable;
    }

    public void playCard(Card card, Colors declaredColor){
        if (!card.toString().equalsIgnoreCase("No card ")) {
            deck.addCardToDiscardPile(card);
            topCard.setCard(card);
            topCard.setDeclaredColor(declaredColor);
        }
    }

    public Boolean hasAction(Card card) {

        if (card.getFace().toString().equalsIgnoreCase("draw4")) {
            return true;
        } else if (card.getFace().toString().equalsIgnoreCase("draw2")) {
            return true;
        } else if (card.getFace().toString().equalsIgnoreCase("skip")) {
            return true;
        }else if (card.getFace().toString().equalsIgnoreCase("reverse")) {
            return true;
        }else {
            return false;
        }

    }

    public void executeCardAction(Card card, Game game){

            if (Faces.Draw2.equals(card.getFace())) {
                var nextPlayerIndex = (currentTurn+turnDirection)%players.size();
                players.get(nextPlayerIndex);
                players.get(nextPlayerIndex).drawCard(game);
                players.get(nextPlayerIndex).drawCard(game);
                //this skips the next player
                currentTurn = currentTurn + turnDirection;
            } else if (Faces.Draw4.equals(card.getFace())) {
                var nextPlayerIndex = (currentTurn+turnDirection)%players.size();
                //this.player = players.get(nextPlayer);
                players.get(nextPlayerIndex);
                players.get(nextPlayerIndex).drawCard(game);
                players.get(nextPlayerIndex).drawCard(game);
                players.get(nextPlayerIndex).drawCard(game);
                players.get(nextPlayerIndex).drawCard(game);
                //player.drawCard(game);
                   //this skips the next player
                currentTurn = currentTurn + turnDirection;

            } else if (Faces.Skip.equals(card.getFace())) {
                currentTurn = currentTurn + turnDirection;
            } else if (Faces.Reverse.equals(card.getFace())) {
                turnDirection= turnDirection*(-1);
            }
    }


}
