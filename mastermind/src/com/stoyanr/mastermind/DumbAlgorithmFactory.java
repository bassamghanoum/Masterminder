package com.stoyanr.mastermind;

public class DumbAlgorithmFactory implements AlgorithmFactory
{
    private final transient Mastermind mastermind;
    
    public DumbAlgorithmFactory(final Mastermind mastermind)
    {
        assert (mastermind != null);
        this.mastermind = mastermind;
    }

    @Override
    public final Algorithm getAlgorithm()
    {
        return new DumbAlgorithm(mastermind);
    }

}
