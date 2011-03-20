package org.pharaox.mastermind;

import static org.pharaox.mastermind.Messages.MSG_AIOP_STARTING_GAME;
import static org.pharaox.mastermind.Messages.MSG_AIOP_GAME_WON_IN_X_ROUNDS;
import static org.pharaox.mastermind.Messages.MSG_AIOP_GAME_LOST_IN_X_ROUNDS;
import static org.pharaox.mastermind.Messages.MSG_AIOP_GUESS;
import static org.pharaox.mastermind.Messages.MSG_AIOP_COWS;
import static org.pharaox.mastermind.Messages.MSG_AIOP_BULLS;

public abstract class AbstractIOPlayer implements Player
{
    @Override
    public final void startGame()
    {
        printLine(MSG_AIOP_STARTING_GAME);
    }

    @Override
    public final void endGame(final boolean won, final int roundsPlayed)
    {
        if (won)
            printLine(MSG_AIOP_GAME_WON_IN_X_ROUNDS, roundsPlayed);
        else
            printLine(MSG_AIOP_GAME_LOST_IN_X_ROUNDS, roundsPlayed);
    }

    @Override
    public final Score getScore(final String guess)
    {
        printLine(MSG_AIOP_GUESS, guess);
        int cows = readLineInt(MSG_AIOP_COWS);
        int bulls = readLineInt(MSG_AIOP_BULLS);
        return new Score(cows, bulls);
    }

    protected abstract void printLine(String message, Object... args);

    protected abstract int readLineInt(String message);

}
