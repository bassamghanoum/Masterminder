package org.pharaox.mastermind;

import static org.pharaox.mastermind.Mastermind.MAX_LENGTH;

public class Score
{
    public static final Score ZERO_SCORE = new Score(0, 0);
    
    private int cows;
    private int bulls;

    public Score(int cows, int bulls)
    {
        this.cows = cows;
        this.bulls = bulls;
    }

    public int getCows()
    {
        return cows;
    }

    public int getBulls()
    {
        return bulls;
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean result = false;
        if (obj instanceof Score)
        {
            Score other = (Score) obj;
            result = (cows == other.cows) && (bulls == other.bulls);
        }
        return result;
    }

    @Override
    public String toString()
    {
        return "(" + cows + ", " + bulls + ")";
    }
    
    @Override
    public int hashCode()
    {
        return bulls * (MAX_LENGTH + 1) + cows;
    }

}
