package com.stoyanr.mastermind;

public interface Player
{
    void startGame();

    void endGame(final boolean won, final int roundsPlayed);

    Score getScore(final String guess);
}
