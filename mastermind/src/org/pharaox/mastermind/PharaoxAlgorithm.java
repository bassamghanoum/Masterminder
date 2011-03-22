package org.pharaox.mastermind;

import java.util.SortedSet;

public class PharaoxAlgorithm extends AbstractAlgorithm
{
    public static final String NAME = "Pharaox Mastermind Algorithm";

    private final transient double percents;

    public PharaoxAlgorithm(final Mastermind mastermind, final double percents)
    {
        super(mastermind);
        this.percents = percents;
    }

    @Override
    protected final double calculateGuessRating(final String guess)
    {
        final Distribution dist = new Distribution();
        for (final Score score : getAllScores())
        {
            // @formatter:off
            final SortedSet<String> elem = getMastermind().evaluatePossibleCodes(guess, score, 
                getPossibleCodes(), false);
            // @formatter:on
            final int diff = getPossibleCodes().size() - elem.size();
            dist.add(diff);
        }
        return dist.calculatePercentile(percents);
    }

    @Override
    public final String toString()
    {
        return NAME;
    }
}
