package org.pharaox.mastermind;

public class DumbAlgorithm implements Algorithm
{
    private final transient String code;

    public DumbAlgorithm(final Mastermind mastermind)
    {
        code = mastermind.getAllPossibleCodes().first();
        assert mastermind.isValidCode(code);
    }

    @Override
    public final String makeGuess()
    {
        return code;
    }

    @Override
    public final void putGuessScore(final String guess, final Score score)
    {
        // No implementation needed
    }

}
