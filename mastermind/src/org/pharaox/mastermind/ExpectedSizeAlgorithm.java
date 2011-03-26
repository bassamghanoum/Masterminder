package org.pharaox.mastermind;

import java.util.SortedSet;

public class ExpectedSizeAlgorithm extends AbstractAlgorithm
{
    public static final String NAME = "Expected Size Mastermind Algorithm";

    public ExpectedSizeAlgorithm(final Mastermind mastermind)
    {
        super(mastermind);
    }

    @Override
    protected final double calculateGuessRating(final String guess)
    {
        double sum = 0.0;
        for (final Score score : getAllScores())
        {
            // @formatter:off
            final SortedSet<String> elem = getMastermind().evaluatePossibleCodes(guess, score, 
                getPossibleCodes());
            // @formatter:on
            final double size =
                (double) (elem.size() * elem.size()) / (double) getPossibleCodes().size();
            sum += size;
        }
        return (getPossibleCodes().size() - sum);
    }

    @Override
    public final String toString()
    {
        return NAME;
    }
}
