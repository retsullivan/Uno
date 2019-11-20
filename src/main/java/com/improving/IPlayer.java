package com.improving;

public interface IPlayer extends IPlayerInfo {
    public void takeTurn(IGame game);
    public Card draw(IGame game);
}
