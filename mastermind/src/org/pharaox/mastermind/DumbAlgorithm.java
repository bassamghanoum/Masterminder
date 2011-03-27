package org.pharaox.mastermind;

public class DumbAlgorithm implements Algorithm
{
    public static final String NAME = "Dumb Mastermind Algorithm";

    private final transient String code;
    private final transient SimpleAlgorithm delegate;

    public DumbAlgorithm(final Mastermind mastermind)
    {
        code = mastermind.getAllPossibleCodes().first();
        delegate = new SimpleAlgorithm(mastermind);
    }

    @Override
    public final String makeGuess()
    {
        return code;
    }

    @Override
    public final void putGuessScore(final String guess, final Score score)
    {
        delegate.putGuessScore(guess, score);
    }

    @Override
    public final Score getGuessScore(final String guess)
    {
        return delegate.getGuessScore(guess);
    }

    @Override
    public final String toString()
    {
        return NAME;
    }
}
