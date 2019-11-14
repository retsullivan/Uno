import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private Deck deck = new Deck();
    private ArrayList<RPlayer> RPlayers = new ArrayList<>();
    private RPlayer RPlayer = new RPlayer();
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
    public ArrayList<RPlayer> getRPlayers() {return RPlayers;}
    public void addPlayer(RPlayer RPlayer) {
        RPlayers.add(RPlayer);}

    public void Play(Game game) {
        System.out.println("Welcome to Uno!");
        System.out.println("How many players?");
        numPlayers=scanner.nextInt();
        gameInProgress = true;
        currentTurn = 0;
        turnDirection = 1;

        this.deck=game.getDeck();
        arrangeStartingDeck(deck);                  //getting deck ready
        for (int i = 0; i <numPlayers ; i++) {      //creating players with starting hands
            ArrayList<Card> hand = getStartingHand(deck);
            RPlayers.add(new RPlayer(hand));
        }

        if (hasAction(topCard.getCard())){
            executeCardAction(topCard.getCard(),game);
        }

        while (gameInProgress){
            //this is making sure that we don't % a negative number
            if(currentTurn<0){
                currentTurn = currentTurn+ RPlayers.size();
            }
            currentPlayer = currentTurn%(RPlayers.size());
            System.out.println("Current Player is Player Number" +currentPlayer);
            this.RPlayer = RPlayers.get(currentPlayer);
            Card faceUp = topCard.getCard();
            System.out.println("The top card is " + topCard.getCard().toString()+".");
            Card playedCard = RPlayer.takeTurn(game);
            if(RPlayer.getHandSize()==0 ){
                System.out.println("Player " + currentPlayer +"has won the game!");
                game.displayGameOver();
                gameInProgress=false;
            } else{
                if (!(playedCard == null)) {
                    System.out.println("Player " + currentPlayer +" played " + playedCard.toString() + " on " +faceUp.toString()+".");
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
                if(currentTurn==0){
                    currentTurn = currentTurn+ RPlayers.size();
                }
                var nextPlayerIndex = (currentTurn+turnDirection)% RPlayers.size();
                RPlayers.get(nextPlayerIndex);
                RPlayers.get(nextPlayerIndex).drawCard(game);
                RPlayers.get(nextPlayerIndex).drawCard(game);
                //this skips the next player
                currentTurn = currentTurn + turnDirection;
                System.out.println("Player " +nextPlayerIndex+ " drew 2 and skipped their turn.");
            } else if (Faces.Draw4.equals(card.getFace())) {
                if(currentTurn==0){
                    currentTurn = currentTurn+ RPlayers.size();
                }
                var nextPlayerIndex = (currentTurn+turnDirection)% RPlayers.size();
//                RPlayers.get(nextPlayerIndex);
                RPlayers.get(nextPlayerIndex).drawCard(game);
                RPlayers.get(nextPlayerIndex).drawCard(game);
                RPlayers.get(nextPlayerIndex).drawCard(game);
                RPlayers.get(nextPlayerIndex).drawCard(game);
                //player.drawCard(game);
                   //this skips the next player
                currentTurn = currentTurn + turnDirection;
                System.out.println("Player " +nextPlayerIndex+ " drew 4 and skipped their turn.");

            } else if (Faces.Skip.equals(card.getFace())) {
                if(currentTurn<0){
                    currentTurn = currentTurn+ RPlayers.size();
                }
                var nextPlayerIndex = (currentTurn+turnDirection)%RPlayers.size();
                System.out.println("Player " +nextPlayerIndex+ " was skipped");
                currentTurn = currentTurn+turnDirection;
            } else if (Faces.Reverse.equals(card.getFace())) {

                turnDirection= turnDirection*(-1);
                System.out.println("Turn order reversed");
            }


    }

    public void yellUno(){
        System.out.println();
        System.out.println("Player " + currentPlayer+ " yelled");;
        System.out.println( "db    db d8b   db  .d88b. \n" +
                            "88    88 888o  88 .8P  Y8.\n" +
                            "88    88 88V8o 88 88    88\n" +
                            "88    88 88 V8o88 88    88\n" +
                            "88b  d88 88  V888 `8b  d8'\n" +
                            "~Y8888P' VP   V8P  `Y88P' ");

        System.out.println();
    }
    public void displayGameOver(){
        System.out.println();
        System.out.println(" d888b   .d8b.  .88b  d88. d88888b    .d88b.  db    db d88888b d8888b.\n" +
                            "88' Y8b d8' `8b 88'YbdP`88 88'       .8P  Y8. 88    88 88'     88  `8D\n" +
                            "88      88ooo88 88  88  88 88ooooo   88    88 Y8    8P 88ooooo 88oobY'\n" +
                            "88  ooo 88~~~88 88  88  88 88~~~~~   88    88 `8b  d8' 88~~~~~ 88`8b  \n" +
                            "88. ~8~ 88   88 88  88  88 88.       `8b  d8'  `8bd8'  88.     88 `88.\n" +
                            " Y888P  YP   YP YP  YP  YP Y88888P    `Y88P'     YP    Y88888P 88   YD");
        System.out.println();
    }
}
