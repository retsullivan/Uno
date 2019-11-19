package com.improving;

import java.util.List;
import java.util.Optional;

public interface IGame {

    public void playCard(Card card, Optional<Colors> color);
    Boolean isPlayable (Card card);
    Card draw();
    public void play(int numPlayers);   //← this will eventually be changed to public void
    //public void play(List<Player> player);
    public List<IPlayerInfo> getPlayerInfo();
    public IPlayerInfo getNextPlayer();
    public IPlayerInfo getPreviousPlayer();
    public IPlayerInfo getNextNextPlayer();
    public IDeck getDeckInfo();

}
