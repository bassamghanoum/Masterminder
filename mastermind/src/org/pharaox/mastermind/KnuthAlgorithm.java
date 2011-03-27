package org.pharaox.mastermind;

import java.util.SortedSet;

public class KnuthAlgorithm extends AbstractAlgorithm
{
    public static final String NAME = "Knuth Mastermind Algorithm";

    public KnuthAlgorithm(final Mastermind mastermind)
    {
        super(mastermind);
    }

    @Override
    protected final double calculateGuessRating(final String guess)
    {
        int maxSize = 0;
        for (final Score score : getAllScores())
        {
            final SortedSet<String> codes = evaluatePossibleCodes(guess, score);
            maxSize = Math.max(maxSize, codes.size());
        }
        return (getPossibleCodes().size() - maxSize);
    }

    @Override
    public final String toString()
    {
        return NAME;
    }
}
