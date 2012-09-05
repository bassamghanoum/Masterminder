package com.stoyanr.mastermind;

public class SimpleAlgorithm extends AbstractAlgorithm
{
    public SimpleAlgorithm(final Mastermind mastermind)
    {
        super(mastermind);
    }

    @Override
    protected final double calculateGuessRating(final String guess)
    {
        return 0.0;
    }
}
