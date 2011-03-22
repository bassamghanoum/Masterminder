package org.pharaox.mastermind;

import static org.pharaox.mastermind.Mastermind.MAX_LENGTH;

public class Score
{
    public static final Score ZERO_SCORE = new Score(0, 0);

    private final transient int cows;
    private final transient int bulls;

    public Score(final int cows, final int bulls)
    {
        this.cows = cows;
        this.bulls = bulls;
    }

    public final int getCows()
    {
        return cows;
    }

    public final int getBulls()
    {
        return bulls;
    }

    @Override
    public final String toString()
    {
        return "(" + cows + ", " + bulls + ")";
    }

    @Override
    public final boolean equals(final Object obj)
    {
        boolean result = false;
        if (obj instanceof Score)
        {
            final Score other = (Score) obj;
            result = (cows == other.cows) && (bulls == other.bulls);
        }
        return result;
    }

    @Override
    public final int hashCode()
    {
        return bulls * (MAX_LENGTH + 1) + cows;
    }

}
