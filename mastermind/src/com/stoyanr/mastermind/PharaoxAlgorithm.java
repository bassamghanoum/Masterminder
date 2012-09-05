package com.stoyanr.mastermind;

import java.util.SortedSet;

public class PharaoxAlgorithm extends AbstractAlgorithm
{
    private final transient double percents;

    public PharaoxAlgorithm(final Mastermind mastermind, final double percents)
    {
        super(mastermind);
        assert (percents >= 0);
        this.percents = percents;
    }

    @Override
    protected final double calculateGuessRating(final String guess)
    {
        final Distribution dist = new Distribution();
        for (final Score score : getAllPossibleScores())
        {
            final SortedSet<String> codes = evaluatePossibleCodes(guess, score);
            final int diff = getPossibleCodes().size() - codes.size();
            dist.add(diff);
        }
        return dist.calculatePercentile(percents);
    }
}
