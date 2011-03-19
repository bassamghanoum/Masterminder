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
    protected Mastermind mastermind;
    protected SortedSet<String> allCodes;
    protected List<Score> allScores;
    protected SortedSet<String> possibleCodes = new TreeSet<String>();
    protected Map<String, Score> guessScores = new HashMap<String, Score>();
    protected Set<String> evaluated = new HashSet<String>();

    public AbstractAlgorithm(Mastermind mastermind)
    {
        this.mastermind = mastermind;
        allCodes = mastermind.getAllPossibleCodes();
        allScores = mastermind.getAllPossibleScores();
        possibleCodes.addAll(allCodes);
    }

    @Override
    public String makeGuess()
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
        for (String guess : guessScores.keySet())
        {
            if (!evaluated.contains(guess))
            {
                possibleCodes = mastermind.evaluatePossibleCodes(guess, guessScores.get(guess), 
                    possibleCodes, false);
                evaluated.add(guess);
            }
        }
    }

    private String makeNextGuess()
    {
        String guess = possibleCodes.first();
        double maxRating = 0.0;
        for (String guessx : allCodes)
        {
            double rating = calculateGuessRating(guessx);
            // @formatter:off
            if ((rating > maxRating) ||
                ((rating == maxRating) && possibleCodes.contains(guessx) && !possibleCodes.contains(guess)))
                guess = guessx;
            // @formatter:on
            maxRating = Math.max(maxRating, rating);
        }
        return guess;
    }

    protected abstract double calculateGuessRating(String guess);

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
}
