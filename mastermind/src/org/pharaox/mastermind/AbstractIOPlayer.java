package org.pharaox.mastermind;

import static org.pharaox.mastermind.Messages.*;

public abstract class AbstractIOPlayer implements Player
{
    @Override
    public void startGame()
    {
        initialize();
        printLine(MSG_AIOP_STARTING_GAME);
    }

    @Override
    public void endGame(boolean won, int roundsPlayed)
    {
        if (won)
            printLine(MSG_AIOP_GAME_WON_IN_X_ROUNDS, roundsPlayed);
        else
            printLine(MSG_AIOP_GAME_LOST_IN_X_ROUNDS, roundsPlayed);
    }
    
    @Override
    public Score getScore(String guess)
    {
        printLine(MSG_AIOP_GUESS, guess);
        int cows = readLineInt(MSG_AIOP_COWS);
        int bulls = readLineInt(MSG_AIOP_BULLS);
        return new Score(cows, bulls);
    }
    
    protected abstract void initialize();

    protected abstract void printLine(String message, Object... args);
    
    protected abstract int readLineInt(String message);

}
