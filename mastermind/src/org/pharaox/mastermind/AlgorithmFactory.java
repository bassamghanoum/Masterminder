package org.pharaox.mastermind;

public class AlgorithmFactory
{
    public enum Type
    {
        SIMPLE, KNUTH, EXP_SIZE
    };
    
    private Type type;
    private Mastermind mastermind;
    
    public AlgorithmFactory(Type type, Mastermind mastermind)
    {
        this.type = type;
        this.mastermind = mastermind;
    }
    
    public Algorithm getAlgorithm()
    {
        Algorithm alg = null;
        switch (type)
        {
        case SIMPLE:
            alg = new SimpleAlgorithm(mastermind);
            break;
        case KNUTH:
            alg = new KnuthAlgorithm(mastermind);
            break;
        case EXP_SIZE:
            alg = new ExpectedSizeAlgorithm(mastermind);
            break;
        }
        return alg;
    }

}
