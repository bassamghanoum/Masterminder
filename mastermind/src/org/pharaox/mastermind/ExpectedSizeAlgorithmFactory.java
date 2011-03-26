package org.pharaox.mastermind;

public class ExpectedSizeAlgorithmFactory implements AlgorithmFactory
{
    private final transient Mastermind mastermind;
    
    public ExpectedSizeAlgorithmFactory(final Mastermind mastermind)
    {
        super();
        this.mastermind = mastermind;
    }

    @Override
    public final Algorithm getAlgorithm()
    {
        return new ExpectedSizeAlgorithm(mastermind);
    }

}
