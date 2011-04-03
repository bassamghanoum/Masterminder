package org.pharaox.mastermind;

public class PharaoxAlgorithmFactory implements AlgorithmFactory
{
    private final transient Mastermind mastermind;
    private final transient double percents;
    
    public PharaoxAlgorithmFactory(final Mastermind mastermind, final double percents)
    {
        assert (mastermind != null && percents >= 0);
        this.mastermind = mastermind;
        this.percents = percents;
    }

    @Override
    public final Algorithm getAlgorithm()
    {
        return new PharaoxAlgorithm(mastermind, percents);
    }

}
