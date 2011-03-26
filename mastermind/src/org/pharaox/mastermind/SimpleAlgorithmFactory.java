package org.pharaox.mastermind;

public class SimpleAlgorithmFactory implements AlgorithmFactory
{
    private final transient Mastermind mastermind;
    
    public SimpleAlgorithmFactory(final Mastermind mastermind)
    {
        super();
        this.mastermind = mastermind;
    }

    @Override
    public final Algorithm getAlgorithm()
    {
        return new SimpleAlgorithm(mastermind);
    }

}
