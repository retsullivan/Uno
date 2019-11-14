public interface Player {

    public int getHandSize();
    public Card takeTurn(Game game);
    public Card drawCard (Game game);

}
