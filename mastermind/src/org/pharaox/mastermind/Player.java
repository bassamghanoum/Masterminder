package org.pharaox.mastermind;

public interface Player
{
    public void startGame();
    
    public void endGame(boolean won, int roundsPlayed);
    
    public Score getScore(String guess);
}
