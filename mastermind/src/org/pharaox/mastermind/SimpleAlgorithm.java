package org.pharaox.mastermind;

public class SimpleAlgorithm extends AbstractAlgorithm
{
    public SimpleAlgorithm(Mastermind mastermind)
    {
        super(mastermind);
    }

    @Override
    protected double calculateGuessRating(String guess)
    {
        return 0.0;
    }
    
    @Override
    public String toString()
    {
        return "Simple Mastermind Algorithm";
    }
}
