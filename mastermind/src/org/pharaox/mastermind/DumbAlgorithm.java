package org.pharaox.mastermind;

import java.util.HashMap;
import java.util.Map;

public class DumbAlgorithm implements Algorithm
{
    public static final String NAME = "Dumb Mastermind Algorithm";
    
    private String code;
    protected Map<String, Score> guessScores = new HashMap<String, Score>();

    public DumbAlgorithm(Mastermind mastermind)
    {
        code = mastermind.getAllPossibleCodes().first();
    }

    @Override
    public String makeGuess()
    {
        return code;
    }
    
    @Override
    public void putGuessScore(String guess, Score score)
    {
        guessScores.put(guess, score);
    }

    @Override
    public Score getGuessScore(String guess)
    {
        return guessScores.get(guess);
    }
    
    @Override
    public String toString()
    {
        return NAME;
    }
}
