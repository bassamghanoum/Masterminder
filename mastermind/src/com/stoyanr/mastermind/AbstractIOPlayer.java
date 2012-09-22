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

/**
 * An abstract {@link Player} implementation for "human" players that use I/O streams, such as the
 * console, for interacting with a {@link Game}. When starting or ending a game, prints messages to
 * the output device. To provide a score, it first prints a message and then reads two numbers from
 * the input device.
 * 
 * <p>
 * This class has two protected abstract methods {@link #printLine(String, Object...)} and
 * {@link #readLineInt(String)} for the actual low-level I/O operations. All concrete I/O players
 * extending this class should implement these two methods to connect to actual I/O devices, such as
 * a reader/writer or stream pair.
 * 
 * @author Stoyan Rachev
 */
public abstract class AbstractIOPlayer implements Player
{
    private final transient Mastermind mastermind;

    /**
     * Creates a new I/O player with the given game setup.
     * 
     * @param mastermind The game setup to use.
     */
    public AbstractIOPlayer(final Mastermind mastermind)
    {
        assert (mastermind != null);
        this.mastermind = mastermind;
    }

    /**
     * Prints a message to the output device to inform the user that a game is starting.
     */
    @Override
    public final void startGame()
    {
        printLine(M_C_STARTING_GAME);
    }

    /**
     * Prints a message to the output device to inform the user that the game has been won or lost
     * in the given number of rounds.
     * 
     * @param won true if the game has been won, false otherwise.
     * @param roundsPlayed The number of rounds played during the game.
     */
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

    /**
     * First prints a message to the output device to ask the user for a score, and then reads two
     * integer numbers from the input device, first for the "cows" and then for the "bulls".
     * 
     * @param guess The guess to provide a score for.
     * @return The score entered by the user.
     * @throws MastermindException If the entered score is not a valid score for the given game
     * setup.
     */
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

    /**
     * Prints the given message to the output device.
     * 
     * @param message The message to print, in the "printf" format.
     * @param args Message arguments, if any.
     */
    protected abstract void printLine(String message, Object... args);

    /**
     * Prints the given message to the output device, and then reads an integer number from the
     * input device.
     * 
     * @param message The messge to print. Must not have any arguments.
     * @return The number read from the input device.
     */
    protected abstract int readLineInt(String message);

}
