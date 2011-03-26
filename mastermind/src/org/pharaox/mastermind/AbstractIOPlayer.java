package org.pharaox.mastermind;

import static org.pharaox.mastermind.Messages.*;

public abstract class AbstractIOPlayer implements Player
{
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
        printLine(M_C_GUESS, guess);
        final int cows = readLineInt(M_C_COWS);
        final int bulls = readLineInt(M_C_BULLS);
        return new Score(cows, bulls);
    }

    protected abstract void printLine(String message, Object... args);

    protected abstract int readLineInt(String message);

}
