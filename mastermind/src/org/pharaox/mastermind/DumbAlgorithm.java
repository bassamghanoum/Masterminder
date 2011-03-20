package org.pharaox.mastermind;

import java.util.HashMap;
import java.util.Map;

public class DumbAlgorithm implements Algorithm
{
    public static final String NAME = "Dumb Mastermind Algorithm";

    private String code;
    private Map<String, Score> guessScores = new HashMap<String, Score>();

    public DumbAlgorithm(final Mastermind mastermind)
    {
        code = mastermind.getAllPossibleCodes().first();
    }

    @Override
    public final String makeGuess()
    {
        return code;
    }

    @Override
    public final void putGuessScore(final String guess, final Score score)
    {
        guessScores.put(guess, score);
    }

    @Override
    public final Score getGuessScore(final String guess)
    {
        return guessScores.get(guess);
    }

    @Override
    public final String toString()
    {
        return NAME;
    }
}
