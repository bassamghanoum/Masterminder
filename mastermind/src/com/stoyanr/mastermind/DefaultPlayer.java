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

/**
 * A {@link Player} implementation for "computer" players that interact with a {@link Game}
 * directly. Does not perform any initialization or cleanup, and always scores the passed guess
 * correctly against the code passed upon construction.
 * 
 * @author Stoyan Rachev
 */
public class DefaultPlayer implements Player
{
    private final transient Mastermind mastermind;
    private final transient String code;

    /**
     * Creates a new computer player with the given game setup and code.
     * 
     * @param mastermind The game setup to use.
     * @param code The code against which all guesses will be scored. It has to be a valid code for
     * the passed game setup.
     */
    public DefaultPlayer(final Mastermind mastermind, final String code)
    {
        assert (mastermind != null && mastermind.isValidCode(code));
        this.mastermind = mastermind;
        this.code = code;
    }

    /**
     * For computer players, this method doesn't do anything.
     */
    @Override
    public final void startGame()
    {
        // No implementation needed
    }

    /**
     * For computer players, this method doesn't do anything.
     */
    @Override
    public final void endGame(final boolean won, final int roundsPlayed)
    {
        // No implementation needed
    }

    /**
     * Scores the passed guess correctly against the code passed upon construction, by delegating to
     * {@link Mastermind#evaluateScore(String, String)}.
     * 
     * @param guess The guess to provide a score for.
     * @return The score for the passed guess.
     */
    @Override
    public final Score getScore(final String guess)
    {
        assert mastermind.isValidCode(guess);
        return mastermind.evaluateScore(guess, code);
    }
}
