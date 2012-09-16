/*
 * $Id: $
 *
 * Copyright 2012 Stoyan Rachev (stoyanr@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
