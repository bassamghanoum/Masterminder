package com.stoyanr.mastermind;

public class SimpleAlgorithmFactory implements AlgorithmFactory
{
    private final transient Mastermind mastermind;
    
    public SimpleAlgorithmFactory(final Mastermind mastermind)
    {
        assert (mastermind != null);
        this.mastermind = mastermind;
    }

    @Override
    public final Algorithm getAlgorithm()
    {
        return new SimpleAlgorithm(mastermind);
    }

}
