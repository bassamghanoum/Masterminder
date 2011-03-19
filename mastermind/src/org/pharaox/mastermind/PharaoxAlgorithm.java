package org.pharaox.mastermind;

import java.util.SortedSet;

public class PharaoxAlgorithm extends AbstractAlgorithm
{
    public static final String NAME = "Pharaox Mastermind Algorithm";
    
    double percents;

    public PharaoxAlgorithm(Mastermind mastermind, double percents)
    {
        super(mastermind);
        this.percents = percents;
    }

    @Override
    protected double calculateGuessRating(String guess)
    {
        Distribution dist = new Distribution();
        for (Score score : allScores)
        {
            SortedSet<String> elem = mastermind.evaluatePossibleCodes(guess, score, possibleCodes);
            int diff = possibleCodes.size() - elem.size();
            dist.add(diff);
        }
        return dist.calculatePercentile(percents);
    }

    @Override
    public String toString()
    {
        return NAME;
    }
}
