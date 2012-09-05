package com.stoyanr.mastermind;

public interface Algorithm
{
    String makeGuess();

    void putGuessScore(final String guess, final Score score);
}
