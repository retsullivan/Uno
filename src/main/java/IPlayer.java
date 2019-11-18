public interface IPlayer {

    public int getHandSize();
    public void takeTurn(Game game);
    public Card draw(Game game);

}
