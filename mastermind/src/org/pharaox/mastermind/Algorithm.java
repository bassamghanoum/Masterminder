package org.pharaox.mastermind;

public interface Algorithm
{
    String makeGuess();

    void putGuessScore(final String guess, final Score score);
}
