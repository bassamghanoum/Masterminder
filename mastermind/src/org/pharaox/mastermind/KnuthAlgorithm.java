package org.pharaox.mastermind;

import java.util.SortedSet;

public class KnuthAlgorithm extends AbstractAlgorithm
{
    public static final String NAME = "Knuth Mastermind Algorithm";

    public KnuthAlgorithm(Mastermind mastermind)
    {
        super(mastermind);
    }

    @Override
    protected double calculateGuessRating(String guess)
    {
        int maxSize = 0;
        for (Score score : allScores)
        {
            SortedSet<String> elem = mastermind.evaluatePossibleCodes(guess, score, possibleCodes);
            maxSize = Math.max(maxSize, elem.size());
        }
        return (possibleCodes.size() - maxSize);
    }

    @Override
    public String toString()
    {
        return NAME;
    }
}
