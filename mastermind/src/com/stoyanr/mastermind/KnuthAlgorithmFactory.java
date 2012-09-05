package com.stoyanr.mastermind;

public class KnuthAlgorithmFactory implements AlgorithmFactory
{
    private final transient Mastermind mastermind;
    
    public KnuthAlgorithmFactory(final Mastermind mastermind)
    {
        assert (mastermind != null);
        this.mastermind = mastermind;
    }

    @Override
    public final Algorithm getAlgorithm()
    {
        return new KnuthAlgorithm(mastermind);
    }

}
