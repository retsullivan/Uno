public interface Player {

    public int getHandSize();
    public void takeTurn(Game game);
    public Card drawCard (Game game);

}
