package org.pharaox.mastermind;

import java.util.SortedSet;

public class ExpectedSizeAlgorithm extends AbstractAlgorithm
{

    public ExpectedSizeAlgorithm(Mastermind mastermind)
    {
        super(mastermind);
    }

    @Override
    protected double calculateGuessRating(String guess)
    {
        double sum = 0.0;
        for (Score score : allScores)
        {
            SortedSet<String> elem = mastermind.evaluatePossibleCodes(guess, score, possibleCodes);
            double size = (double) (elem.size() * elem.size()) / (double) possibleCodes.size();
            sum += size;
        }
        return ((double) possibleCodes.size() - sum);
    }

    @Override
    public String toString()
    {
        return "Expected Size Mastermind Algorithm";
    }
}
