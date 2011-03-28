package org.pharaox.mastermind;

import java.util.SortedSet;

public class KnuthAlgorithm extends AbstractAlgorithm
{
    public KnuthAlgorithm(final Mastermind mastermind)
    {
        super(mastermind);
    }

    @Override
    protected final double calculateGuessRating(final String guess)
    {
        int maxSize = 0;
        for (final Score score : getAllPossibleScores())
        {
            final SortedSet<String> codes = evaluatePossibleCodes(guess, score);
            maxSize = Math.max(maxSize, codes.size());
        }
        return (getPossibleCodes().size() - maxSize);
    }
}
