package com.stoyanr.mastermind;

import java.util.SortedSet;

public class ExpectedSizeAlgorithm extends AbstractAlgorithm
{
    public ExpectedSizeAlgorithm(final Mastermind mastermind)
    {
        super(mastermind);
    }

    @Override
    protected final double calculateGuessRating(final String guess)
    {
        double sum = 0.0;
        for (final Score score : getAllPossibleScores())
        {
            final SortedSet<String> codes = evaluatePossibleCodes(guess, score);
            final double size =
                (double) (codes.size() * codes.size()) / (double) getPossibleCodes().size();
            sum += size;
        }
        return (getPossibleCodes().size() - sum);
    }
}
