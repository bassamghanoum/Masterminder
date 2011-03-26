package org.pharaox.mastermind;

public class KnuthAlgorithmFactory implements AlgorithmFactory
{
    private final transient Mastermind mastermind;
    
    public KnuthAlgorithmFactory(final Mastermind mastermind)
    {
        super();
        this.mastermind = mastermind;
    }

    @Override
    public final Algorithm getAlgorithm()
    {
        return new KnuthAlgorithm(mastermind);
    }

}
