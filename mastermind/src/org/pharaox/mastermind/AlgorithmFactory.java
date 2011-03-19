package org.pharaox.mastermind;

public class AlgorithmFactory
{
    public enum AlgorithmType
    {
        SIMPLE, KNUTH, PHARAOX, EXP_SIZE, DUMB, 
    };
    
    private AlgorithmType type;
    private Mastermind mastermind;
    
    public AlgorithmFactory(AlgorithmType type, Mastermind mastermind)
    {
        this.type = type;
        this.mastermind = mastermind;
    }
    
    public Algorithm getAlgorithm()
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
        case EXP_SIZE:
            algorithm = new ExpectedSizeAlgorithm(mastermind);
            break;
        case DUMB:
            algorithm = new DumbAlgorithm(mastermind);
            break;
        }
        return algorithm;
    }

}
