package org.pharaox.mastermind;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class AbstractAlgorithm implements Algorithm
{
    private final transient Mastermind mastermind;
    private final transient SortedSet<String> allCodes;
    private final transient List<Score> allScores;
    private final transient Map<String, Score> guessScores = new HashMap<String, Score>();
    private final transient Set<String> evaluated = new HashSet<String>();

    private transient SortedSet<String> possibleCodes = new TreeSet<String>();
    
    public AbstractAlgorithm(final Mastermind mastermind)
    {
        this.mastermind = mastermind;
        allCodes = mastermind.getAllPossibleCodes();
        allScores = mastermind.getAllPossibleScores();
        possibleCodes.addAll(allCodes);
    }

    protected final Mastermind getMastermind()
    {
        return mastermind;
    }

    protected final List<Score> getAllScores()
    {
        return allScores;
    }

    public final SortedSet<String> getPossibleCodes()
    {
        return possibleCodes;
    }

    @Override
    public final String makeGuess()
    {
        evaluateGuesses();
        String guess = "";
        if (!possibleCodes.isEmpty())
        {
            guess = makeNextGuess();
        }
        return guess;
    }

    private void evaluateGuesses()
    {
        for (final String guess : guessScores.keySet())
        {
            if (!evaluated.contains(guess))
            {
                // @formatter:off
                possibleCodes = mastermind.evaluatePossibleCodes(guess, guessScores.get(guess), 
                    possibleCodes);
                // @formatter:on
                evaluated.add(guess);
            }
        }
    }

    private String makeNextGuess()
    {
        String guess = possibleCodes.first();
        double maxRating = 0.0;
        for (final String guessx : allCodes)
        {
            final double rating = calculateGuessRating(guessx);
            final boolean preferGuessx =
                possibleCodes.contains(guessx) && !possibleCodes.contains(guess);
            if ((rating > maxRating) || ((rating == maxRating) && preferGuessx))
            {
                guess = guessx;
            }
            maxRating = Math.max(maxRating, rating);
        }
        return guess;
    }

    protected abstract double calculateGuessRating(final String guess);

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
}
