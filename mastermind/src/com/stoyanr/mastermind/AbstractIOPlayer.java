package com.stoyanr.mastermind;

import static com.stoyanr.mastermind.Messages.*;

public abstract class AbstractIOPlayer implements Player
{
    private final transient Mastermind mastermind;
    
    public AbstractIOPlayer(final Mastermind mastermind)
    {
        assert (mastermind != null);
        this.mastermind = mastermind;
    }

    @Override
    public final void startGame()
    {
        printLine(M_C_STARTING_GAME);
    }

    @Override
    public final void endGame(final boolean won, final int roundsPlayed)
    {
        if (won)
        {
            printLine(M_C_GAME_WON, roundsPlayed);
        }
        else
        {
            printLine(M_C_GAME_LOST, roundsPlayed);
        }
    }

    @Override
    public final Score getScore(final String guess)
    {
        assert mastermind.isValidCode(guess);
        printLine(M_C_GUESS, guess);
        final int cows = readLineInt(M_C_COWS);
        final int bulls = readLineInt(M_C_BULLS);
        final Score score = new Score(cows, bulls);
        if (!mastermind.isValidScore(score))
        {
            throw new MastermindException();
        }
        return score;
    }

    protected abstract void printLine(String message, Object... args);

    protected abstract int readLineInt(String message);

}
