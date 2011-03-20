package org.pharaox.mastermind;

public class SimpleAlgorithm extends AbstractAlgorithm
{
    public static final String NAME = "Simple Mastermind Algorithm";

    public SimpleAlgorithm(final Mastermind mastermind)
    {
        super(mastermind);
    }

    @Override
    protected final double calculateGuessRating(final String guess)
    {
        return 0.0;
    }

    @Override
    public final String toString()
    {
        return NAME;
    }
}
