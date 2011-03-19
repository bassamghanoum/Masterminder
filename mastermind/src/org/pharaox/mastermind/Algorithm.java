package org.pharaox.mastermind;

public interface Algorithm
{
    String makeGuess();
    
    void putGuessScore(String guess, Score score);

    Score getGuessScore(String guess);
}
