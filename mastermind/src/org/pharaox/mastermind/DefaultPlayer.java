package org.pharaox.mastermind;

public class DefaultPlayer implements Player
{
    private final transient Mastermind mastermind;
    private final transient String code;
    
    public DefaultPlayer(final Mastermind mastermind, final String code)
    {
        super();
        this.mastermind = mastermind;
        this.code = code;
    }

    @Override
    public final void startGame()
    {
        // No implementation needed
    }

    @Override
    public final void endGame(final boolean wonx, final int roundsPlayedx)
    {
        // No implementation needed
    }

    @Override
    public final Score getScore(final String guess)
    {
        return mastermind.evaluateScore(guess, code);
    }
}
