public interface IPlayer extends IPlayerInfo {
    public void takeTurn(Game game);
    public Card draw(Game game);

}
