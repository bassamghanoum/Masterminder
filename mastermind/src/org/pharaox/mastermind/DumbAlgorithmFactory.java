package org.pharaox.mastermind;

public class DumbAlgorithmFactory implements AlgorithmFactory
{
    private final transient Mastermind mastermind;
    
    public DumbAlgorithmFactory(final Mastermind mastermind)
    {
        super();
        this.mastermind = mastermind;
    }

    @Override
    public final Algorithm getAlgorithm()
    {
        return new DumbAlgorithm(mastermind);
    }

}
