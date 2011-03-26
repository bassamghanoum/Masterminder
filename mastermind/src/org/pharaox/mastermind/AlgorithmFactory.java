package org.pharaox.mastermind;

public class AlgorithmFactory
{
    public enum AlgorithmType
    {
        SIMPLE, KNUTH, PHARAOX, ESIZE, DUMB,
    }

    private final transient AlgorithmType type;
    private final transient Mastermind mastermind;

    public AlgorithmFactory(final AlgorithmType type, final Mastermind mastermind)
    {
        this.type = type;
        this.mastermind = mastermind;
    }

    public final Algorithm getAlgorithm()
    {
        Algorithm algorithm = null;
        switch (type)
        {
        case SIMPLE:
            algorithm = new SimpleAlgorithm(mastermind);
            break;
        case KNUTH:
            algorithm = new KnuthAlgorithm(mastermind);
            break;
        case PHARAOX:
            algorithm = new PharaoxAlgorithm(mastermind, 0.0);
            break;
        case ESIZE:
            algorithm = new ExpectedSizeAlgorithm(mastermind);
            break;
        case DUMB:
            algorithm = new DumbAlgorithm(mastermind);
            break;
        default:
            break;
        }
        return algorithm;
    }

}
